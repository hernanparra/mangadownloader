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
import java.util.List;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.SwingWorker.StateValue;


public abstract class SimpleSwingWorker<T,V> extends SwingWorker<T,V> {

    private BindableInMemoryLogger log;

    public SimpleSwingWorker(BindableInMemoryLogger log, final JProgressBar progressBar) {
        this.log = log;

        addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public  void propertyChange(PropertyChangeEvent evt) {
                if ("state".equals(evt.getPropertyName())) {
                    if(evt.getNewValue() == StateValue.STARTED) {
                        progressBar.setVisible(true);
                        progressBar.setIndeterminate(true);
                    } else {
                        progressBar.setVisible(false);
                        progressBar.setIndeterminate(false);
                    }
                }
            }
        });
    }

    @Override
    protected void process(List<V> list) {
        for (V e : list) {
            log(e.toString());
        }
    }

    public void publishMessage(V[] vs) {
        publish(vs);
    }

    public void log(String text) {
        log.log(text);
    }

    public String getLog() {
        return log.getLog();
    }
}
