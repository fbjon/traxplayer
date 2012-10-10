package traxplayer;

import java.io.*;
import java.util.regex.*;

public class Mplayer implements MplayerListener, ConfigListener {

    File mediaFile;
    float offset;
    private Process proc;
    private BufferedWriter stdin =
            new BufferedWriter(new OutputStreamWriter(new NullOutputStream()));
    private MplayerReader readerThread;
    final MplayerListener other;
    final String mplayerpath;
    float currpos = 0;
    final Config conf;
    double sensitivity_default = 0.4f;
    double sensitivity = sensitivity_default;
    private int lastVolume;
    private float mediaLength;

    public Mplayer(Config conf, MplayerListener other) {
        this.conf = conf;
        this.other = other;
        this.mplayerpath = conf.getString(Config.confPlayer);
        sensitivity_default = conf.getDouble(Config.confSensitivity);

    }

    @Override
    protected void finalize() throws Throwable {
        kill();
        super.finalize();
    }

    synchronized public void kill() {
        conf.removeListener(this);
        if (proc != null) {
            proc.destroy();
        }
        proc = null;
        if (readerThread != null) {
            readerThread.stop();
        }
        readerThread = null;
    }
    private final Object initlock = new Object();

    private void init() {
        conf.addListener(this);
        if (proc == null) {
            synchronized (initlock) {
                if (proc == null && mediaFile != null && mediaFile.exists()) {
                    System.out.println("init player: " + mediaFile);
                    try {
                        ProcessBuilder builder = new ProcessBuilder()
                                .command(mplayerpath, "-slave", mediaFile.getAbsolutePath());
                        proc = builder.start();
                        stdin = new BufferedWriter(new OutputStreamWriter(
                                proc.getOutputStream()));
                        readerThread = new MplayerReader(proc);
                        new Thread(readerThread, "Mplayer-reader:" + mediaFile.getName()).start();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                boolean done = false;
                                while (!done) {
                                    try {
                                        proc.waitFor();
                                        proc.exitValue();
                                        done = true;
                                    } catch (Exception e) {
                                    }
                                }
                                kill();
                                other.killed();
                            }
                        }, "Mplayer-killwaiter:" + mediaFile.getName()).start();
                        pause();
                        write("get_time_length");
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }
    }

    public void setFile(File f) {
        this.mediaFile = f;
        kill();
    }
    final Pattern time = Pattern.compile(".*A:\\s*(\\d+(?:\\.\\d+)?).*");
    final Pattern speed = Pattern.compile(".*(\\d+\\.\\d+)x.*");
    final Pattern filetime = Pattern.compile("ANS_TIME_POSITION=([\\d]+(\\.\\d+)?)");
    final Pattern filetimelength = Pattern.compile("ANS_LENGTH=([\\d]+(\\.\\d+)?)");
    final Pattern pause = Pattern.compile(".*PAUSE.*");

    @Override
    public void killed() {
    }

    @Override
    public void configChanged(String key) {
        if (Config.confPlayer.equals(key)) {
        } else if (Config.confSensitivity.equals(key)) {
            this.sensitivity_default = conf.getDouble(key);
        }
    }

    @Override
    public void reset() {
    }

    class MplayerReader implements Runnable {

        private boolean run = true;
        private final BufferedReader stdout;
        private final Process mp;

        public MplayerReader(Process mp) {
            this.mp = mp;
            stdout = new BufferedReader(new InputStreamReader(
                    mp.getInputStream()));
        }

        private String readLine() {
            String line;
            try {
                line = stdout.readLine();
            } catch (IOException ex) {
                line = null;
                System.out.println(ex.getMessage());
            }

            return line;
        }

        public void stop() {
            run = false;
        }

        @Override
        public void run() {
            String line;
            while (run) {
                line = readLine();
                if (line != null) {
                    Matcher m;
                    if ((m = time.matcher(line)).matches()) {
                        float pos = Float.parseFloat(m.group(1));
                        other.seek(pos);
                        currpos = pos;
                        Matcher spd = speed.matcher(line);
                        if (spd.matches() && spd.groupCount() >= 1 && spd.group(1) != null) {
                            other.speed(Float.parseFloat(spd.group(1)));
                        }

                    } else if (pause.matcher(line).matches()) {
                        other.pause();
                    } else if ((m = filetimelength.matcher(line)).matches()) {
                        float len = Float.parseFloat(m.group(1));
                        mediaLength = len;
                    }
                } else {
                    run = false;
                }
            }
        }
    }

    public void volume(int vol) {
        int _vol = Math.max(0, Math.min(100, vol));
        write("volume " + _vol + " 1");
        System.out.println("vol: " + _vol);
        this.lastVolume = _vol;
    }

    public void skip(float i) {
        write("seek " + i + " 0");
    }

    @Override
    public void seek(float pos) {
        init();
        float fpos = pos + offset;
        if (Math.abs(fpos - currpos) > sensitivity) {
            if (fpos < -sensitivity) {
                write("pausing seek " + fpos + " 2");
            } else if (fpos > mediaLength) {
                write("pausing seek " + (mediaLength - 0.1) + " 2");
            } else {
                write("seek " + fpos + " 2");
            }
            currpos = fpos;
        }
        if (sensitivity == 0.0) {
            // volume sometimes gets zeroed when writing lots of seeks
            // e.g. while tweaking offset
            write("volume " + lastVolume + " 1");
        }
        sensitivity = sensitivity_default;
    }

    public void setOffset(float off) {
        this.offset = off;
        sensitivity = 0.0f; // make sure the new offset is used immediately
    }

    private void write(String s) {
        try {
            stdin.write(s);
            stdin.write("\n");
            stdin.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void pause() {
        init();
        write("pause");
        sensitivity = 0.0f;
    }

    public void cmd(String cmd) {
        init();
        write(cmd);
    }

    public void normalSpeed() {
        init();
        write("speed_set 1");
    }

    @Override
    public void speed(float spd) {
        init();
        write("speed_set " + spd);
    }

    private class NullOutputStream extends OutputStream {

        @Override
        public void write(int b) throws IOException {
        }
    }
}
