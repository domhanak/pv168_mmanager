package cz.muni.fi.pv168.musicManager.api;

/**
 * Created by Adam on 19.3.14.
 */

import cz.muni.fi.pv168.musicManager.gui.MusicManagerFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class Main {

    final static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)
    {
        log.info("Application started.");
        EventQueue.invokeLater(() -> {
            JFrame mainAppFrame = new JFrame();
            MusicManagerFrame musicManFrame = new MusicManagerFrame();
            musicManFrame.getPanel().setPreferredSize(new Dimension(800, 600));
            mainAppFrame.setContentPane(musicManFrame.getPanel());
            mainAppFrame.pack();
            mainAppFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mainAppFrame.setVisible(true);
      });
    }
}

