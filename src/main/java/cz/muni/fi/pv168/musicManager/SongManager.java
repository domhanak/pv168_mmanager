package cz.muni.fi.pv168.musicManager;

import java.util.List;

/**
 * Simple interface that defines functions of Song database manager.
 *
 * Created by Hany on 5.3.2014.
 */
public interface SongManager {

    public Song createSong(Song song) throws SongException;

    public void deleteSong(Song song) throws SongException;

    public void updateSong(Song song) throws SongException;

    public List<Song> getAllSongs() throws SongException;

    public Song getSongByName(String name) throws SongException;

    public Song getSongById(long id) throws SongException;
}
