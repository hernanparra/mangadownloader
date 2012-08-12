package md.parsers;

import java.util.*;
import md.model.*;

/**
 *
 * @author Hernan
 */
//TODO PARSERSFACTORY Armar esquema de registración
public class DefaultSiteParsersFactory extends AbstractSiteParserFactory {
    private List<SiteParser> siteParsers = null;

    public List<SiteParser> listParsers() {
        if( siteParsers == null ) {
            List<SiteParser> result = new ArrayList<SiteParser>();
            result.add(new NullSiteParser());
            result.addAll(SubmangaSiteParser.createParsers());
            result.addAll(MangareaderSiteParser.createParsers());
            siteParsers = result;
        }
        return siteParsers;
    }

}
