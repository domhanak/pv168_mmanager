package cz.muni.fi.pv168.musicManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * UNFINISHED
 *
 * Created by Hany on 5.3.2014.
 */
public class SongManagerImpl implements SongManager {
    private static final SongManager SONG_MANAGER = new SongManagerImpl();
    private final static Logger log = LoggerFactory.getLogger(SongManagerImpl.class);

    private SongManagerImpl() {}

    public static final SongManager getSongManager() {
        return SONG_MANAGER;
    }

    @Override
    public Song createSong(Song song) {
        if (song == null) {
            throw new IllegalArgumentException("In createSong: song is null");
        }


        //TODO: Save to DB.

        return song;
    }

    @Override
    public void deleteSong(Song song) {
        if (song == null) {
            throw new IllegalArgumentException("In deleteSong: song is null");
        }

        //TODO: Delete from DB.
    }

    @Override
    public void updateSong(Song song) {
        if (song == null) {
            throw new IllegalArgumentException("In updateSong: song is null");
        }

        //TODO: Update song in DB.
    }

    @Override
    public List<Song> getAllSongs() {
        return null;
    }

    @Override
    public Song getSongByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("In getSongByName: name is null");
        }

        //TODO: Find song with name.

        return null;
    }

    @Override
    public Song getSongById(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("In getSongById: id < 0!");
        }

        //TODO: Find song with id.

        return null;
    }
}
