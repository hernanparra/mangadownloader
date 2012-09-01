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
package mdui.support;

import java.beans.*;
import java.util.ArrayList;
import mdui.MangaDownloaderConfiguration;

/**
 *
 * @author Hernan
 */
public class DownloadList {
    static final private boolean AUTO_START = true;

    private MangaDownloaderConfiguration config;
    private PropertyChangeSupport support;

    private ArrayList<DownloadJobWorker> list = new ArrayList<DownloadJobWorker>();
    private int downloading = 0;

    public DownloadList(MangaDownloaderConfiguration config) {
        this.config = config;
        support = new PropertyChangeSupport(this);
    }

    public void add(DownloadJobWorker downloadWorker) {
        ArrayList<DownloadJobWorker> oldList = list;
        list = new ArrayList<DownloadJobWorker>(oldList);
        list.add(downloadWorker);
        support.firePropertyChange("list", oldList, list);
        nextDownload();
    }

    private void endDownload(DownloadJobWorker worker) {
        downloading -= 1;
        nextDownload();
    }

    private void nextDownload() {
        if( (downloading < config.getSimultaneousDownloads()) && AUTO_START ) {
            for( DownloadJobWorker job : list) {
                if( DownloadStatus.WAITING  == job.getStatus() ) {
                    job.start(new DownloadEventsListener());
                    downloading += 1;
                }
            }
        }
    }

    public ArrayList<DownloadJobWorker> getList() {
        return list;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    //FIXME Acoplamiento entre esta clase y DownloadJobWorker
    public class DownloadEventsListener {
        public void endOfDownload(DownloadJobWorker finishedWorker) {
            endDownload(finishedWorker);
        }
    }
}
