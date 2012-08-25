package md.parsers;

import java.util.*;
import md.model.*;

/**
 *
 * @author Hernan
 */
//TODO PARSERSFACTORY Armar esquema de registración
public class DefaultSiteParsersRegistry extends AbstractSiteParserRegistry {
    private List<SiteParser> siteParsers = null;

    public List<SiteParser> listParsers() {
        if( siteParsers == null ) {
            List<SiteParser> result = new ArrayList<SiteParser>();
            result.add(new NullSiteParser());
            result.addAll(new SubmangaParserFactory().createParsers());
            result.addAll(new MangareaderParserFactory().createParsers());
            siteParsers = result;
        }
        return siteParsers;
    }

}
