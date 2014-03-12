package cz.muni.fi.pv168.musicManager;

/**
<<<<<<< HEAD
 * Created by Hany, Adam on 5.3.2014.
=======
 * Created by Hany on 5.3.2014.
>>>>>>> 6a34b0e0bd627fc7cface62aa75227fa31f3e247
 */
public class Album {
    private long albumId;
    private String name;
    private String genre;
    private int year;
    private String artist;

    public Album() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album)) return false;

        Album album = (Album) o;

        if (albumId != album.albumId) return false;
        if (name != null ? !name.equals(album.name) : album.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (albumId ^ (albumId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
