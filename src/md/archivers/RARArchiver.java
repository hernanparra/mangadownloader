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

    public File archive(String filename, File directory) throws FileNotFoundException, IOException {
        String cbrPath = filename + extension;

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
