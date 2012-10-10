package traxplayer;

import java.io.*;
import java.util.*;

public final class Config {

    final File confFile;
    final private Properties props = new Properties();
    final public static boolean windowsOS = System.getProperty("os.name").toLowerCase().contains("win");
    final private Set<ConfigListener> listeners = new HashSet<ConfigListener>();
    public static final String confSensitivity = "drift-sensitivity";
    public static final String confPlayer = "mplayer-path";
    public static final String confDefaultMediaFolder = "default-folder";
    public static final String mainX = "mainX";
    public static final String mainY = "mainY";
    private static String defaultFile = "traxplayer.conf";
    private static File defaultFolder = new File(System.getProperty("user.home"), ".traxplayer");

    public Config() {
        this(new File(defaultFile).exists()
                ? new File(defaultFile)
                : new File(defaultFolder, "traxplayer.conf"));
    }

    public Config(File confFile) {
        this.confFile = confFile;
        props.setProperty(confSensitivity, "0.4");
        props.setProperty(confPlayer, someDefaultMplayer());
        props.setProperty(confDefaultMediaFolder, "");
        read();

    }

    private String someDefaultMplayer() {
        if (Config.windowsOS) {
            File smplayer = new File("C:/Program Files (x86)/SMPlayer/mplayer/mplayer.exe");
            if (smplayer.exists()) {
                return smplayer.getAbsolutePath();
            } else {
                return "mplayer.exe";
            }
        } else {
            return "mplayer";
        }
    }

    public void write() {
        if (!confFile.exists()) {
            confFile.getParentFile().mkdirs();
        }
        try {
            synchronized (props) {
                props.store(new FileWriter(confFile), " TraxPlayer configuration\n\n If you remove this file it will be recreated. You can also place\n this file in the same directory where you're running TraxPlayer from.\n");
            }
        } catch (IOException ex) {
        }
    }

    public String getString(String key) {
        return get(key);
    }

    public Integer getInt(String key) {
        Integer ret;
        try {
            ret = Integer.parseInt(get(key));
        } catch (NumberFormatException e) {
            ret = 0;
        }
        return ret;
    }

    public Double getDouble(String key) {
        Double ret;
        try {
            ret = Double.parseDouble(get(key));
        } catch (NumberFormatException e) {
            ret = 1.0;
        }
        return ret;
    }

    private String get(String key) {
        return (String) props.get(key);
    }

    synchronized public String set(String key, String value) {
        String prev;
        synchronized (props) {
            prev = (String) props.setProperty(key, value);
        }
        write();
        if (value != null && !value.equals(prev)) {
            notify(key);
        }
        return prev;
    }

    public String set(String key, Object value) {
        return set(key, value.toString());
    }

    public void read() {
        read(confFile);
    }

    public void read(File file) {
        try {
            FileReader fileReader;
            fileReader = new FileReader(confFile);
            synchronized (props) {
                props.load(fileReader);
            }
        } catch (IOException ex) {
        }
    }

    private void notify(String k) {
        for (Object l : listeners.toArray()) {
            ((ConfigListener)l).configChanged(k);
        }
    }

    public void reset() {
        for (Object l :  listeners.toArray()) {
            ((ConfigListener)l).reset();
        }
    }

    public void removeListener(ConfigListener aThis) {
        synchronized (listeners) {
            listeners.remove(aThis);
        }
    }

    public void addListener(ConfigListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }
}
