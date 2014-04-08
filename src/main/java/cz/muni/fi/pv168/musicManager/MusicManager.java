package cz.muni.fi.pv168.musicManager;

import java.util.List;

/**
 * Created by Adam on 12.3.14.
 */
public interface MusicManager {

    public void addSongIntoAlbum(Song song, Album album);

    public void removeSongFromAlbum(Song song, Album album);

    public List<Song> getAllSongsFromAlbum(Album album);

    public SongManager getSongManager();

    public AlbumManager getAlbumManager();
}