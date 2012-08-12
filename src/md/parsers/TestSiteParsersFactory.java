package md.parsers;

import java.util.*;
import md.model.*;

/**
 *
 * @author Hernan
 */
public class TestSiteParsersFactory extends AbstractSiteParserFactory {
    private List<SiteParser> siteParsers = null;

    public List<SiteParser> listParsers() {
        if( siteParsers == null ) {
            List<SiteParser> result = new ArrayList<SiteParser>();
            result.add(new NullSiteParser());
            result.addAll(SubmangaSiteParser.createParsersForTest());
            result.addAll(MangareaderSiteParser.createParsersForTest());
            siteParsers = result;
        }
        return siteParsers;
    }

}
