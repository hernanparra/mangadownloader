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

/**
 *
 * @author Hernan
 */
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.ExecutionException;
import javax.swing.JProgressBar;

public abstract class ShowsProgressInUIWorker<T,V> {

    protected PropertyChangeSupport support;
    
    private final SimpleSwingWorker<T, V> worker;

    public ShowsProgressInUIWorker(final BindableInMemoryLogger log, final JProgressBar progressBar) {
        worker = new SimpleSwingWorker<T, V>(log, progressBar) {

                @Override
                protected T doInBackground() throws Exception {
                    return ShowsProgressInUIWorker.this.doInBackground();
                }

                @Override
                protected void done() {
                    try {
                        ShowsProgressInUIWorker.this.done();
                    } catch (InterruptedException ignore) {
                    } catch (java.util.concurrent.ExecutionException e) {
                        log.logException(e);
                    } finally {
                    }
                }
            };

        support = new PropertyChangeSupport(this);

        log.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent pce) {
                    support.firePropertyChange("log", pce.getOldValue(), pce.getNewValue());
                }
            });
    }

    protected abstract T doInBackground() throws Exception;

    protected abstract void done() throws ExecutionException, InterruptedException;

    public T get() throws InterruptedException, ExecutionException {
        return worker.get();
    }

    public void log(String text) {
        worker.log(text);
    }

    public String getLog() {
        return worker.getLog();
    }

    public void execute() {
        worker.execute();
    }

    public void publish(V[] vs) {
        worker.publishMessage(vs);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

}
