package md.parsers;

import java.io.File;
import md.model.*;
import java.io.IOException;
import java.util.*;
import md.documentfactorys.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

/**
 *
 * @author Hernan
 */
public class MangareaderSiteParser extends AbstractSiteParser {
    private static String DOMAIN = "http://www.mangareader.net";

    public static List<SiteParser> createParsers(DocumentFactory factory) {
        List<SiteParser> result = new ArrayList<SiteParser>();
        result.add(new MangareaderSiteParser("Mangareader Alphabetical", DOMAIN + "/alphabetical", factory));
        return result;
    }

    public static List<SiteParser> createParsers() {
        return MangareaderSiteParser.createParsers(new DocumentFactoryFromURL());
    }

    public static List<SiteParser> createParsersForTest() {
        String MANGAREADER_BASEDIR = "resources" + File.separator + "mangareader" + File.separator;

        final DocumentFactoryFromFile factory = new DocumentFactoryFromFile();
        factory.put("http://www.mangareader.net/alphabetical", MANGAREADER_BASEDIR + "alphabetical.htm", "UTF-8", null);
        factory.put("http://www.mangareader.net/224/anima.html", MANGAREADER_BASEDIR + "anima.html", "UTF-8", null);
        return MangareaderSiteParser.createParsers(factory);
    }

    private String name;
    private String url;

    public MangareaderSiteParser(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public MangareaderSiteParser(String name, String url, DocumentFactory db) {
        super(db);
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getURL() {
        return url;
    }

    public String getBaseURL() {
        return DOMAIN;
    }

    public List<MangaSeriesInfo> retrieveMangaList(String url, EventsHandler eh) throws IOException {
        Document doc = docBuilder.create(url);
        Elements elements = doc.select("ul.series_alpha > li");
        List<MangaSeriesInfo> result = new ArrayList<MangaSeriesInfo>();
        for (Element e : elements) {
            Element a = e.child(0);
            if (e.children().size() > 1) {
                result.add(new MangaSeriesInfo(a.ownText(), DOMAIN + a.attr("href"), e.child(1).ownText()));
            } else {
                result.add(new MangaSeriesInfo(a.ownText(), DOMAIN + a.attr("href")));
            }
        }
        return result;
    }

    public List<MangaChaptersInfo> retrieveMangaChaptersList(String url, EventsHandler eh) throws IOException {
        Document doc = docBuilder.create(url);
        //table#listing tbody tr td a
        Elements elements = doc.select("#listing tr:not(.table_head)");
        List<MangaChaptersInfo> result = new ArrayList<MangaChaptersInfo>();
        for (Element e : elements) {
            Element td = e.child(0);
            Element a = td.getElementsByTag("a").first();
            String s = a.ownText();
            int sep = s.lastIndexOf(" ");
            String name = s.substring(0, sep);
            String chapter = s.substring(sep + 1, s.length());
            String additionalData = td.ownText();
            if (additionalData.length() > 2) {
                additionalData = additionalData.substring(2);
            } else {
                additionalData = "";
            }
            result.add(new MangaChaptersInfo(DOMAIN + a.attr("href"), name, chapter, additionalData));
        }
        return result;
    }

    public MangaChapterInfo retrieveMangaPagesList(String url, EventsHandler eh) throws IOException {
        MangaChapterInfo chapterInfo = new MangaChapterInfo();
        chapterInfo.chapterNumber = "";

        Document doc = docBuilder.create(url);
        Elements elements = doc.select("#mangainfo h1");
        String nameAndChapter = elements.first().ownText();
        int pos = nameAndChapter.lastIndexOf(" ");
        chapterInfo.mangaName = nameAndChapter.substring(0, pos);
        chapterInfo.chapterNumber = nameAndChapter.substring(pos + 1, nameAndChapter.length());
        chapterInfo.images = getImagesInfo(doc);
        return chapterInfo;
    }

    private List<MangaImageInfo> getImagesInfo(Document doc) throws IOException {

        String text = doc.select("#selectpage").first().ownText();
        int pages = Integer.parseInt(text.substring(text.lastIndexOf(" ") + 1));

        List<MangaImageInfo> result = new ArrayList<MangaImageInfo>();
        for(int i = 1; i <= pages; i++) {
            String imageURL = doc.select("#img").attr("src");
            String extension = imageURL.substring(imageURL.lastIndexOf("."));
            result.add(new MangaImageInfo(imageURL, extension));
            if( i != pages ) {
                String nextURL = DOMAIN + doc.select("span.next > a").attr("href");
                doc = docBuilder.create(nextURL);
            }
        }

        return result;
    }

}
