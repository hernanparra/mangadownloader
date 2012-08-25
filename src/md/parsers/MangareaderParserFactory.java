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
public class MangareaderParserFactory {
    public static String DOMAIN = "http://www.mangareader.net";

    public List<SiteParser> createParsers(DocumentFactory factory) {
        List<SiteParser> result = new ArrayList<SiteParser>();
        result.add(new MangareaderSiteParser("Mangareader Alphabetical", DOMAIN + "/alphabetical", factory));
        return result;
    }

    public List<SiteParser> createParsers() {
        return createParsers(new DocumentFactoryFromURL());
    }

    public List<SiteParser> createParsersForTest() {
        String MANGAREADER_BASEDIR = "resources" + File.separator + "mangareader" + File.separator;

        final DocumentFactoryFromFile factory = new DocumentFactoryFromFile();
        factory.put("http://www.mangareader.net/alphabetical", MANGAREADER_BASEDIR + "alphabetical.htm", "UTF-8", null);
        factory.put("http://www.mangareader.net/224/anima.html", MANGAREADER_BASEDIR + "anima.html", "UTF-8", null);
        return createParsers(factory);
    }
}
