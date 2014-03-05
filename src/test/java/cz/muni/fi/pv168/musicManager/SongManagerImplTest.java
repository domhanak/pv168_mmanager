package cz.muni.fi.pv168.musicManager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Created by Hany on 5.3.2014.
 */
public class SongManagerImplTest {

    SongManagerImpl songManager;

    @Before
    public void setUp() throws Exception {
        songManager = new SongManagerImpl();
    }

    @Test
    public void testCreateSong() throws Exception {
        try {
            songManager.createSong(null);
            fail("NullPointerException not thrown.");
        } catch (NullPointerException ex) {};
    }

    @Test
    public void testDeleteSong() throws Exception {
        try {
            songManager.deleteSong(null);
            fail("NullPointerException not thrown.");
        } catch (NullPointerException ex) {};
    }

    @Test
    public void testUpdateSong() throws Exception {
        try {
            songManager.updateSong(null);
            fail("NullPointerException not thrown.");
        } catch (NullPointerException ex) {};
    }

    @Test
    public void testGetSongById() throws Exception {

    }

    @Test
    public void testGetSongWithName() throws Exception {
        try {
            songManager.getSongByName(null);
            fail("NullPointerException not thrown.");
        } catch (NullPointerException ex) {};
    }
}
