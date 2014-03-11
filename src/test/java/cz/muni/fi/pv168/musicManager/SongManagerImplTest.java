package cz.muni.fi.pv168.musicManager;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Hany on 5.3.2014.
 */
public class SongManagerImplTest {

    SongManagerImpl songManager;
    Song song;

    @Before
    public void setUp() throws Exception {
        songManager = new SongManagerImpl();
    }

    @Test
    public void testCreateSong() {
        Song song = newSong(0, "Raz,dva", 0, 0, 845);
        songManager.createSong(song);

        Long songId = song.getSongId();
        Song result = songManager.getSongById(songId);
        assertEquals(song, result);
        assertNotSame(song, result);
    }

    @Test
    public void testDeleteSong() {
        Song song = newSong(0, "Raz,dva", 0, 0, 845);
        Song song2 = newSong(1, "Raz,dva,tri", 1, 2, 846);
        songManager.createSong(song);
        songManager.createSong(song2);
        Long songId = song.getSongId();

        song = songManager.getSongById(songId);
        song.setTrack(45);
        songManager.updateSong(song);
        assertEquals(45, song.getTrack());
        assertEquals(0 , song.getRank());
        assertEquals(845, song.getLength());
        assertEquals("Raz,dva", song.getName());

        song = songManager.getSongById(songId);
        song.setName("Osem");
        songManager.updateSong(song);
        assertEquals(45, song.getTrack());
        assertEquals(0 , song.getRank());
        assertEquals(845, song.getLength());
        assertEquals("Osem", song.getName());

        song = songManager.getSongById(songId);
        song.setName(null);
        songManager.updateSong(song);
        assertEquals(45, song.getTrack());
        assertEquals(0 , song.getRank());
        assertEquals(845, song.getLength());
        assertNull(song.getName());

        song = songManager.getSongById(songId);
        song.setRank(4);
        songManager.updateSong(song);
        assertEquals(45, song.getTrack());
        assertEquals(4 , song.getRank());
        assertEquals(845, song.getLength());
        assertEquals("Osem", song.getName());

        song = songManager.getSongById(songId);
        song.setLength(360);
        songManager.updateSong(song);
        assertEquals(45, song.getTrack());
        assertEquals(0 , song.getRank());
        assertEquals(360, song.getLength());
        assertEquals("Osem", song.getName());
    }

    @Test
    public void textDeleteSong() {
        Song song = newSong(0, "Raz,dva", 0, 0, 845);
        Song song2 = newSong(1, "Raz,dva,tri", 1, 2, 846);

        songManager.createSong(song);
        songManager.createSong(song2);

        assertNotNull(songManager.getSongById(song.getSongId()));
        assertNotNull(songManager.getSongById(song2.getSongId()));

        songManager.deleteSong(song);

        assertNull(songManager.getSongById(song.getSongId()));
        assertNotNull(songManager.getSongById(song2.getSongId()));
    }

    @Test
    public void testDeleteSongWithWrongAttributes() throws Exception {
        Song song = newSong(0, "Raz, dva", 3, 14, 845);

        try {
            songManager.deleteSong(null);
            fail("IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException ex) {};

        try {
            song.setSongId(-1);
            songManager.deleteSong(song);
            fail("IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException ex) {};

        try {
            song.setSongId(1l);
            songManager.deleteSong(song);
            fail("IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException ex) {};
    }

    @Test
    public void testUpdateSongWithWrongAtrributtes() throws Exception {

        Song song = newSong(0, "Raz, dva", 3, 14, 845);
        songManager.createSong(song);
        Long songId = song.getSongId();

        try {
            songManager.updateSong(null);
            fail("IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException ex) {};

        try {
            song = songManager.getSongById(songId);
            song.setSongId(songId - 1);
            songManager.updateSong(song);
            fail();
        } catch (IllegalArgumentException ex) {};

        try {
            song = songManager.getSongById(songId);
            song.setRank(-1);
            songManager.updateSong(song);
        } catch (IllegalArgumentException ex) {};

        try {
            song = songManager.getSongById(songId);
            song.setTrack(-1);
            songManager.updateSong(song);
        } catch (IllegalArgumentException ex) {};

        try {
            song = songManager.getSongById(songId);
            song.setLength(-1);
            songManager.updateSong(song);
        } catch (IllegalArgumentException ex) {};
    }


    @Test
    public void testAddSongWithWrongAttributes() throws Exception {
        try {
            songManager.getSongById(-1);
            fail("IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException ex) {};

        Song song = newSong(0, "Raz, dva", 3, 14, 845);
        song.setSongId(1l);
        try {
            songManager.createSong(song);
            fail();
        } catch (IllegalArgumentException ex) {};

        song = newSong(-1, "Raz, dva", 3, 14, 845);
        try {
            songManager.createSong(song);
            fail();
        } catch (IllegalArgumentException ex) {};

        song = newSong(0, "", 3, 14, 845);
        try {
            songManager.createSong(song);
            fail();
        } catch (IllegalArgumentException ex) {};

        song = newSong(0, "Raz, dva", -1, 14, 845);
        try {
            songManager.createSong(song);
            fail();
        } catch (IllegalArgumentException ex) {};

        song = newSong(0, "Raz, dva", 3, -1, 845);
        try {
            songManager.createSong(song);
            fail();
        } catch (IllegalArgumentException ex) {};

        song = newSong(0, "Raz, dva", 3, 14, -1);
        try {
            songManager.createSong(song);
            fail();
        } catch (IllegalArgumentException ex) {};
    }

    @Test
    public void testGetAllSongs() {
        assertTrue(songManager.getAllSongs().isEmpty());

        Song song = newSong(0, "Raz,dva", 0, 0, 845);
        Song song2 = newSong(1, "Raz,dva,tri", 1, 2, 846);

        songManager.createSong(song);
        songManager.createSong(song2);

        List <Song> expected = Arrays.asList(song, song2);
        List <Song> actual = songManager.getAllSongs();

        Collections.sort(actual, idComparator);
        Collections.sort(expected , idComparator);

        assertEquals(expected, actual);
    }

    private static Song newSong(long albumId, String name, int rank, int track, int length) {
        Song song = new Song();
        song.setSongId(albumId);
        song.setName(name);
        song.setRank(rank);
        song.setTrack(track);
        song.setLength(length);

        return song;
    }

    private static Comparator<Song> idComparator = new Comparator<Song>() {

        @Override
        public int compare(Song object1, Song object2) {
            return Long.valueOf(object1.getSongId()).compareTo(Long.valueOf(object2.getSongId()));
        }
    };
}
