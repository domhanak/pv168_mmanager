package cz.muni.fi.pv168.musicManager.api;

/**
 * Represents entity Album.
 *
 * @author Adam Ryvola
 */
public class Album {
    private long albumId;
    private String name;
    private String genre;
    private int published;
    private String artist;

    public Album() {}

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Album{");
        sb.append("albumId=").append(albumId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", genre='").append(genre).append('\'');
        sb.append(", year=").append(published);
        sb.append(", artist='").append(artist).append('\'');
        sb.append('}');
        return sb.toString();
    }

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
        return published;
    }

    public void setYear(int year) {
        this.published = year;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
