/* MangaDownloader can help with Comics too
   Copyright (C) 2012 Hernán Parra

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
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
