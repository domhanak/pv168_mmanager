package cz.muni.fi.pv168.musicManager;

import java.util.List;

/**
 * Created by Adam on 12.3.14.
 */
public interface MusicManager {

    public void addSongIntoAlbum(Song song, Album album) throws MusicManagerException;

    public void removeSongFromAlbum(Song song, Album album) throws MusicManagerException;

    public List<Song> getAllSongsFromAlbum(Album album) throws MusicManagerException;

    public SongManager getSongManager();

    public AlbumManager getAlbumManager();

    public void setSongManager(SongManager songManager);

    public void setAlbumManager(AlbumManager albumManager);
}