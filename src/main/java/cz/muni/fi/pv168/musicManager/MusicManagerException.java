package cz.muni.fi.pv168.musicManager;

/**
 * Created by Hany on 8.4.2014.
 */
public class MusicManagerException extends Exception {
    public MusicManagerException() {
        super();
    }

    public MusicManagerException(String message) {
        super(message);
    }

    public MusicManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
