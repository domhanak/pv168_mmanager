package cz.muni.fi.pv168.musicManager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Created by Adam on 11.3.14.
 */
public class AlbumManagerImplTest {

    AlbumManagerImpl albumManager;

    @Before
    public void setUp() throws Exception {
        albumManager = new AlbumManagerImpl();
    }

    @Test
    public void testCreateAlbum() throws Exception {
        try{
            albumManager.createAlbum(null);
            fail("NullPointerException in createAlbum not thrown");
        }catch (NullPointerException ex){}
    }

    @Test
    public void testDeleteAlbum() throws Exception {
        try{
            albumManager.deleteAlbum(null);
            fail("NullPointerException in deleteAlbum not thrown");
        }catch (NullPointerException ex){}
    }

    @Test
    public void testGetAbumById() throws Exception{
        try{
            albumManager.getAlbumById(-1);
            fail("IllegalArgumentException in getAlbumById not thrown");
        }catch (IllegalArgumentException ex){}
    }

    @Test
    public void testUpdateAlbum() throws Exception{
        try{
            albumManager.updateAlbum(null);
            fail("NullPointerException in updateAlbum not thrown");
        }catch (NullPointerException ex){};
    }

    @Test
    public void testFindAlbumsByArtist() throws Exception{
        try{
            albumManager.findAlbumsByArtist(null);
            fail("NullPointerException in findAlbumsByArtist not thrown");
        }catch (NullPointerException ex){}
    }

    @Test
    public void testFindAlbumsByGenre() throws Exception{
        try{
            albumManager.findAlbumsByGenre(null);
            fail("NullPointerException in findAlbumsByGenre not thrown");
        }catch (NullPointerException ex){}
    }

    @Test
    public void testFindAlbumsBetweenYears() throws Exception{
        try{
            albumManager.findAlbumsBetweenYears(2, 1);
            fail("IllegalArgumentException in findAlbumsBetweenYears not thrown");
        }catch (IllegalArgumentException ex){}
    }


}
