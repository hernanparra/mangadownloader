package md;

import java.io.*;
import java.net.*;
import java.util.*;
import md.model.*;
import org.apache.commons.io.*;

/**
 *
 * @author Hernan
 */
//API Submanga.com backups & ripping
//API Mangareader.net backups & ripping
//API Support for zip, cbz, rar, cbr compression & packing
//API Automatic chapter naming
//API Multichapter (tome) download & packing
//API Inteligent Automatic tome naming
//Reintento por falla download (hecho)
//TODO API Soporte a resume desde el archivo de pendings.txt
//TODO API Soporte a resume comparando lo solicitado de lo que ya existe en el archivo.
//TODO API Watchdog de nuevos capitulos
public class MangaDownloader {
    private final static ResourceBundle STRINGS = java.util.ResourceBundle.getBundle("md/strings");
    private final static String PENDINGS_FILE_NAME = STRINGS.getString("NAME_PENDINGS");
    private final static int CONNECTION_TIMEOUT = 20000;
    private final static int READ_TIMEOUT = 60000;
    private SiteParser site;
    private Archiver archiver;
    private EventsHandler handler;
    private File tempDirectory = null;

    public MangaDownloader(SiteParser site, Archiver archiver, EventsHandler eh) {
        this.site = site;
        this.archiver = archiver;
        this.handler = eh;
    }

    public File volumeDownload(String volumeName, String[] urls ) throws IOException {
        for (int i = 0; i < urls.length; i++) {
            String downloadedChapter = download(urls[i]);
            handler.event(EventsHandler.EventTypes.HighLevelEvent, String.format(STRINGS.getString("MSG_DOWNLOADEDCHAPTER"), downloadedChapter));
        }
        return pack(volumeName);
    }

    public File volumeDownload(String volumeName, List<MangaChaptersInfo> chapters ) throws IOException {
        List<String> urls = new ArrayList<String>();
        for( MangaChaptersInfo chapter : chapters) {
            urls.add(chapter.url);
        }
        String[] urlsArray = {};
        urlsArray = urls.toArray(urlsArray);
        return volumeDownload(volumeName, urlsArray);
    }

    public File chapterDownload(String url) throws IOException {
        return pack(download(url));
    }

    public static String automaticVolumeName(List<MangaChaptersInfo> selectedChapters) {
        TreeSet<Integer> chapterNumbers = new TreeSet<Integer>();
        String alternative = "";
        for(MangaChaptersInfo m : selectedChapters) {
            try {
                if(!m.chapterNumber.equals("")) {
                    int test = Integer.parseInt(m.chapterNumber);
                    chapterNumbers.add(test);
                }
            } catch(NumberFormatException e) {
                //If more than one alternative chapter name then all renamed to "Extras"
                if(alternative.equals("")) {
                    alternative = m.chapterNumber;
                } else {
                    alternative = STRINGS.getString("NAME_EXTRAS");
                }                
            }
        }

        String result = "";
        if(!chapterNumbers.isEmpty()) {
            int firstInSequence = Integer.MIN_VALUE;
            int lastInSequence;

            for(Iterator<Integer> i = chapterNumbers.iterator(); i.hasNext(); ) {
                int actualNumber = i.next();
                if( firstInSequence == Integer.MIN_VALUE ) firstInSequence = actualNumber;
                lastInSequence = actualNumber;
                int next;
                if( i.hasNext() ) {
                    next = chapterNumbers.higher(actualNumber);
                } else {
                    next = Integer.MIN_VALUE;
                }
                if( (actualNumber + 1 != next) || (next == Integer.MIN_VALUE) ) {
                    //FIN DE SECUENCIA
                    if( !result.equals("") ) {
                        result += ", ";
                    }
                    if( firstInSequence == lastInSequence ) {
                        result += String.format("%04d", firstInSequence);
                    } else {
                        result += String.format("%04d - %04d", firstInSequence, lastInSequence);
                    }
                    firstInSequence = Integer.MIN_VALUE;
                }
            }

        }

        if( !alternative.equals("") ) {
            if(!result.equals("")) result += " & ";
            result += alternative;
        }

        return result;
    }

    private String download(String url) throws IOException {
        MangaChapterInfo downloadInfo = site.retrieveMangaPagesList(url, handler);
        handler.event(EventsHandler.EventTypes.HighLevelEvent, STRINGS.getString("MSG_GOTCHAPTERDOWNLOADLIST"));

        String filename = String.format("%s %s", makeValidFileName(downloadInfo.mangaName), downloadInfo.chapterNumber);

        if (tempDirectory == null) {
            tempDirectory = createTempDirectory();
        }

        downloadList(downloadInfo.images, filename, tempDirectory);
        handler.event(EventsHandler.EventTypes.HighLevelEvent, String.format(STRINGS.getString("MSG_ARCHIVEDFILE"), filename));
        return filename;
    }

    private File pack(String filename) throws IOException {
        filename = makeValidFileName(filename);
        try {
            return archiver.archive(filename, tempDirectory);
        } finally {
            if (tempDirectory != null) {
                FileUtils.deleteDirectory(tempDirectory);
            }
        }
    }

    /**
     * The urlList must be ordered for renumbering
     *
     * @param urlList List of ordered url to be downloaded and renamed
     * @throws MalformedURLException
     */
    private File downloadList(List<MangaImageInfo> urlList, String namePrefix, File workDirectory) throws IOException {
        String baseName = namePrefix + " - ";
        File baseDirectory = new File(workDirectory, namePrefix);

        int counter = 1;
        for (MangaImageInfo page : urlList) {
            File outputFile = new File(baseDirectory, String.format("%s%04d%s", baseName,counter++,page.imgExtension));
            try {
                FileUtils.copyURLToFile(new URL(page.url), outputFile, CONNECTION_TIMEOUT, READ_TIMEOUT);
            } catch (IOException ioe) {
                outputFile.delete();
                handler.event(EventsHandler.EventTypes.LowLevelEvent, String.format(STRINGS.getString("MSG_RETRYINGFILE"), page.url, ioe.getMessage()));
                try {
                    FileUtils.copyURLToFile(new URL(page.url), outputFile, CONNECTION_TIMEOUT, READ_TIMEOUT);
                } catch(IOException ex) {
                    handler.event(EventsHandler.EventTypes.LowLevelEvent, String.format(STRINGS.getString("MSG_SKIPPINGFILE"), page.url, ex.getMessage()));
                    FileUtils.writeStringToFile(new File(baseDirectory, PENDINGS_FILE_NAME), page.url);
                }
            }
            handler.event(EventsHandler.EventTypes.LowLevelEvent, String.format(STRINGS.getString("MSG_DOWNLOADEDFILE"), page.url));
        }
        return baseDirectory;
    }

    private File createTempDirectory() throws IOException {
        final File temp = File.createTempFile(STRINGS.getString("NAME_TEMP"), Long.toBinaryString(System.nanoTime()));

        if (!(temp.delete())) {
            throw new IOException(String.format(STRINGS.getString("MSG_COULDNOTDELETETEMPORALFILE"), temp.getAbsolutePath()));
        }

        if (!(temp.mkdir())) {
            throw new IOException(String.format(STRINGS.getString("MSG_COULDNOTCREATETEMPORALDIRECTORY"), temp.getAbsolutePath()));
        }

        return temp;
    }

    private String makeValidFileName(String mangaName) {
        mangaName = mangaName.replaceAll("(\\\\|/|\\*|\\?)*", "");
        return mangaName;
    }
}
