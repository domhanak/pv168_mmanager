package cz.muni.fi.pv168.musicManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Adam on 12.3.14.
 */
public class MusicManagerImpl implements MusicManager{

    private JdbcTemplate jdbc;
    public static final Logger log = LoggerFactory.getLogger(MusicManagerImpl.class);

    private AlbumManager albumManager;
    private SongManager songManager;

    public MusicManagerImpl(DataSource dataSource) throws MusicManagerException
    {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    public void setAlbumManager(AlbumManager albumManager){
        this.albumManager = albumManager;
    }

    public void setSongManager(SongManager songManager){
        this.songManager = songManager;
    }

    public AlbumManager getAlbumManager(){
        return albumManager;
    }

    public SongManager getSongManager() {
        return songManager;
    }

    @Override
    public void addSongIntoAlbum(Song song, Album album) {

        if (album == null){
            throw new NullPointerException("In addSongIntoAlbum album is null");
        }

        if (song == null){
            throw new NullPointerException("In addSongIntoAlbum song is null");
        }

        log.debug("addSongIntoAlbum({})", song, album);

        SimpleJdbcInsert songIntoAlbumInsert = new SimpleJdbcInsert(jdbc)
                .withTableName("music")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("albumId", album.getAlbumId())
                .addValue("songId", song.getId());

        Number id = songIntoAlbumInsert.executeAndReturnKey(parameters);
    }

    @Override
    public void removeSongFromAlbum(Song song, Album album) {

        if (album == null){
            throw new NullPointerException("In removeSongFromAlbum album is null");
        }

        if (song == null){
            throw new NullPointerException("In removeSongFromAlbum song is null");
        }

        jdbc.update("DELETE FROM music WHERE songId=? AND albumId=?", song.getId(),album.getAlbumId());

    }

    @Override
    public List<Song> getAllSongsFromAlbum(Album album) {

        if (album == null){
            throw new NullPointerException("In getAllSongsFromAlbum album is null");
        }

        return jdbc.query("SELECT * FROM music WHERE albumId=?", musicMapper, album.getAlbumId());

    }

    private RowMapper<Song> musicMapper = (rs, rowNum) -> {
        Song song = new Song();
        song.setId(rs.getLong("id"));
        song.setName(rs.getString("name"));
        song.setTrack(rs.getInt("track"));
        song.setRank(rs.getInt("rank"));
        song.setLength(rs.getInt("length"));
        return song;
    };
}