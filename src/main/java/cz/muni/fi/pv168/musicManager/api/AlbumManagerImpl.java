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
 * Created by Adam on 6.3.14.
 */
public class AlbumManagerImpl implements AlbumManager {

    private JdbcTemplate jdbc;
    public static final Logger log = LoggerFactory.getLogger("AlbumManagerImpl");

    public AlbumManagerImpl(DataSource dataSource) throws AlbumException {
        this.jdbc = new JdbcTemplate(dataSource);
    }


    @Override
    public Album createAlbum(Album album) throws AlbumException{
        if (album == null){
            throw new NullPointerException("In createAlbum album is null");
        }

        log.debug("createAlbum({})", album);

        SimpleJdbcInsert albumInsert = new SimpleJdbcInsert(jdbc).withTableName("albums").usingGeneratedKeyColumns("id");
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", album.getName())
                .addValue("genre", album.getGenre())
                .addValue("artist", album.getArtist())
                .addValue("published", album.getYear());

        Number id = albumInsert.executeAndReturnKey(parameters);
        album.setAlbumId(id.intValue());

        return album;
    }

    @Override
    public void deleteAlbum(Album album) throws AlbumException{
        if (album == null){
            throw new IllegalArgumentException("In deleteAlbum album is null");
        }
        if (album.getAlbumId() < 0){
            throw new IllegalArgumentException("In deleteAlbum album id is < 0");
        }

        jdbc.update("DELETE FROM albums WHERE id=?", album.getAlbumId());
    }

    @Override
    public Album getAlbumById(long albumId) throws AlbumException{
        if (albumId < 0){
            throw new IllegalArgumentException("In getAlbumById albumId is lower then zero");
        }

        log.debug("getAlbumByID({})", albumId);

        try {
            return jdbc.queryForObject("SELECT * FROM albums WHERE id=?", albumMapper, albumId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Album getAlbumByName(String name) throws AlbumException{
        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("In getAlbumById albumId is lower then zero");
        }

        log.debug("", name);

        try {
            return jdbc.queryForObject("SELECT * FROM albums WHERE name=?", albumMapper, name);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateAlbum(Album album) throws AlbumException{
        if (album == null){
            throw new NullPointerException("In updateAlbum album is null");
        }

        if (album.getAlbumId() < 1){
            throw new IllegalArgumentException("In updateAlbum albumId is lower than one");
        }

        jdbc.update("UPDATE albums SET name=?, genre=?, artist=?, published=? WHERE id=?",
                album.getName(), album.getGenre(), album.getArtist(), album.getYear(), album.getAlbumId());
    }

    @Override
    public List<Album> findAlbumsByArtist(String artist) throws AlbumException
    {
        if (artist == null){
            throw new IllegalArgumentException("In findAlbumsByArtist artist is null");
        }
        if (artist.isEmpty()) {
            throw new IllegalArgumentException("In findAlbumsByArtist artist is empty");
        }

        return jdbc.query("SELECT * FROM albums WHERE artist=?", albumMapper, artist);
    }

    @Override
    public List<Album> findAlbumsByGenre(String genre) throws AlbumException
    {
        if (genre == null){
            throw new IllegalArgumentException("In findAlbumByGenre genre is null");
        }
        if (genre.isEmpty()){
            throw new IllegalArgumentException("In findAlbumByGenre genre is empty");
        }
        return jdbc.query("SELECT * FROM albums WHERE genre=?", albumMapper, genre);
    }

    @Override
    public List<Album> findAlbumsBetweenYears(int fromYear, int toYear) throws AlbumException{

        if(fromYear < 0){
            throw new IllegalArgumentException("In findAlbumsBetweenYears fromYear is lower then zero");
        }

        if(toYear < 0){
            throw new IllegalArgumentException("In findAlbumsBetweenYears toYear is lower then zero");
        }

        if (fromYear > toYear){
            throw new IllegalArgumentException("In findAlbumsBetweenYears fromYear is higher then toYear");
        }

        return jdbc.query("SELECT * FROM albums WHERE published<=? AND published>=?", albumMapper, toYear, fromYear);
    }

    @Override
    public List<Album> findAllAlbums() {

        log.debug("findAllCars()");

        return jdbc.query("SELECT * FROM albums", albumMapper);
    }

    private RowMapper<Album> albumMapper = (rs, rowNum) -> {
        Album album = new Album();
        album.setAlbumId(rs.getLong("id"));
        album.setName(rs.getString("name"));
        album.setGenre(rs.getString("genre"));
        album.setArtist(rs.getString("artist"));
        album.setYear(rs.getInt("published"));
        return album;
    };
}