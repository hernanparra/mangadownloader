package md.sites;

import md.parsers.TestSiteParsersFactory;
import md.model.SiteParser;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Hernan
 */
public class SubmangaSiteParserTest {
    private static Map<String, SiteParser> parsers;

    @BeforeClass
    public static void setUpClass() throws Exception {
        parsers = new TestSiteParsersFactory().parsersMap();
    }

    @Test
    public void testRetrieveSeriesInfo() throws Exception {
        System.out.println("retrieveSeriesInfo");
        String url = "http://submanga.com/series";

        SiteParser instance = parsers.get("Submanga Most Popular");

        List result = instance.retrieveMangaList(url, null);
        assertEquals( 6749, result.size());
    }

    @Test
    public void testRetrieveSeriesInfoOrderedByName() throws Exception {
        System.out.println("retrieveSeriesInfoOrderedByName");
        String url = "http://submanga.com/series/n";
        
        SiteParser instance = parsers.get("Submanga Most Popular");
        
        List result = instance.retrieveMangaList(url, null);
        assertEquals( 6796, result.size());
    }
    
    @Test
    public void testRetrieveChaptersInfo() throws Exception {
        System.out.println("retrieveChaptersInfo");
        String url = "http://submanga.com/Naruto/completa";

        SiteParser instance = parsers.get("Submanga Most Popular");

        List result = instance.retrieveMangaChaptersList(url, null);
        assertEquals( 567, result.size());
    }

}
