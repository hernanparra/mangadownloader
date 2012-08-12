package md.model;

import java.io.*;

/**
 *
 * @author Hernan
 */
public interface Archiver {
    void verifyPrerequisites();
    File archive(String filename, File directory) throws FileNotFoundException, IOException;

    String getArchiver();
    String getExtension();
    
}
