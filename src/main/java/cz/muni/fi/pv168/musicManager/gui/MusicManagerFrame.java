package cz.muni.fi.pv168.musicManager.gui;

import cz.muni.fi.pv168.musicManager.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;

/**
 * Created by Hany on 29.4.2014.
 */

public class MusicManagerFrame {

    private AlbumManager albumManager;
    private SongManager songManager;
    private MusicManager musicManager;

    final static Logger log = LoggerFactory.getLogger(MusicManagerFrame.class);

    private JTabbedPane MainPanel;
    private JPanel panel1;
    private JPanel musicTab;
    private JTable musicTable;
    private JTable songsInAlbumTable;
    private JButton deleteAlbumButton;
    private JPanel albumTab;
    private JTable albumTable;
    private JButton createAlbumButton;
    private JPanel addSongPanel;
    private JLabel headerLabel;
    private JTextField nameTextField;
    private JTextField genreTextField;
    private JLabel nameLabel;
    private JLabel rankLabel;
    private JLabel trackLabel;
    private JLabel lengthLabel;
    private JTextField artistTextField;
    private JTextField yearTextField;
    private JTable songTable;
    private JButton pairButton;
    private JButton deleteAlbumButton1;
    private JButton deleteSongButton;
    private JButton addAlbumButton;
    private JButton deleteSongFromAlbumButton;
    private JButton createSongButton;
    private JTextField songNameTextField;
    private JTextField songTrackTextField;
    private JTextField songLengthTextField;
    private JTextField songRankTextField;

    public JPanel getPanel() {
        return panel1;
    }

    public MusicManagerFrame() {
        ApplicationContext springContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        songManager = springContext.getBean("songManager", SongManager.class);
        albumManager = springContext.getBean("albumManager", AlbumManager.class);
        musicManager = springContext.getBean("musicManager", MusicManager.class);

        try {
            initializeMusicTables();
        } catch (AlbumException e) {
            e.printStackTrace();
        } catch (MusicManagerException e) {
            e.printStackTrace();
        }

        createAlbumButton.addActionListener(e2 -> {
            /* Get values from textfields */
            String name = nameTextField.getText();
            String genre = genreTextField.getText();
            String artist = artistTextField.getText();
            int year = Integer.parseInt(yearTextField.getText());

            /* Create new album to add into DB */
            Album album = new Album();
            album.setName(name);
            album.setArtist(artist);
            album.setGenre(genre);
            album.setYear(year);

            SwingWorker swingWorker = new SwingWorker() {
                @Override
                protected Album doInBackground() throws Exception {
                    try {
                        albumManager.createAlbum(album);
                        log.debug("Created album {}", album);
                    } catch (AlbumException e1) {
                        log.error("Album not created" + e1);
                        e1.printStackTrace();
                    }
                    return album;
                }

                @Override
                protected void done() {
                    AlbumTableModel aModel = (AlbumTableModel) albumTable.getModel();
                    aModel.addAlbum(album);
                }
            };
            swingWorker.execute();
            /*
            try {
                albumManager.createAlbum(album);
                log.debug("Created album {}", album);
            } catch (AlbumException e1) {
                log.error("Album not created" + e1);
                e1.printStackTrace();
            }
            AlbumTableModel aModel = (AlbumTableModel) albumTable.getModel();
            aModel.addAlbum(album);*/
        });

        createSongButton.addActionListener(e -> {
            String name = songNameTextField.getText();
            int rank = Integer.parseInt(songRankTextField.getText());
            int track = Integer.parseInt(songTrackTextField.getText());
            int length = Integer.parseInt(songLengthTextField.getText());

            Song song = new Song();
            song.setName(name);
            song.setRank(rank);
            song.setTrack(track);
            song.setLength(length);

            try {
                songManager.createSong(song);
            } catch (SongException e1) {
                log.error("");
                e1.printStackTrace();
            }
        });

        deleteAlbumButton1.addActionListener((e) -> {
            int row = albumTable.convertRowIndexToModel(albumTable.getSelectedRow());
            AlbumTableModel albumTableModel = (AlbumTableModel) albumTable.getModel();
            String name = (String) albumTableModel.getValueAt(row , 0);
            try {
                Album album = albumManager.getAlbumByName(name);
                albumManager.deleteAlbum(album);
            } catch (AlbumException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void initializeMusicTables() throws AlbumException, MusicManagerException
    {
        musicTable.setModel(new AlbumTableModel());
        AlbumTableModel model = (AlbumTableModel) musicTable.getModel();
        model.addAlbums(albumManager.findAllAlbums());
        musicTable.setOpaque(true);

        songsInAlbumTable.setModel(new SongTableModel());
        SongTableModel stModel = (SongTableModel) songsInAlbumTable.getModel();
        Album album = albumManager.getAlbumByName((String) model.getValueAt(0, 0));
        stModel.addSongs(musicManager.getAllSongsFromAlbum(album));
        songsInAlbumTable.setOpaque(true);

        albumTable.setModel(new AlbumTableModel());
        AlbumTableModel aModel = (AlbumTableModel) albumTable.getModel();
        aModel.addAlbums(albumManager.findAllAlbums());
        albumTable.setOpaque(true);

        songTable.setModel(new SongTableModel());
        SongTableModel sModel = (SongTableModel) songTable.getModel();
        sModel.addSongs(songManager.getAllSongs());
        songTable.setOpaque(true);
    }
}
