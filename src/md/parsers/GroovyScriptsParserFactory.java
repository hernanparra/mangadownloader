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
