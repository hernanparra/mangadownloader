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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JProgressBar;
import md.model.MangaChaptersInfo;

/**
 *
 * @author Hernan
 */
//FIXME La hora está desincronizada por 2 1/2 horas
public abstract class DownloadJobWorker<T,V> extends ShowsProgressInUIWorker<T,V> {
    private String name;
    private Calendar scheduledAt;
    private Calendar startTime;
    private Calendar endTime;
    private DownloadStatus status = DownloadStatus.WAITING;
    private List<MangaChaptersInfo> chapters;
    private DownloadList.DownloadEventsListener listener;

    public DownloadJobWorker(String name, List<MangaChaptersInfo> chapters, BindableInMemoryLogger log, JProgressBar progressBar) {
        super(log, progressBar);
        this.name = name;
        this.chapters = chapters;
        this.scheduledAt = new GregorianCalendar();
        //System.out.println(scheduledAt.getTimeZone());
    }

    public String getEndTime() {
        if( endTime != null) {
            return String.format("%1$tH:%1$tM:%1$tS.%1$tL", endTime);
        } else {
            return "";
        }
    }

    public String getScheduledAt() {
        if( scheduledAt != null) {
            return String.format("%1$tH:%1$tM:%1$tS", scheduledAt);
        } else {
            return "";
        }
    }

    public String getName() {
        return name;
    }

    public String getStartTime() {
        if( startTime != null) {
            return String.format("%1$tH:%1$tM:%1$tS.%1$tL", startTime);
        } else {
            return "";
        }
    }

    public DownloadStatus getStatus() {
        return status;
    }

    private void setStatus(DownloadStatus newStatus) {
        DownloadStatus oldStatus = status;
        status = newStatus;
        support.firePropertyChange("status", oldStatus, newStatus);
    }

    public void setStartTime(Calendar startTime) {
        Calendar oldStartTime = this.startTime;
        this.startTime = startTime;
        support.firePropertyChange("startTime", oldStartTime, startTime);
    }

    public void setEndTime(Calendar endTime) {
        Calendar oldEndTime = this.endTime;
        this.endTime = endTime;
        support.firePropertyChange("endTime", oldEndTime, endTime);
    }

    public List<MangaChaptersInfo> getChapters() {
        return chapters;
    }

    //FIXME Acoplamiento entre DownloadList y esta clase
    public void start(DownloadList.DownloadEventsListener listener) {
        this.listener = listener;
        setStatus(DownloadStatus.DOWNLOADING);
        setStartTime(new GregorianCalendar());
        execute();
    }

    public void end() {
        setStatus(DownloadStatus.DOWNLOADED);
        setEndTime(new GregorianCalendar());
        listener.endOfDownload(this);
    }

}
