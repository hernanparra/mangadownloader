package md.parsers;

import md.documentfactorys.DocumentFactoryFromURL;
import md.model.*;


/**
 *
 * @author Hernan
 */
public abstract class AbstractSiteParser implements SiteParser {
    protected DocumentFactory docBuilder;
    
    public AbstractSiteParser() {
        docBuilder = new DocumentFactoryFromURL();
    }

    public AbstractSiteParser(DocumentFactory builder) {
        docBuilder = builder;
    }

    @Override
    public String toString() {
        return getName();
    }

}
