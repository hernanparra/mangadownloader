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

package md;

import java.io.*;
import java.util.*;
import md.archivers.*;
import md.model.*;
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

        SiteParser parser = new DefaultSiteParsersRegistry().parsersMap().get(args[1]);

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
        AbstractSiteParserRegistry parsersFactory = new DefaultSiteParsersRegistry();
        Map<String, SiteParser> sitesMap = parsersFactory.parsersMap();
        for( String name : sitesMap.keySet()) {
            System.out.println(name);
        }
        System.exit(1);
    }
}
