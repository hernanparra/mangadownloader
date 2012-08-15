package md;

import md.model.*;
import java.io.*;
import java.util.*;
import md.archivers.*;
import md.parsers.*;

//CLI Permitir seleccionar entre cbr y zip desde linea de comandos (hecho)
//TODO CLI Permitir seleccionar SiteParser. HECHO Verificar si funciona.
//TODO CLI Mejorar soporte de internacionalización utilizando printf
//TODO CLI Mejorar soporte de volumenes. Soportar interfaz que acepte "desde" "hasta" para armar un volumen.
public class MangaDownloaderCLI {
    private final static ResourceBundle STRINGS = java.util.ResourceBundle.getBundle("md/strings");

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
        if (args.length < 2) {
            showUsage();
        }

        Archiver archiver = ArchiversFactory.archiversMap().get(args[0]);
        if( archiver == null) {
            showUsage();
        }

        SiteParser parser = new DefaultSiteParsersFactory().parsersMap().get(args[1]);

        EventsHandler eventHandler = new EventsHandler() {
            public void event(EventsHandler.EventTypes eventType , String value) {
                switch( eventType ) {
                    case HighLevelEvent:
                        System.out.println();
                        System.out.print(value);
                        break;
                    case LowLevelEvent:
                        System.out.print(".");
                        break;
                }
            }
        };

        MangaDownloader engine = new MangaDownloader(parser, archiver, eventHandler);

        long startTime = System.currentTimeMillis();

        File packedFile;
        if( args.length > 3) {
            String[] urls = Arrays.copyOfRange(args, 3, args.length);
            packedFile = engine.volumeDownload(args[2], urls);
        } else {
            //Doesn't needs chapter name.
            packedFile = engine.chapterDownload(args[2]);
        }

        long elapsed = (System.currentTimeMillis() - startTime) / 1000;

        System.out.println();
        System.out.printf(STRINGS.getString("MSG_RESULTFILE"), packedFile.getAbsolutePath());
        System.out.printf(STRINGS.getString("MSG_RUNNINGTIME"), String.valueOf(elapsed));
    }

    private static void showUsage() {
        System.out.println(STRINGS.getString("MSG_USAGE"));
        System.out.println(STRINGS.getString("MSG_USAOR"));

        System.out.println(STRINGS.getString("MSG_ARCHIVERISONEOF"));
        Map<String,Archiver> archiversList = ArchiversFactory.archiversMap();
        for( String name : archiversList.keySet()) {
            System.out.println(name);
        }

        System.out.println("SITE is one of: ");
        AbstractSiteParserFactory parsersFactory = new DefaultSiteParsersFactory();
        Map<String, SiteParser> sitesMap = parsersFactory.parsersMap();
        for( String name : sitesMap.keySet()) {
            System.out.println(name);
        }
        System.exit(1);
    }
}
