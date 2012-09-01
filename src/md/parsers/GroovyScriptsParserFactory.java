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
package md.parsers;

import groovy.lang.GroovyClassLoader;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.logging.*;
import md.model.SiteParser;
import org.codehaus.groovy.control.CompilationFailedException;

/**
 *
 * @author Hernan
 */
public class GroovyScriptsParserFactory implements ParserFactory {

    public List<SiteParser> createParsers() {
        List<SiteParser> result = new ArrayList<SiteParser>();
        List<SiteParser> parsers;
        ClassLoader cl = getClass().getClassLoader();
        GroovyClassLoader gcl = new GroovyClassLoader(cl);
        /*
        try {
            Enumeration<URL> parsersUrl = gcl.getResources("GroovyParsers/MangaZoneParser.groovy");
            URL url = parsersUrl.nextElement();
            parsersUrl.nextElement().openStream();
        } catch (IOException ex) {
            Logger.getLogger(GroovyScriptsParserFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        */

        File groovyDir = new File("." + File.separator + "GroovyParsers");
        File[] parserFiles = groovyDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".groovy");
            }
        });

        Class gclass;
        for(File groovyFile : parserFiles) {
            try {
                gclass = gcl.parseClass(groovyFile);
                Method declaredMethod = gclass.getDeclaredMethod("createParsers");
                parsers = (List<SiteParser>)declaredMethod.invoke(null, (Object[]) null);
                result.addAll(parsers);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(GroovyScriptsParserFactory.class.getName()).log(Level.WARNING, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(GroovyScriptsParserFactory.class.getName()).log(Level.WARNING, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(GroovyScriptsParserFactory.class.getName()).log(Level.WARNING, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(GroovyScriptsParserFactory.class.getName()).log(Level.WARNING, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(GroovyScriptsParserFactory.class.getName()).log(Level.WARNING, null, ex);
            } catch (CompilationFailedException ex) {
                Logger.getLogger(GroovyScriptsParserFactory.class.getName()).log(Level.WARNING, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GroovyScriptsParserFactory.class.getName()).log(Level.WARNING, null, ex);
            }
        }
        return result;
    }

    public List<SiteParser> createParsersForTest() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
