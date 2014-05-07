package cz.muni.fi.pv168.musicManager.gui;

import cz.muni.fi.pv168.musicManager.api.Album;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Table model for album entity.
 *
 * Created by Dominik Hanák on 30.4.2014.
 */
public class AlbumTableModel extends AbstractTableModel {

    private List<Album> albums = new ArrayList<>();

    public void addAlbum(Album album)
    {
        albums.add(album);
    }

    public void addAlbums(List<Album> albums)
    {
        this.albums.addAll(albums);
    }

    @Override
    public int getRowCount() {
        return albums.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Album album = albums.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return album.getName();
            case 1:
                return album.getArtist();
            case 2:
                return album.getGenre();
            case 3:
                return album.getYear();
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Name";
            case 1:
                return "Artist";
            case 2:
                return "Genre";
            case 3:
                return "Year";
            default:
                throw new IllegalArgumentException("column");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return Integer.class;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
}
