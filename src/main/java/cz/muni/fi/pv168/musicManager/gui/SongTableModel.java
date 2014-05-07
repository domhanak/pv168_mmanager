package cz.muni.fi.pv168.musicManager.gui;

import cz.muni.fi.pv168.musicManager.api.Song;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Table model for Song entity.
 *
 * Created by Dominik Hanák on 30.4.2014
 */
public class SongTableModel extends AbstractTableModel {

    private List<Song> songs = new ArrayList<>();

    public void addSongs(List<Song> songs)
    {
        this.songs.addAll(songs);
    }

    @Override
    public int getRowCount() {
        return songs.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Name";
            case 1:
                return "Rank";
            case 2:
                return "Track";
            case 3:
                return "Length(sec)";
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Song song = songs.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return song.getName();
            case 1:
                return song.getRank();
            case 2:
                return song.getTrack();
            case 3:
                return song.getLength();
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
}