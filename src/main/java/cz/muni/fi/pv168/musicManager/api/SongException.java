package cz.muni.fi.pv168.musicManager.api;

/**
 * Song exception.
 * Thrown when something wrong happens processing songs.
 * <p/>
 * Created by Hany on 19.3.2014.
 */
public class SongException extends Exception {
    SongException(String message, Throwable cause)
    {
        super(message, cause);
    }

    SongException(Throwable cause)
    {
        super(cause);
    }

    SongException(String message)
    {
        super(message);
    }

}
