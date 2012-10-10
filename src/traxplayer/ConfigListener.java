package traxplayer;

public interface ConfigListener {
    public void configChanged(String key);

    public void reset();
}
