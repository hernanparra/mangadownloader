package md.parsers;

import java.util.*;
import md.model.*;

/**
 *
 * @author Hernan
 */
public class TestSiteParsersFactory extends AbstractSiteParserRegistry {
    private List<SiteParser> siteParsers = null;

    public List<SiteParser> listParsers() {
        if( siteParsers == null ) {
            List<SiteParser> result = new ArrayList<SiteParser>();
            result.add(new NullSiteParser());
            result.addAll(new SubmangaParserFactory().createParsersForTest());
            result.addAll(new MangareaderParserFactory().createParsersForTest());
            siteParsers = result;
        }
        return siteParsers;
    }

}
