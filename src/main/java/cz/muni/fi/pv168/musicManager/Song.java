package cz.muni.fi.pv168.musicManager;

/**
 * Represents a Song in DB.
 *
 * Created by Dominik Hanak on 5.3.2014.
 */
public class Song {
    private long songId;
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

        if (songId != song.songId) return false;

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

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
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
}
