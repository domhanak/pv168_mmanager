package cz.muni.fi.pv168.musicManager.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Adam on 12.3.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringTestConfig.class})
@Transactional
public class MusicManagerImplTest {

    @Autowired
    private MusicManager musicManager;
    @Autowired
    private SongManager songManager;
    @Autowired
    private AlbumManager albumManager;

    @Test
    public void testAddSongIntoAlbum() throws Exception
    {

        Album album1 = newAlbum("Kronikar", "Rap", 2012, "Decko");
        Album album2 = newAlbum("Pirat", "Rap", 2014, "Monsignor Separ");

        Song song1 = newSong("Vitaj vo finale", 5, 7, 205);
        Song song2 = newSong("Do oci", 6, 9, 193);

        albumManager.createAlbum(album1);
        albumManager.createAlbum(album2);

        songManager.createSong(song1);
        songManager.createSong(song2);

        musicManager.addSongIntoAlbum(song1, album2);
        musicManager.addSongIntoAlbum(song2, album2);

        List<Song> actual = Arrays.asList(song1, song2);
        List<Song> result = musicManager.getAllSongsFromAlbum(album2);

        Collections.sort(actual, trackComparator);
        Collections.sort(result, trackComparator);

        assertEquals(result, actual);
    }

    @Test
    public void testAddSongIntoAlbumWithWrongAttributes()throws Exception
    {

        Song song1 = newSong("Vitaj vo finale", 5, 7, 205);
        Album album1 = newAlbum("Pirat", "Rap", 2014, "Monsignor Separ");

        albumManager.createAlbum(album1);
        songManager.createSong(song1);

        try {
            musicManager.addSongIntoAlbum(null, album1);
            fail("NullPointerException in addSongIntoAlbum not thrown, song is null");
        } catch (IllegalArgumentException ex){}

        try {
            musicManager.addSongIntoAlbum(song1, null);
            fail("NullPointerException in addSongIntoAlbum not thrown, album is null");
        } catch (IllegalArgumentException ex){}
    }

    @Test
    public void testRemoveSongFromAlbum() throws Exception
    {
        Song song1 = newSong("Vitaj vo finale", 5, 7, 205);
        Album album1 = newAlbum("Pirat", "Rap", 2014, "Monsignor Separ");

        albumManager.createAlbum(album1);
        songManager.createSong(song1);

        musicManager.addSongIntoAlbum(song1, album1);

        musicManager.removeSongFromAlbum(song1, album1);

        assertEquals(new ArrayList<Song>(), musicManager.getAllSongsFromAlbum(album1));
    }

    @Test
    public void testRemoveSongFromAlbumWithWrongAttributes() throws Exception
    {
        Song song1 = newSong("Vitaj vo finale", 5, 7, 205);
        Album album1 = newAlbum("Pirat", "Rap", 2014, "Monsignor Separ");

        albumManager.createAlbum(album1);
        songManager.createSong(song1);

        try {
            musicManager.addSongIntoAlbum(null, album1);
            fail("NullPointerException in addSongIntoAlbum not thrown, song is null");
        } catch (IllegalArgumentException ex){}

        try {
            musicManager.addSongIntoAlbum(song1, null);
            fail("NullPointerException in addSongIntoAlbum not thrown, album is null");
        } catch (IllegalArgumentException ex){}
    }

    @Test
    public void testGetAllSongsFromAlbum() throws Exception
    {
        Song song1 = newSong("Vitaj vo finale", 5, 7, 205);
        Song song2 = newSong("Do oci", 6, 9, 193);
        Song song3 = newSong("Do analu", 4, 2, 142);
        Album album1 = newAlbum("Pirat", "Rap", 2014, "Monsignor Separ");

        songManager.createSong(song1);
        songManager.createSong(song2);
        songManager.createSong(song3);
        albumManager.createAlbum(album1);

        musicManager.addSongIntoAlbum(song1, album1);
        musicManager.addSongIntoAlbum(song2, album1);
        musicManager.addSongIntoAlbum(song3, album1);

        List<Song> actual = Arrays.asList(song1, song2, song3);
        List<Song> result = musicManager.getAllSongsFromAlbum(album1);

        Collections.sort(actual, trackComparator);
        Collections.sort(result, trackComparator);

        assertEquals(actual, result);
    }

    @Test
    public void testGetAllSongsFromAlbumWithWrongAttributes() throws Exception
    {
        try{
            musicManager.getAllSongsFromAlbum(null);
            fail("IllegalArgumentException in getAllSongsFromAlbum not thrown, album is null");
        }catch (IllegalArgumentException ex) {}
    }



    private static Album newAlbum(String name, String genre, int year, String artist)
    {
        Album album = new Album();
        album.setName(name);
        album.setGenre(genre);
        album.setYear(year);
        album.setArtist(artist);
        return album;
    }

    private static Song newSong(String name, int track, int rank, int length)
    {
        Song song = new Song();
        song.setName(name);
        song.setTrack(track);
        song.setRank(rank);
        song.setLength(length);
        return song;
    }

    private static Comparator<Song> trackComparator = new Comparator<Song>(){
        @Override
        public int compare(Song object1, Song object2) {
            return Integer.valueOf(object1.getTrack()).compareTo(Integer.valueOf(object2.getTrack()));
        }
    };

}
