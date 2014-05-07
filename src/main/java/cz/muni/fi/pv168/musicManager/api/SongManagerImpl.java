package cz.muni.fi.pv168.musicManager.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.List;

/**
 * Song manager  provides communication between DB using SpringJDBC.
 * Contains basic CRUD operations needed to manipulate the DB of  songs.
 *
 * Created by Hany on 5.3.2014.
 */
public class SongManagerImpl implements SongManager {
    private final static Logger log = LoggerFactory.getLogger(SongManagerImpl.class);
    private JdbcTemplate jdbc;

    /**
     * @param ds
     */
    public SongManagerImpl(DataSource ds) {
        jdbc = new JdbcTemplate(ds);
    }

    @Override
    public Song createSong(Song song) throws SongException
    {
        if (song == null) {
            log.error("Tried to create song that is null. {}", song);
            throw new IllegalArgumentException("In createSong: song is null");
        }
        if (song.getName().isEmpty()) {
            throw new IllegalArgumentException("In createSong: song's name is empty.");
        }
        if (song.getRank() < 0) {
            throw new IllegalArgumentException("In createSong: rank is negative");
        }
        if (song.getTrack() < 0) {
            throw new IllegalArgumentException("In createSong: track si negative.");
        }
        if (song.getLength() < 0) {
            throw new IllegalArgumentException("In createSong: length si negative.");
        }

        log.debug("Create song {}", song);
        SimpleJdbcInsert insertSong = new SimpleJdbcInsert(jdbc)
                .withTableName("songs").usingGeneratedKeyColumns("id");

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", song.getName())
                .addValue("rank", song.getRank())
                .addValue("track", song.getTrack())
                .addValue("length", song.getLength());

        Number id = insertSong.executeAndReturnKey(parameters);
        song.setId(id.longValue());

        return song;
    }

    @Override
    public void deleteSong(Song song) throws SongException
    {
        if (song == null) {
            throw new IllegalArgumentException("In deleteSong: song is null");
        }
        if (song.getId() < 0) {
            throw new IllegalArgumentException("In deleteSong: song id < 0");
        }

        log.debug("deleteSong {}", song);
        jdbc.update("DELETE FROM SONGS WHERE id=?", song.getId());
    }

    @Override
    public void updateSong(Song song) throws  SongException
    {
        if (song == null) {
            throw new IllegalArgumentException("In updateSong: song is null");
        }
        if (song.getName() == null) {
            throw new IllegalArgumentException("updateSong: name of song is null");
        }
        if (song.getTrack() < 0) {
            throw new IllegalArgumentException("updateSong: track of song < 0");
        }
        if (song.getRank() < 0) {
            throw new IllegalArgumentException("updateSong: rank of song < 0");
        }
        if (song.getLength() < 0) {
            throw new IllegalArgumentException("updateSong: length of song < 0");
        }

        log.debug("updateSong {}" , song);
        jdbc.update("UPDATE SONGS SET name=?, rank=?, track=?, length=?",
                song.getName(), song.getRank(), song.getTrack(), song.getLength());
    }

    @Override
    public List<Song> getAllSongs()
    {
        return jdbc.query("SELECT * FROM SONGS", songMapper);
    }

    @Override
    public Song getSongByName(String name) throws SongException
    {
        if (name == null) {
            throw new IllegalArgumentException("In getSongById: name is null!");
        }

        if (name.isEmpty()) {
            throw new IllegalArgumentException("In getSongById: name is empty!");
        }

        log.debug("getSongByName {}", name);
        try {
            return jdbc.queryForObject("SELECT * FROM SONGS WHERE name=?", songMapper, name);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Song getSongById(Long id) throws SongException
    {
        if (id < 0) {
            throw new IllegalArgumentException("In getSongById: id < 0!");
        }

        log.debug("getSongById {}", id);
        try {
            return jdbc.queryForObject("SELECT * FROM SONGS WHERE id=?", songMapper, id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private RowMapper<Song> songMapper = (rs, rowNum) ->
    {
        Song song = new Song();
        song.setId(rs.getLong("id"));
        song.setName(rs.getString("name"));
        song.setRank(rs.getInt("rank"));
        song.setTrack(rs.getInt("track"));
        song.setLength(rs.getInt("length"));
        return song;
    };
}
