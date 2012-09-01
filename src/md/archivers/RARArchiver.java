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
package md.archivers;

import md.model.NoPrerequisitesException;
import java.io.*;
import java.util.*;
import md.model.Archiver;

/**
 *
 * @author Hernan
 */
public class RARArchiver implements Archiver {
    public static List<Archiver> createArchivers() {
        List<Archiver> result = new ArrayList<Archiver>();
        result.add(new RARArchiver(".cbr"));
        result.add(new RARArchiver(".rar"));
        return result;
    }

    private final String extension;

    public RARArchiver(String extension) {
        this.extension = extension;
    }

    public String getArchiver() {
        return "RAR";
    }

    public String getExtension() {
        return extension;
    }

    public File archive(String path, String filename, File directory) throws FileNotFoundException, IOException {
        String cbrPath = path + filename + extension;

        ProcessBuilder pb = new ProcessBuilder();
        pb.command("rar.exe","a","-ep1","-inul","-r", cbrPath, directory.getAbsolutePath() + File.separator + "*");
        Process p = pb.start();
        try {
            p.waitFor();
        } catch (InterruptedException ex) {
            throw new RuntimeException("RAR Interrupted by user?", ex);
        }
        if(p.exitValue()!=0) {
            throw new RuntimeException("RAR returned an error. Return value:" + p.exitValue());
        }

        return new File(cbrPath);
    }

    public void verifyPrerequisites() throws NoPrerequisitesException {
        ProcessBuilder pb = new ProcessBuilder();
        pb.command("rar.exe","-");
        Process p;
        try {
            p = pb.start();
            p.waitFor();
        } catch (IOException ex) {
            throw new NoPrerequisitesException("Please add RAR.EXE to the path and retry or change the ZIP archiver.");
        } catch (InterruptedException ex) {
            throw new NoPrerequisitesException("Please add RAR.EXE to the path and retry or change the ZIP archiver.");
        }
        if(p.exitValue() != 7) {
            throw new NoPrerequisitesException("Please add RAR.EXE to the path and retry or change the ZIP archiver.");
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.extension != null ? this.extension.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RARArchiver other = (RARArchiver) obj;
        if ((this.extension == null) ? (other.extension != null) : !this.extension.equals(other.extension)) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return getExtension();
    }

}
