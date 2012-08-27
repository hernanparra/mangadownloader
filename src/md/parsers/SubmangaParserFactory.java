package md.parsers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import md.documentfactorys.DocumentFactoryFromFile;
import md.documentfactorys.DocumentFactoryFromURL;
import md.model.DocumentFactory;
import md.model.SiteParser;

/**
 *
 * @author Hernan
 */
public class SubmangaParserFactory implements ParserFactory {
    public static String DOMAIN = "http://submanga.com";

    public List<SiteParser> createParsers(DocumentFactory factory) {
        List<SiteParser> result = new ArrayList<SiteParser>();
        result.add(new SubmangaSiteParser("Submanga Most Popular", DOMAIN + "/series", factory));
        result.add(new SubmangaSiteParser("Submanga Alphabetical", DOMAIN + "/series/n", factory));
        return result;
    }

    @Override
    public List<SiteParser> createParsers() {
        return createParsers(new DocumentFactoryFromURL());
    }

    @Override
    public List<SiteParser> createParsersForTest() {
        String SUBMANGA_BASEDIR = "resources" + File.separator + "submanga" + File.separator;
        DocumentFactoryFromFile factory = new DocumentFactoryFromFile();
        factory.put("http://submanga.com/series", SUBMANGA_BASEDIR + "series.htm", "UTF-8", null);
        factory.put("http://submanga.com/series/n", SUBMANGA_BASEDIR + "n.htm", "UTF-8", null);
        factory.put("http://submanga.com/Naruto/completa", SUBMANGA_BASEDIR + "completa.htm", "UTF-8", null);
        return createParsers(factory);
    }
}
