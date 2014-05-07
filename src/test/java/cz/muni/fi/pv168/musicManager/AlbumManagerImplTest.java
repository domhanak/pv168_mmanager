package cz.muni.fi.pv168.musicManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Unit tests for AlbumManagerImpl using Spring.
 *
 * Created by Adam and Dominik
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Main.SpringConfig.class})
@Transactional
public class AlbumManagerImplTest {

    @Autowired
    private AlbumManager albumManager;

    @Test
    public void testCreateAlbum() throws Exception
    {
        Album album = newAlbum("Farbo slepo", "Rap", 2013, "Vec");
        albumManager.createAlbum(album);

        Album result = albumManager.getAlbumById(album.getAlbumId());

        assertEquals(album, result);
    }

    @Test
    public void testCreateAlbumWithWrongAttributes() throws Exception
    {
        try {
            albumManager.createAlbum(null);
            fail("NullPointerException in createAlbum not thrown");
        } catch(NullPointerException ex){}
    }

    @Test
    public void testDeleteAlbum() throws Exception
    {
        Album album = newAlbum("Farbo slepo", "Rap", 2013, "Vec");
        Album album2 = newAlbum("Sloboda", "Rap", 2013, "Delik");

        albumManager.createAlbum(album);
        albumManager.createAlbum(album2);

        assertThat(album.getAlbumId(), is(not(equalTo(null))));
        assertThat(album2.getAlbumId(), is(not(equalTo(null))));

        albumManager.deleteAlbum(album);

        assertThat(albumManager.getAlbumById(album.getAlbumId()), is(equalTo(null)));
        assertThat(albumManager.getAlbumById(album2.getAlbumId()), is(not(equalTo(null))));
    }

    @Test
    public void testDeleteAlbumWithWrongAttributes() throws Exception
    {
        Album album = newAlbum("Sloboda", "Rap", 2013, "Delik");

        try{
            albumManager.deleteAlbum(null);
            fail("IllegalArgumentException in deleteAlbum not thrown");
        } catch(IllegalArgumentException ex){}

        try{
            album.setAlbumId(-1);
            albumManager.deleteAlbum(album);
            fail("IllegalArgumentException in deleteAlbum not thrown");
        } catch(IllegalArgumentException ex){}

    }

    @Test
    public void testGetAlbumById() throws Exception
    {
        Album album = newAlbum("Strepy", "Rap", 2013, "Rest, DJ Fatte");
        albumManager.createAlbum(album);

        Album result = albumManager.getAlbumById(album.getAlbumId());
        assertEquals(album, result);
    }

    @Test
    public void testGetAlbumByIdWithWrongAttributes() throws Exception
    {
        try{
            albumManager.getAlbumById(-1);
            fail("IllegalArgumentException in getAlbumById not thrown");
        } catch (IllegalArgumentException ex){}
    }

    @Test
    public void testUpdateAlbum() throws Exception
    {
        Album album = newAlbum("Strepy", "Rap", 2013, "Rest, DJ Fatte");

        albumManager.createAlbum(album);

        Long albumId = album.getAlbumId();

        album = albumManager.getAlbumById(albumId);
        album.setName("Premiera");
        albumManager.updateAlbum(album);
        assertEquals("Premiera", album.getName());
        assertEquals("Rap", album.getGenre());
        assertEquals(2013, album.getYear());
        assertEquals("Rest, DJ Fatte", album.getArtist());

        album = albumManager.getAlbumById(albumId);
        album.setGenre("HIP-HOP");
        albumManager.updateAlbum(album);
        assertEquals("Premiera", album.getName());
        assertEquals("HIP-HOP", album.getGenre());
        assertEquals(2013, album.getYear());
        assertEquals("Rest, DJ Fatte", album.getArtist());

        album = albumManager.getAlbumById(albumId);
        album.setYear(2010);
        albumManager.updateAlbum(album);
        assertEquals("Premiera", album.getName());
        assertEquals("HIP-HOP", album.getGenre());
        assertEquals(2010, album.getYear());
        assertEquals("Rest, DJ Fatte", album.getArtist());

        album = albumManager.getAlbumById(albumId);
        album.setArtist("Ty Nikdy");
        albumManager.updateAlbum(album);
        assertEquals("Premiera", album.getName());
        assertEquals("HIP-HOP", album.getGenre());
        assertEquals(2010, album.getYear());
        assertEquals("Ty Nikdy", album.getArtist());

    }

