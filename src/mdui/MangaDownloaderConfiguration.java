/* MangaDownloader can help with Comics too
   Copyright (C) 2012 Hernán Parra

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package mdui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.Map;
import java.util.Properties;
import javax.swing.*;
import md.archivers.ArchiversFactory;
import md.model.*;

/**
 *
 * @author Hernan
 */
public class MangaDownloaderConfiguration {
    static private Map<String, Archiver> archivers = ArchiversFactory.archiversMap();

    static public MangaDownloaderConfiguration loadPreferences() {
        boolean savePreferences = false;
        MangaDownloaderConfiguration config = new MangaDownloaderConfiguration();

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("conf.properties"));
        } catch (IOException e) {
            savePreferences = true;
        }

        config.setSimultaneousDownloads(Integer.parseInt((String)properties.getProperty("simultaneous_downloads", "1")));
        config.setSaveTo((String)properties.getProperty("save_to", new File(".").getAbsolutePath()));
        config.setSelectedArchiver(archivers.get((String)properties.getProperty("archiver",".cbz")));

        if( savePreferences ) {
            savePreferences(config);
        }

        try {
            config.getSelectedArchiver().verifyPrerequisites();
        } catch (NoPrerequisitesException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage());
            System.exit(1);
        }
        return config;
    }

    static public void savePreferences(MangaDownloaderConfiguration config) {
        //TODO Tomar el directorio desde donde se está ejecutando el programa
        //URL url = MangaDownloaderUI.class.getProtectionDomain().getCodeSource().getLocation();
        Properties properties = new Properties();
        properties.put("archiver", config.getSelectedArchiver().getExtension());
        properties.put("simultaneous_downloads", String.valueOf(config.getSimultaneousDownloads()));
        properties.put("save_to", config.getSaveTo());
        try {
            properties.store(new FileOutputStream("conf.properties"), null);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Couldn't save the preferences: " + ex.getMessage());
        }
    }

    private Archiver selectedArchiver;
    private int simultaneousDownloads;
    private String saveTo;

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public int getSimultaneousDownloads() {
        return simultaneousDownloads;
    }

    public void setSimultaneousDownloads(int simultaneousDownloads) {
        int oldSimultaneousDownloads = this.simultaneousDownloads;
        this.simultaneousDownloads = simultaneousDownloads;
        support.firePropertyChange("simultaneousDownloads", oldSimultaneousDownloads, simultaneousDownloads);
    }

    public String getSaveTo() {
        return saveTo;
    }

    public void setSaveTo(String saveTo) {
        String oldSaveTo = this.saveTo;
        this.saveTo = saveTo;
        support.firePropertyChange("saveTo", oldSaveTo, saveTo);
    }

    Archiver getSelectedArchiver() {
        return selectedArchiver;
    }

    public void setSelectedArchiver(Archiver selectedArchiver) {
        Archiver oldSelectedArchiver = this.selectedArchiver;
        this.selectedArchiver = selectedArchiver;
        support.firePropertyChange("selectedArchiver", oldSelectedArchiver, selectedArchiver);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

}
