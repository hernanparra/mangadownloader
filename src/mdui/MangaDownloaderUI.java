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

import java.beans.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import javax.swing.*;
import md.*;
import md.archivers.*;
import md.model.*;
import md.model.EventsHandler.EventTypes;
import md.parsers.*;
import mdui.support.*;
import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author Hernan
 */
//UI Soporte para descargar a partir de una URL copiada
//UI Agregar soporte para seleccionar más SiteParsers
//UI Mostrar todos los eventos generados desde la API
//UI Proponer un nombre de archivo acorde a los capítulos seleccionados
//UI Utilización de esquema de binding
//UI Log de eventos
//UI Soporte batch
//UI Check for RAR.EXE in path
//UI Permitir seleccionar el archivador y su extensión
//UI Permitir ver lista de tareas pendientes de download
//UI Log individual por tarea
//UI Permitir configurar la cantidad de downloads simultaneos
//UI Permitir seleccionar el directorio donde se guardan los archivos
//UI Extraer la configuración a un objeto distinto de MangaDownloaderUI
//UI Botón generación volumenes a partir de una multiselección
//FIXME Poner como default el primer download si no hay seleccionado ninguno
//TODO UI Permitir cancelar una tarea pendiente
//TODO UI Permitir cancelar una tarea en ejecución
//TODO UI Agregar i18n
//TODO UI Restringir a un máximo la cantidad de downloads simultaneos por servidor
//TODO UI Leer clipboard para detectar URLs? Debiera preguntar a los site parsers quien la soporta
public class MangaDownloaderUI extends javax.swing.JFrame {

    private MangaDownloaderConfiguration config;
    private DownloadList tasks;
    private BindableInMemoryLogger logger = null;
    private List<SiteParser> siteParsers;
    private List<MangaSeriesInfo> mangas = null;
    private List<MangaChaptersInfo> chapters = null;
    private final JFileChooser fileChooser = new JFileChooser();

