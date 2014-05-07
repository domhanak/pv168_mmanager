package cz.muni.fi.pv168.musicManager.api;

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

import static org.junit.Assert.*;

/**
 * Unit tests for Song manager using Spring.
 * <p/>
 * Created by Dominik and Adam.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringTestConfig.class})
@Transactional
public class SongManagerImplTest {

    @Autowired
    private SongManager songManager;

    @Test
    public void testCreateSong() throws SongException {
        Song song = newSong("Raz,dva", 0, 0, 845);
        songManager.createSong(song);

        Song result = songManager.getSongById(song.getId());

        assertEquals(song, result);
        assertNotSame(song, result);
    }

    @Test
    public void testUpdateSong() throws SongException {
        Song song = newSong("Raz,dva", 0, 0, 845);

        songManager.createSong(song);
        Long songId = song.getId();

        song = songManager.getSongById(songId);
        song.setTrack(45);
        songManager.updateSong(song);
        assertEquals(45, song.getTrack());
        assertEquals(0, song.getRank());
        assertEquals(845, song.getLength());
        assertEquals("Raz,dva", song.getName());

        song = songManager.getSongById(songId);
        song.setName("Osem");
        songManager.updateSong(song);
        assertEquals(45, song.getTrack());
        assertEquals(0, song.getRank());
        assertEquals(845, song.getLength());
        assertEquals("Osem", song.getName());

        song = songManager.getSongById(songId);
        song.setRank(4);
        songManager.updateSong(song);
        assertEquals(45, song.getTrack());
        assertEquals(4, song.getRank());
        assertEquals(845, song.getLength());
        assertEquals("Osem", song.getName());

        song = songManager.getSongById(songId);
        song.setLength(360);
        songManager.updateSong(song);
        assertEquals(45, song.getTrack());
        assertEquals(4, song.getRank());
        assertEquals(360, song.getLength());
        assertEquals("Osem", song.getName());
    }

    @Test
    public void testDeleteSong() throws SongException {
        Song song = newSong("Raz,dva", 0, 0, 845);
        Song song2 = newSong("Raz,dva,tri", 1, 2, 846);

        songManager.createSong(song);
        songManager.createSong(song2);

        assertNotNull(songManager.getSongById(song.getId()));
        assertNotNull(songManager.getSongById(song2.getId()));

        songManager.deleteSong(song);

        assertNull(songManager.getSongById(song.getId()));
        assertNotNull(songManager.getSongById(song2.getId()));
    }

    @Test
    public void testDeleteSongWithWrongAttributes() throws Exception {
        Song song = newSong("Raz, dva", 3, 14, 845);

        try {
            songManager.deleteSong(null);
            fail("IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException ex) {
        }

        try {
            song.setId(-1);
            songManager.deleteSong(song);
            fail("IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException ex) {
        }
    }

    @Test
    public void testUpdateSongWithWrongAttributes() throws Exception
    {

        Song song = newSong("Raz, dva", 3, 14, 845);
        songManager.createSong(song);
        Long songId = song.getId();

        try {
            songManager.updateSong(null);
            fail("IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException ex) {}

        try {
            song = songManager.getSongById(songId);
            song.setRank(-1);
            songManager.updateSong(song);
        } catch (IllegalArgumentException ex) {}

        try {
            song = songManager.getSongById(songId);
            song.setTrack(-1);
            songManager.updateSong(song);
        } catch (IllegalArgumentException ex) {}

        try {
            song = songManager.getSongById(songId);
            song.setLength(-1);
            songManager.updateSong(song);
        } catch (IllegalArgumentException ex) {}
    }


    @Test
    public void testAddSongWithWrongAttributes() throws Exception
    {
        Song song = newSong("Raz, dva", 3, 14, 845);

        song = newSong("", 3, 14, 845);
        try {
            songManager.createSong(song);
            fail();
        } catch (IllegalArgumentException ex) {}

        song = newSong("Raz, dva", -1, 14, 845);
        try {
            songManager.createSong(song);
            fail();
        } catch (IllegalArgumentException ex) {}

        song = newSong("Raz, dva", 3, -1, 845);
        try {
            songManager.createSong(song);
            fail();
        } catch (IllegalArgumentException ex) {}

        song = newSong("Raz, dva", 3, 14, -1);
        try {
            songManager.createSong(song);
            fail();
        } catch (IllegalArgumentException ex) {}
    }


    @Test
    public void testGetAllSongs() throws SongException
    {
        assertTrue(songManager.getAllSongs().isEmpty());

        Song song = newSong("Raz,dva", 0, 0, 845);
        Song song2 = newSong("Raz,dva,tri", 1, 2, 846);

        songManager.createSong(song);
        songManager.createSong(song2);

        List<Song> expected = Arrays.asList(song, song2);
        List<Song> actual = songManager.getAllSongs();

        Collections.sort(actual, idComparator);
        Collections.sort(expected, idComparator);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetSongByName() throws SongException
    {
        Song song = newSong("Raz,dva", 0, 0, 845);

        songManager.createSong(song);
        Song result = songManager.getSongByName("Raz,dva");

        assertEquals(song, result);
    }


    private static Song newSong(String name, int rank, int track, int length)
    {
        Song song = new Song();
        song.setName(name);
        song.setRank(rank);
        song.setTrack(track);
        song.setLength(length);
        return song;
    }

    private static Comparator<Song> idComparator = new Comparator<Song>() {
        @Override
        public int compare(Song object1, Song object2) {
            return Long.valueOf(object1.getId()).compareTo(Long.valueOf(object2.getId()));
        }
    };
}
