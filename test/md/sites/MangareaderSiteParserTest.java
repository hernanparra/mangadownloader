package md.sites;

import md.parsers.TestSiteParsersFactory;
import java.util.*;
import md.model.SiteParser;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Hernan
 */
public class MangareaderSiteParserTest {
    private static Map<String, SiteParser> parsers;

    @BeforeClass
    public static void setUpClass() throws Exception {
        parsers = new TestSiteParsersFactory().parsersMap();
    }

    @Test
    public void testRetrieveSeriesInfo() throws Exception {
        System.out.println("retrieveSeriesInfo");
        String url = "http://www.mangareader.net/alphabetical";

        SiteParser instance = parsers.get("Mangareader Alphabetical");
 
        List result = instance.retrieveMangaList(url, null);
        assertTrue( result.size() > 2100 );
    }

    @Test
    public void testRetrieveChaptersInfo() throws Exception {
        System.out.println("retrieveChaptersInfo");
        String url = "http://www.mangareader.net/224/anima.html";

        SiteParser instance = parsers.get("Mangareader Alphabetical");

        List result = instance.retrieveMangaChaptersList(url, null);
        assertTrue(result.size() == 42);
    }

}