    @Test
    public void testUpdateAlbumWithWrongAttributes() throws Exception
    {
        try{
            albumManager.updateAlbum(null);
            fail("NullPointerException in updateAlbum not thrown");
        } catch (NullPointerException ex){}
    }

    @Test
    public void testFindAlbumsByArtist() throws Exception
    {
        Album album1 = newAlbum("Kronikar", "Rap", 2012, "Decko");
        Album album2 = newAlbum("Pirat", "Rap", 2014, "Monsignor Separ");
        Album album3 = newAlbum("Buldozer", "Rap", 2013, "Monsignor Separ");
        albumManager.createAlbum(album1);
        albumManager.createAlbum(album2);
        albumManager.createAlbum(album3);

        List<Album> expected = Arrays.asList(album2, album3);
        List<Album> result = albumManager.findAlbumsByArtist("Monsignor Separ");

        assertEquals(expected, result);

    }

    @Test
    public void testFindAlbumsByArtistWithWrongAttributes() throws Exception
    {
        try{
            albumManager.findAlbumsByArtist(null);
            fail("IAE in findAlbumsByArtist not thrown");
        } catch (IllegalArgumentException ex){}

        try{
            albumManager.findAlbumsByArtist("");
            fail("IAE in findAlbumsByArtist not thrown");
        } catch (IllegalArgumentException ex){}
    }


    @Test
    public void testFindAlbumsByGenre() throws Exception{

        Album album = newAlbum("Highway to hell", "Rock", 1979, "AC/DC");
        Album album2 = newAlbum("Sloboda", "HIP-HOP", 2013, "Delik");
        albumManager.createAlbum(album);
        albumManager.createAlbum(album2);
        List<Album> expected = Arrays.asList(album);

        List<Album> result = albumManager.findAlbumsByGenre("Rock");

        assertEquals(expected, result);
    }

    @Test
    public void testFindAlbumsByGenreWithWrongAttributes() throws Exception {
        try{
            albumManager.findAlbumsByGenre(null);
            fail("IllegalArgumentException in findAlbumsByGenre not thrown");
        }catch (IllegalArgumentException ex){}

        try{
            albumManager.findAlbumsByGenre("");
            fail("IllegalArgumentException in findAlbumsByGenre not thrown");
        }catch (IllegalArgumentException ex){}
    }

    @Test
    public void testFindAlbumsBetweenYears() throws Exception{

        Album album = newAlbum("Farbo slepo", "Rap", 2013, "Vec");
        Album album2 = newAlbum("Sloboda", "Rap", 2013, "Delik");
        Album album3 = newAlbum("Cisla nepustia", "Rap", 2008, "H16");

        albumManager.createAlbum(album);
        albumManager.createAlbum(album2);
        albumManager.createAlbum(album3);

        List<Album> expected = Arrays.asList(album3);
        List<Album> found = albumManager.findAlbumsBetweenYears(2005, 2009);

        Collections.sort(expected, idComparator);
        Collections.sort(found, idComparator);

        assertEquals(expected, found);
    }

    @Test
    public void testFindAlbumsBetweenYearsWithWrongAttributes() throws Exception
    {
        try{
            albumManager.findAlbumsBetweenYears(2, 1);
            fail("IllegalArgumentException in findAlbumsBetweenYears not thrown");
        } catch (IllegalArgumentException ex){}

        try{
            albumManager.findAlbumsBetweenYears(-1, 1);
            fail("IllegalArgumentException in findAlbumsBetweenYears not thrown");
        } catch (IllegalArgumentException ex){}

        try{
            albumManager.findAlbumsBetweenYears(2, -1);
            fail("IllegalArgumentException in findAlbumsBetweenYears not thrown");
        } catch (IllegalArgumentException ex){}
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


    private static Comparator<Album> idComparator = new Comparator<Album>() {

        @Override
        public int compare(Album object1, Album object2) {
            return Long.valueOf(object1.getAlbumId()).compareTo(Long.valueOf(object2.getAlbumId()));
        }
    };


}
