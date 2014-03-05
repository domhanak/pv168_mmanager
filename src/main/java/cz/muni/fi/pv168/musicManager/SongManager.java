package cz.muni.fi.pv168.musicManager;

import java.util.Collection;

/**
 * Created by Hany on 5.3.2014.
 */
public interface SongManager {

    public Song createSong(Song song);

    public void deleteSong(Song song);

    public void updateSong(Song song);

    public Collection<Song> getAllSongs();

    public Song getSongByName(String name);

    public Song getSongById(long id);


}