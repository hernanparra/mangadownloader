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
class SubmangaGroovyParser extends AbstractSiteParser {
    static String DOMAIN = "http://submanga.com";
    String name;
    String url;

    static public List<SiteParser> createParsers() {
        return createParsers(new DocumentFactoryFromURL());
    }

    static def List<SiteParser> createParsers(DocumentFactory factory) {
        def result = new ArrayList<SiteParser>();
        result.add(new SubmangaGroovyParser("Submanga Groovy Most Popular", DOMAIN + "/series", factory));
        result.add(new SubmangaGroovyParser("Submanga Groovy Alphabetical", DOMAIN + "/series/n", factory));
        return result;
    }

    def SubmangaGroovyParser(String name, String url) {
        this.name = name;
        this.url = url;
    }

    def SubmangaGroovyParser(String name, String url, DocumentFactory builder) {
        super(builder);
        this.name = name;
        this.url = url;
    }

    def String getBaseURL() {
        return SubmangaParserFactory.DOMAIN;
    }

    public String getName() {
        return name;
    }

    public String getURL() {
        return url;
    }

    public List<MangaSeriesInfo> retrieveMangaList(String url, EventsHandler eh) throws IOException {
        def doc = docBuilder.create(url);
        def elements = doc.select("div#b table.caps td a[href]");
        def result =  new ArrayList<MangaSeriesInfo>();
        elements.each { Element e -> result.add(new MangaSeriesInfo(e.ownText(), e.attr("href"))) }
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
        elements.each { Element e -> result.add(new MangaChaptersInfo(e.attr("href"), e.ownText(), e.child(0).ownText())) }
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

        (1..pages).each { fileList.add(new MangaImageInfo(head + String.format("%d", it) + extension, extension)) }
        return fileList;
    }

}
