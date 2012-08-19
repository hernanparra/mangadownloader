package md.archivers;

import java.io.*;
import java.util.*;
import java.util.zip.*;
import md.model.Archiver;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Hernan
 */
public class ZIPArchiver implements Archiver {

    public static List<Archiver> createArchivers() {
        List<Archiver> result = new ArrayList<Archiver>();
        result.add(new ZIPArchiver(".cbz"));
        result.add(new ZIPArchiver(".zip"));
        return result;
    }

    private String extension;
    
    public ZIPArchiver(String extension) {
        this.extension = extension;
    }

    public String getArchiver() {
        return "ZIP";
    }

    public String getExtension() {
        return extension;
    }

    public File archive(String path, String filename, File directory) throws FileNotFoundException, IOException {
        File zipFile = new File(path + filename + extension);
        zipDirectory(zipFile, directory);
        return zipFile;
    }

    private void zipDirectory(File zipFile, File directory) throws FileNotFoundException, IOException {
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
        FileInputStream fin = null;
        for (File d : directory.listFiles()) {
            for (File f : d.listFiles()) {
                zos.putNextEntry(new ZipEntry(createNameForZip(f)));
                try {
                    fin = new FileInputStream(f);
                    IOUtils.copy(fin, zos);
                } finally {
                    if( fin != null) fin.close();
                }
                zos.closeEntry();
            }
        }
        zos.close();
    }

    private String createNameForZip(File f) {
        String path = f.getParent();
        int pos = path.lastIndexOf(File.separator);
        String baseDir = path.substring(pos + 1);
        String zipPath = baseDir + File.separator + f.getName();
        return zipPath;
    }

    public void verifyPrerequisites() {
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
        final ZIPArchiver other = (ZIPArchiver) obj;
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
