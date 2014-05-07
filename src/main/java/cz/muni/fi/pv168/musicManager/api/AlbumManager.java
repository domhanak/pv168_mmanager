package cz.muni.fi.pv168.musicManager.api;
import java.util.List;

/**
 * Created by Adam on 6.3.14.
 */
public interface AlbumManager {

    public Album createAlbum(Album album) throws AlbumException;

    public void deleteAlbum(Album album) throws AlbumException;

    public Album getAlbumById(long albumId) throws AlbumException;

    public void updateAlbum(Album album) throws AlbumException;

    public Album getAlbumByName(String name) throws AlbumException;

    public List<Album> findAlbumsByArtist(String artist) throws AlbumException;

    public List<Album> findAlbumsByGenre(String genre) throws AlbumException;

    public List<Album> findAlbumsBetweenYears(int fromYear, int toYear) throws AlbumException;

    public List<Album> findAllAlbums();

}
