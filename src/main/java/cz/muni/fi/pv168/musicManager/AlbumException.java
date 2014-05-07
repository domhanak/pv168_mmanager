package cz.muni.fi.pv168.musicManager;

/**
 * Created by Hany on 25.3.2014.
 */
public class AlbumException extends Exception {
    AlbumException(String message, Throwable cause){
        super(message, cause);
    }

    public AlbumException(String message) {
        super(message);
    }
}