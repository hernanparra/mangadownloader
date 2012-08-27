package md.parsers;

import java.util.List;
import md.model.SiteParser;

/**
 *
 * @author Hernan
 */
public interface ParserFactory {

    List<SiteParser> createParsers();
    List<SiteParser> createParsersForTest();

}
