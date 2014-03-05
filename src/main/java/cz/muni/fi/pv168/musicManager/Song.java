package cz.muni.fi.pv168.musicManager;

/**
 * Created by Hany on 5.3.2014.
 */
public class Song {
    private long songId;
    private String name;
    private int track;
    private int rank;
    private int length;

    public Song() {
    }

    public Song(long songId, String name, int track, int rank, int length) {
        this.songId = songId;
        this.name = name;
        this.track = track;
        this.rank = rank;
        this.length = length;
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
