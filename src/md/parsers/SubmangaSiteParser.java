package md.parsers;

import java.io.*;
import java.util.*;
import md.model.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

/**
 *
 * @author Hernan
 */
public class SubmangaSiteParser extends AbstractSiteParser {
    private String name;
    private String url;

    public SubmangaSiteParser(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public SubmangaSiteParser(String name, String url, DocumentFactory builder) {
        super(builder);
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getBaseURL() {
        return SubmangaParserFactory.DOMAIN;
    }

    public String getURL() {
        return url;
    }

    public List<MangaSeriesInfo> retrieveMangaList(String url, EventsHandler eh) throws IOException {
        Document doc = docBuilder.create(url);
        Elements elements = doc.select("div#b table.caps td a[href]");
        List<MangaSeriesInfo> result =  new ArrayList<MangaSeriesInfo>();
        for(Element e : elements) {
            result.add(new MangaSeriesInfo(e.ownText(), e.attr("href")));
        }
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
        
        Document doc = docBuilder.create(url);
        Elements elements = doc.select("#b table.caps td.s a[href]:not([rel=nofollow])");
        List<MangaChaptersInfo> result =  new ArrayList<MangaChaptersInfo>();
        for(Element e : elements) {
            result.add(new MangaChaptersInfo(e.attr("href"), e.ownText(), e.child(0).ownText()));
        }
        return result;
    }

    // Lo que necesita de submanga es esto:
    // http://submanga.com/c/115356
    // De esta página debiera conseguir la url tipo:
    // http://img3.submanga.com/pages/115/115356b57/1.jpg
    public MangaChapterInfo retrieveMangaPagesList(String url, EventsHandler eh) throws IOException {
        MangaChapterInfo result = new MangaChapterInfo();

        Document doc = findNeededDocument(url);

        Elements elements = doc.select("td.l > a");
        result.mangaName = elements.get(2).ownText();
        result.chapterNumber = elements.get(3).ownText();

        int pages = calculatePageCount(doc);
        String imgURL = findFirstImageURL(doc);

        result.images = createList(imgURL, pages);
        return result;
    }

    private Document findNeededDocument(String url) throws IOException {
        Document doc = docBuilder.create(url);
        Element element = doc.getElementById("l");
        if ((element != null) && (element.tagName().equals("a"))) {
            doc = docBuilder.create(element.attr("href"));
        }
        return doc;
    }

    private int calculatePageCount(Document source) {
        Elements elementList = source.select("option");
        int pages = elementList.size();
        return pages;
    }

    private String findFirstImageURL(Document source) {
        //Encontrar los links a la página 2
        //El último de ellos contiene la imágen como primer elemento.
        Elements elementList = source.select("body > div > a > img");
        Element element = elementList.get(elementList.size() - 1);
        String imgSource = element.attr("src");
        return imgSource;
    }

    private List<MangaImageInfo> createList(String firstURL, int pages) {
        //http://img3.submanga.com/pages/115/115356b57/1.jpg

        int slashPos = firstURL.lastIndexOf("/");
        String head = firstURL.substring(0, slashPos + 1);

        int dotPos = firstURL.lastIndexOf(".");

        String extension = firstURL.substring(dotPos);

        List<MangaImageInfo> fileList = new ArrayList<MangaImageInfo>();
        for (int i = 1; i <= pages; i++) {
            fileList.add(
                    new MangaImageInfo(head + String.format("%d", i) + extension, extension));
        }
        return fileList;
    }

}