    public MangaDownloaderUI(MangaDownloaderConfiguration config) {
        this.config = config;
        tasks = new DownloadList(config);
        logger = new BindableInMemoryLogger();
        logger.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                statusTextPane.setText(logger.getLog());
            }
        });

        setSiteParsers(new DefaultSiteParsersRegistry().listParsers());
        initComponents();
        progressBar.setVisible(false);
        archiversComboBox.setSelectedItem(config.getSelectedArchiver());
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    /**
     * Constructor to help testing
     * 
     * @param siteParsers replaces siteParsers with dummy ones for testing
     */
    public MangaDownloaderUI(MangaDownloaderConfiguration config, List<SiteParser> parsers) {
        this(config);
        logger.log("TEST MODE: ON");
        //bindingGroup.addBindingListener(new LoggingBindingListener(statusTextPane));
        setSiteParsers(parsers);
    }

    //<editor-fold defaultstate="collapsed" desc="Setters - Getters for Binding">
    public Converter<Date, String> getDateToTimeConverter() {
        return new Converter<Date, String>() {
            @Override
            public String convertForward(Date value) {
                return String.format("%tT", value);
            }

            @Override
            public Date convertReverse(String value) {
                return new Date();
            }
        };
    }

    public MangaDownloaderConfiguration getConfig() {
        return config;
    }

    public void setConfig(MangaDownloaderConfiguration config) {
        this.config = config;
    }

    public List<SiteParser> getSiteParsers() {
        return siteParsers;
    }

    public final void setSiteParsers(List<SiteParser> siteParsers) {
        List<SiteParser> oldSiteParsers = this.siteParsers;
        this.siteParsers = siteParsers;
        firePropertyChange("siteParsers", oldSiteParsers, siteParsers);
    }

    public List<MangaSeriesInfo> getMangas() {
        return mangas;
    }

    public void setMangas(List<MangaSeriesInfo> mangas) {
        List<MangaSeriesInfo> oldMangas = this.mangas;
        this.mangas = mangas;
        firePropertyChange("mangas", oldMangas, mangas);
    }

    public List<MangaChaptersInfo> getChapters() {
        return chapters;
    }

    public void setChapters(List<MangaChaptersInfo> chapters) {
        List<MangaChaptersInfo> oldChapters = this.chapters;
        this.chapters = chapters;
        firePropertyChange("chapters", oldChapters, chapters);
    }

    public BindableInMemoryLogger getLog() {
        return logger;
    }

    public List<Archiver> getArchiversAsList() {
        return ArchiversFactory.listArchivers();
    }

    public DownloadList getTasks() {
        return tasks;
    }
    //</editor-fold>

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jLabel2 = new javax.swing.JLabel();
        tabsPane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        mangaSitesCombo = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        chaptersList = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        statusTextPane = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        mangaURLText = new javax.swing.JTextField();
        exitButton = new javax.swing.JButton();
        showChaptersButton = new javax.swing.JButton();
        downloadSelectedButton = new javax.swing.JButton();
        downloadVolumeButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jobStatusTable = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jobLogTextPane = new javax.swing.JTextPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        archiversComboBox = new javax.swing.JComboBox();
        cancelConfigButton = new javax.swing.JButton();
        saveConfigButton = new javax.swing.JButton();
        savePathTextField = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        simultaneousTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Manga Downloader");
        setBounds(new java.awt.Rectangle(0, 0, 800, 600));

        jLabel2.setText("MangaDownloader v1.0");

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${siteParsers}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, mangaSitesCombo);
        bindingGroup.addBinding(jComboBoxBinding);

        mangaSitesCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mangaSitesComboActionPerformed(evt);
            }
        });

        mangaList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${mangas}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, mangaList, "");
        jListBinding.setSourceNullValue(new ArrayList());
        bindingGroup.addBinding(jListBinding);

        mangaList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                mangaListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(mangaList);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${chapters}");
        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, chaptersList);
        jListBinding.setSourceNullValue(new ArrayList());
        bindingGroup.addBinding(jListBinding);

        jScrollPane1.setViewportView(chaptersList);

        statusTextPane.setEditable(false);
        statusTextPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        statusTextPane.setMaximumSize(new java.awt.Dimension(500, 500));
        statusTextPane.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                statusTextPaneFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                statusTextPaneFocusLost(evt);
            }
        });
        jScrollPane3.setViewportView(statusTextPane);

        jLabel1.setText("Manga URL:");

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        showChaptersButton.setText("Show Chapters");
        showChaptersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showChaptersButtonActionPerformed(evt);
            }
        });

        downloadSelectedButton.setText("Download selected");
        downloadSelectedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadSelectedButtonActionPerformed(evt);
            }
        });

        downloadVolumeButton.setText("Download volume");
        downloadVolumeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadVolumeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                            .addComponent(mangaSitesCombo, javax.swing.GroupLayout.Alignment.TRAILING, 0, 262, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mangaURLText, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(exitButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                    .addComponent(downloadSelectedButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                    .addComponent(showChaptersButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                    .addComponent(downloadVolumeButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {downloadSelectedButton, exitButton, showChaptersButton});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mangaSitesCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(mangaURLText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showChaptersButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(downloadSelectedButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(downloadVolumeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exitButton))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {downloadSelectedButton, exitButton, showChaptersButton});

        tabsPane.addTab("Download", jPanel1);

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setDividerSize(8);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jLabel3.setText("Jobs status");

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${tasks.list}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, jobStatusTable, "tableBinding");
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${name}"));
        columnBinding.setColumnName("Name");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${status}"));
        columnBinding.setColumnName("Status");
        columnBinding.setColumnClass(mdui.support.DownloadStatus.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${scheduledAt}"));
        columnBinding.setColumnName("Scheduled At");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${startTime}"));
        columnBinding.setColumnName("Start Time");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${endTime}"));
        columnBinding.setColumnName("End Time");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane4.setViewportView(jobStatusTable);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
                    .addComponent(jLabel3))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setTopComponent(jPanel4);

        jLabel4.setText("Job log");

        jobLogTextPane.setEditable(false);
        jobLogTextPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jobLogTextPane.setMaximumSize(new java.awt.Dimension(500, 500));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jobStatusTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.log}"), jobLogTextPane, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jobLogTextPane.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jobLogTextPaneFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jobLogTextPaneFocusLost(evt);
            }
        });
        jScrollPane5.setViewportView(jobLogTextPane);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
                    .addComponent(jLabel4))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel5);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );

        tabsPane.addTab("Jobs", jPanel2);

        jLabel5.setText("Archives extension");

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${archiversAsList}");
        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, archiversComboBox);
        bindingGroup.addBinding(jComboBoxBinding);

        cancelConfigButton.setText("Cancel");
        cancelConfigButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelConfigButtonActionPerformed(evt);
            }
        });

        saveConfigButton.setText("Ok");
        saveConfigButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveConfigButtonActionPerformed(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${config.saveTo}"), savePathTextField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        browseButton.setText("Browse");
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Save files to");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${config.simultaneousDownloads}"), simultaneousTextField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel7.setText("Simultaneous Downloads");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 544, Short.MAX_VALUE)
                        .addComponent(saveConfigButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelConfigButton))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                            .addComponent(jLabel7))
                        .addGap(70, 70, 70)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(savePathTextField)
                                .addGap(18, 18, 18)
                                .addComponent(browseButton))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(archiversComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(simultaneousTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelConfigButton, saveConfigButton});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(archiversComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(browseButton)
                    .addComponent(savePathTextField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(simultaneousTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 359, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelConfigButton)
                    .addComponent(saveConfigButton))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cancelConfigButton, saveConfigButton});

        tabsPane.addTab("Configuration", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(tabsPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabsPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, progressBar});

        setVisible(false);

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void mangaListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_mangaListValueChanged
        if (evt.getValueIsAdjusting() == false) {
            if (mangaList.getSelectedIndex() != -1) {
                MangaSeriesInfo mangaInfo = getMangas().get(mangaList.getSelectedIndex());
                retrieveChaptersInfoWorker(mangaInfo.url).execute();
            }
        }
    }//GEN-LAST:event_mangaListValueChanged

    private void showChaptersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showChaptersButtonActionPerformed
        if( !(mangaURLText.getText().equals("")) ) {
            retrieveChaptersInfoWorker(mangaURLText.getText()).execute();
        }
    }//GEN-LAST:event_showChaptersButtonActionPerformed

    private void downloadSelectedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadSelectedButtonActionPerformed
        int[] indx = chaptersList.getSelectedIndices();
        List<MangaChaptersInfo> selectedChapters;
        MangaSeriesInfo msi;
        String archiveName;

        for (int i = 0; i < indx.length; i++) {
            selectedChapters = new ArrayList<MangaChaptersInfo>();
            selectedChapters.add(chapters.get(indx[i]));
            msi = (MangaSeriesInfo) mangaList.getModel().getElementAt(mangaList.getSelectedIndex());
            archiveName = msi.name + MangaDownloader.automaticVolumeName(selectedChapters);
            tasks.add(new RetrieveChaptersWorker(archiveName, selectedChapters, logger, progressBar));
            logger.log(archiveName + " selected.");
        }

    }//GEN-LAST:event_downloadSelectedButtonActionPerformed

    private void mangaSitesComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mangaSitesComboActionPerformed
        int index = mangaSitesCombo.getSelectedIndex();
        if (index == -1 || index == 0) {
            setMangas(new ArrayList<MangaSeriesInfo>());
            setChapters(new ArrayList<MangaChaptersInfo>());
            return;
        }
        retrieveSeriesInfoWorker((SiteParser) mangaSitesCombo.getSelectedItem()).execute();
    }//GEN-LAST:event_mangaSitesComboActionPerformed

