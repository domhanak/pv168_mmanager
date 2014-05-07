package cz.muni.fi.pv168.musicManager;

/**
 * Represents a Song.
 *
 * Created by Dominik Hanak on 5.3.2014.
 */
public class Song {
    private Long songId;
    private String name;
    private int track;
    private int rank;
    private int length;

    public Song() {}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Song song = (Song) obj;

        if (!(name.equals(((Song) obj).name))) {
            return false;
        } else if (length != song.length) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (songId ^ (songId >>> 32));
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Long getId() {
        return songId;
    }

    public void setId(long songId) {
        this.songId = songId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Song{");
        sb.append("songId=").append(songId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", track=").append(track);
        sb.append(", rank=").append(rank);
        sb.append(", length=").append(length);
        sb.append('}');
        return sb.toString();
    }
}
