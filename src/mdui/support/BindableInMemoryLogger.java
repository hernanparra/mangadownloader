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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileNotFoundException;

/**
 *
 * @author Hernan
 */
public class BindableInMemoryLogger {
    public static String NEWLINE = System.getProperty("line.separator");
    private String log = "";
    private PropertyChangeSupport support;
    private BindableInMemoryLogger parent = null;

    public BindableInMemoryLogger() {
        support = new PropertyChangeSupport(this);
    }

    public BindableInMemoryLogger(BindableInMemoryLogger parent) {
        this();
        this.parent = parent;
    }

    public void log(String line) {
        String oldLog = log;
        log += line + NEWLINE;
        if( parent != null ) {
            parent.log(line);
        }
        support.firePropertyChange("log", oldLog, log);
    }

    public void logException(Exception e) {
        String why = null;
        Throwable cause = e.getCause();
        if (cause != null) {
            if( cause instanceof FileNotFoundException) {
                why = "File not found: " + cause.getMessage();
            } else {
                why = cause.toString();
            }
        } else {
            why = e.toString();
        }
        log(why);
    }

    public String getLog() {
        return log;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
