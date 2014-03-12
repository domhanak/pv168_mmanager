package cz.muni.fi.pv168.musicManager;

import java.util.List;

/**
 * Created by Hany on 5.3.2014.
 */
public class SongManagerImpl implements SongManager {

    private SongManagerImpl() {}

    public static SongManager getSongManager() {
        return 
    }

    @Override
    public Song createSong(Song song) {
        if (song == null) {
            throw new NullPointerException("In createSong: song is null");
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