private void statusTextPaneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_statusTextPaneFocusGained
    statusTextPane.setAutoscrolls(false);
}//GEN-LAST:event_statusTextPaneFocusGained

private void statusTextPaneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_statusTextPaneFocusLost
    statusTextPane.setAutoscrolls(true);
}//GEN-LAST:event_statusTextPaneFocusLost

    private void jobLogTextPaneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jobLogTextPaneFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jobLogTextPaneFocusGained

    private void jobLogTextPaneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jobLogTextPaneFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jobLogTextPaneFocusLost

    private void saveConfigButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveConfigButtonActionPerformed
        config.setSelectedArchiver((Archiver)archiversComboBox.getSelectedItem());
        MangaDownloaderConfiguration.savePreferences(config);
        tabsPane.setSelectedIndex(0);
    }//GEN-LAST:event_saveConfigButtonActionPerformed

    private void cancelConfigButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelConfigButtonActionPerformed
        archiversComboBox.setSelectedItem(config.getSelectedArchiver());
        tabsPane.setSelectedIndex(0);
    }//GEN-LAST:event_cancelConfigButtonActionPerformed

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        fileChooser.setCurrentDirectory(new File(config.getSaveTo()));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            config.setSaveTo(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_browseButtonActionPerformed

    private void downloadVolumeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadVolumeButtonActionPerformed
        int[] indx = chaptersList.getSelectedIndices();

        List<MangaChaptersInfo> selectedChapters = new ArrayList<MangaChaptersInfo>();
        for (int i = 0; i < indx.length; i++) {
            selectedChapters.add(chapters.get(indx[i]));
        }

        MangaSeriesInfo msi = (MangaSeriesInfo) mangaList.getModel().getElementAt(mangaList.getSelectedIndex());
        String archiveName = msi.name + MangaDownloader.automaticVolumeName(selectedChapters);

        tasks.add(new RetrieveChaptersWorker(archiveName, selectedChapters, logger, progressBar));
        logger.log(archiveName + " selected.");
    }//GEN-LAST:event_downloadVolumeButtonActionPerformed

    private ShowsProgressInUIWorker retrieveSeriesInfoWorker(final SiteParser parser) {
        return new ShowsProgressInUIWorker<List, String>(logger, progressBar) {

            @Override
            public List<MangaSeriesInfo> doInBackground() throws IOException {
                return actualSiteParser().retrieveMangaList(parser.getURL(), new EventsHandler() {
                    @Override
                    public void event(EventTypes event, String description) {
                        publish(new String[] { description });
                    }
                });
            }

            @Override
            public void done() throws ExecutionException, InterruptedException {
                setMangas((List<MangaSeriesInfo>)get());
            }
        };
    }

    private ShowsProgressInUIWorker retrieveChaptersInfoWorker(final String url) {
        return new ShowsProgressInUIWorker<List, String>(logger, progressBar) {

            @Override
            public List<MangaChaptersInfo> doInBackground() throws IOException {
                return actualSiteParser().retrieveMangaChaptersList(url, new EventsHandler() {
                    @Override
                    public void event(EventTypes event, String description) {
                        publish(new String[] { description });
                    }
                });
            }

            @Override
            public void done() throws ExecutionException, InterruptedException {
                setChapters((List<MangaChaptersInfo>)get());
            }
        };
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        final MangaDownloaderConfiguration config = MangaDownloaderConfiguration.loadPreferences();
        if (args.length > 0) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new MangaDownloaderUI(config, new TestSiteParsersFactory().listParsers()).setVisible(true);
                }
            });
        } else {
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new MangaDownloaderUI(config).setVisible(true);
                }
            });
        }
    }

    private SiteParser actualSiteParser() {
        return (SiteParser)getSiteParsers().get(mangaSitesCombo.getSelectedIndex());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox archiversComboBox;
    private javax.swing.JButton browseButton;
    private javax.swing.JButton cancelConfigButton;
    private javax.swing.JList chaptersList;
    private javax.swing.JButton downloadSelectedButton;
    private javax.swing.JButton downloadVolumeButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextPane jobLogTextPane;
    private javax.swing.JTable jobStatusTable;
    private final javax.swing.JList mangaList = new javax.swing.JList();
    private javax.swing.JComboBox mangaSitesCombo;
    private javax.swing.JTextField mangaURLText;
    private final javax.swing.JProgressBar progressBar = new javax.swing.JProgressBar();
    private javax.swing.JButton saveConfigButton;
    private javax.swing.JTextField savePathTextField;
    private javax.swing.JButton showChaptersButton;
    private javax.swing.JTextField simultaneousTextField;
    private javax.swing.JTextPane statusTextPane;
    private javax.swing.JTabbedPane tabsPane;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    public class RetrieveChaptersWorker extends DownloadJobWorker<String, String> {

        public RetrieveChaptersWorker(String name, List<MangaChaptersInfo> selectedChapters, BindableInMemoryLogger log, JProgressBar progressBar) {
            super(name, selectedChapters, new BindableInMemoryLogger(log), progressBar);
        }

        @Override
        public String doInBackground() throws IOException {
            MangaDownloader engine = new MangaDownloader(actualSiteParser(), config.getSelectedArchiver(), new EventsHandler() {
                @Override
                public void event(EventTypes event, String description) {
                    publish(new String[] { description });
                }
            });
            engine.setSavePath(config.getSaveTo());
            File resultFile = engine.volumeDownload(getName(), getChapters());
            return resultFile.getAbsolutePath();
        }

        @Override
        public void done() throws ExecutionException, InterruptedException {
            end();
            log(get() + " downloaded.");
        }
    }
}
