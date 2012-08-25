/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package md.parsers;

import java.util.*;
import md.model.SiteParser;

/**
 *
 * @author Hernan
 */
public abstract class AbstractSiteParserRegistry {

    public AbstractSiteParserRegistry() {
    }

    public Map<String, SiteParser> parsersMap() {
        Map<String, SiteParser> result = new LinkedHashMap<String, SiteParser>();
        for(SiteParser parser : listParsers()) {
            result.put(parser.getName(), parser);
        }
        return result;
    }

    public abstract List<SiteParser> listParsers();

}
