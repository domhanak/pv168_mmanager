package cz.muni.fi.pv168.musicManager;

import java.util.List;

/**
 * Created by Adam on 6.3.14.
 */
public class AlbumManagerImpl implements AlbumManager {


    @Override
    public Album createAlbum(Album album) {
        if (album == null){
            throw new NullPointerException("In createAlbum album is null");
        }
        return album;
    }

    @Override
    public void deleteAlbum(Album album) {
        if (album == null){
            throw new NullPointerException("In deleteAlbum album is null");
        }

    }

    @Override
    public Album getAlbumById(long albumId) {
        if (albumId < 0){
            throw new IllegalArgumentException("In getAlbumById albumId is lower then zero");
        }

        return null;
    }

    @Override
    public void updateAlbum(Album album) {
        if (album == null){
            throw new NullPointerException("In updateAlbum album is null");
        }

    }

    @Override
    public List<Album> findAlbumsByArtist(String artist) {
        if (artist == null){
            throw new NullPointerException("In findAlbumsByArtist artist is null");
        }

        return null;
    }

    @Override
    public List<Album> findAlbumsByGenre(String genre) {
        if (genre == null){
            throw new NullPointerException("In findAlbumByGenre genre is null");
        }
        return null;
    }

    @Override
    public List<Album> findAlbumsBetweenYears(int fromYear, int toYear) {
        if(fromYear < 0){
            throw new IllegalArgumentException("In findAlbumsBetweenYears fromYear is lower then zero");
        }

        if(toYear < 0){
            throw new IllegalArgumentException("In findAlbumsBetweenYears toYear is lower then zero");
        }

        if (fromYear > toYear){
            throw new IllegalArgumentException("In findAlbumsBetweenYears fromYear is higher then toYear");
        }

        return null;
    }
}
