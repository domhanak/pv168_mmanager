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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Adam on 12.3.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Main.SpringConfig.class})
@Transactional
public class MusicManagerImplTest {

    @Autowired
    private MusicManager musicManager;

 /*
    static ApplicationContext ctx;
    private EmbeddedDatabase db;


    @BeforeClass
    public static void beforeClass() throws Exception{
        ctx = new AnnotationConfigApplicationContext(Main.SpringConfig.class);
    }

    @Before
    public void setUp() throws Exception{
        musicManager = ctx.getBean("musicManager", MusicManagerImpl.class);
        db = new EmbeddedDatabaseBuilder()
                .setType(DERBY)
                .addScript("my-schema.sql")
                .addScript("my-test-data.sql")
                .build();

        musicManager = new MusicManagerImpl(db);
    }

    @After
    public void tearDown() throws Exception{
        db.shutdown();
    }
*/
    @Test
    public void testAddSongIntoAlbum() throws Exception{

        Album album1 = newAlbum("Kronikar", "Rap", 2012, "Decko");
        Album album2 = newAlbum("Pirat", "Rap", 2014, "Monsignor Separ");

        Song song1 = newSong("Vitaj vo finale", 5, 7, 205);
        Song song2 = newSong("Do oci", 6, 9, 193);

        musicManager.addSongIntoAlbum(song1, album2);
        musicManager.addSongIntoAlbum(song2, album2);

        List<Song> actual = Arrays.asList(song1, song2);
        List<Song> result = musicManager.getAllSongsFromAlbum(album2);

        List<Song> emptyAlbum = musicManager.getAllSongsFromAlbum(album2);

        Collections.sort(actual, trackComparator);
        Collections.sort(result, trackComparator);

        assertEquals(result, actual);
        assertEquals(emptyAlbum, null);
    }

    @Test
    public void testAddSongIntoAlbumWithWrongAttributes()throws Exception{

        Song song1 = newSong("Vitaj vo finale", 5, 7, 205);
        Album album1 = newAlbum("Pirat", "Rap", 2014, "Monsignor Separ");

        musicManager.getAlbumManager().createAlbum(album1);
        musicManager.getSongManager().createSong(song1);

        try{
            musicManager.addSongIntoAlbum(null, album1);
            fail("NullPointerException in addSongIntoAlbum not thrown, song is null");
        }catch (NullPointerException ex){}

        try{
            musicManager.addSongIntoAlbum(song1, null);
            fail("NullPointerException in addSongIntoAlbum not thrown, album is null");
        }catch (NullPointerException ex){}
    }

    @Test
    public void testRemoveSongFromAlbum() throws Exception{

    }

    @Test
    public void testRemoveSongFromAlbumWithWrongAttributes() throws Exception{

    }

    @Test
    public void testGetAllSongsFromAlbum() throws Exception{

    }

    @Test
    public void testGetAllSongsFromAlbumWithWrongAttributes() throws Exception{

    }

    private static Album newAlbum(String name, String genre, int year, String artist){
        Album album = new Album();
        album.setName(name);
        album.setGenre(genre);
        album.setYear(year);
        album.setArtist(artist);

        return album;
    }

    private static Song newSong(String name, int track, int rank, int length){
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
