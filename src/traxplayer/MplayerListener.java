package traxplayer;

public interface MplayerListener {

    public void seek(float pos);

    public void pause();

    public void speed(float spd);

    public void killed();
}
