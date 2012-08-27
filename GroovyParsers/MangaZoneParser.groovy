package md.parsers

import groovy.transform.TypeChecked
import md.model.*
import org.jsoup.nodes.*
import org.jsoup.select.*
import md.documentfactorys.DocumentFactoryFromURL
/**
 *
 * @author Hernan
 */
@TypeChecked
class MangaZoneParser extends AbstractSiteParser {
    static def DOMAIN = "http://submanga.com";
    private def name;
    private def url;

    static public List<SiteParser> createParsers() {
        return createParsers(new DocumentFactoryFromURL());
    }

    static def List<SiteParser> createParsers(DocumentFactory factory) {
        def result = new ArrayList<SiteParser>();
        result.add(new MangaZoneParser("MangaZone Most Popular", DOMAIN + "/series", factory));
        result.add(new MangaZoneParser("MangaZone Alphabetical", DOMAIN + "/series/n", factory));
        return result;
    }

    def MangaZoneParser(String name, String url) {
        this.name = name;
        this.url = url;
    }

    def MangaZoneParser(String name, String url, DocumentFactory builder) {
        super(builder);
        this.name = name;
        this.url = url;
    }

    def String getName() {
        return name;
    }

    def String getBaseURL() {
        return SubmangaParserFactory.DOMAIN;
    }

    public String getURL() {
        return url;
    }

    public List<MangaSeriesInfo> retrieveMangaList(String url, EventsHandler eh) throws IOException {
        def doc = docBuilder.create(url);
        def elements = doc.select("div#b table.caps td a[href]");
        def result =  new ArrayList<MangaSeriesInfo>();
        elements.each { result.add(new MangaSeriesInfo(it.ownText(), it.attr("href"))) }
        return result;
    }

    public List<MangaChaptersInfo> retrieveMangaChaptersList(String url, EventsHandler eh) throws IOException {
        //Repair url
        if( !(url.endsWith("/completa"))) {
            if(!(url.endsWith("/"))) {
                url = url + "/";
            }
            url = url + "completa";
        }

        def doc = docBuilder.create(url);
        def elements = doc.select("#b table.caps td.s a[href]:not([rel=nofollow])");
        def result =  new ArrayList<MangaChaptersInfo>();
        elements.each { e -> result.add(new MangaChaptersInfo(e.attr("href"), e.ownText(), e.child(0).ownText())) }
        return result;
    }

    public MangaChapterInfo retrieveMangaPagesList(String url, EventsHandler eh) throws IOException {
        def result = new MangaChapterInfo();

        def doc = findNeededDocument(url);

        def elements = doc.select("td.l > a");
        result.mangaName = elements.get(2).ownText();
        result.chapterNumber = elements.get(3).ownText();

        def pages = calculatePageCount(doc);
        def imgURL = findFirstImageURL(doc);

        result.images = createList(imgURL, pages);
        return result;
    }
    
    private Document findNeededDocument(String url) throws IOException {
        def doc = docBuilder.create(url);
        def element = doc.getElementById("l");
        if ((element != null) && (element.tagName().equals("a"))) {
            doc = docBuilder.create(element.attr("href"));
        }
        return doc;
    }

    private int calculatePageCount(Document source) {
        def elementList = source.select("option");
        def pages = elementList.size();
        return pages;
    }

    private String findFirstImageURL(Document source) {
        //Encontrar los links a la página 2
        //El último de ellos contiene la imágen como primer elemento.
        def elementList = source.select("body > div > a > img");
        def element = elementList.get(elementList.size() - 1);
        return element.attr("src");
    }

    private List<MangaImageInfo> createList(String firstURL, int pages) {
        //http://img3.submanga.com/pages/115/115356b57/1.jpg

        def slashPos = firstURL.lastIndexOf("/");
        def head = firstURL.substring(0, slashPos + 1);
        def dotPos = firstURL.lastIndexOf(".");
        def extension = firstURL.substring(dotPos);
        def fileList = new ArrayList<MangaImageInfo>();

        (1..pages).each { fileList.add(new MangaImageInfo(head + String.format("%d", i) + extension, extension)) }
        return fileList;
    }

}
