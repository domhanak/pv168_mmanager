package cz.muni.fi.pv168.musicManager;
import java.util.List;

/**
 * Created by Adam on 6.3.14.
 */
public interface AlbumManager {

    public Album createAlbum(Album album);

    public void deleteAlbum(Album album);

    public Album getAlbumById(long albumId);

    public void updateAlbum(Album album);

    public List<Album> findAlbumsByArtist(String artist);

    public List<Album> findAlbumsByGenre(String genre);

    public List<Album> findAlbumsBetweenYears(int fromYear, int toYear);
}
