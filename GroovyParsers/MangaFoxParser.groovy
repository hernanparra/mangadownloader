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
class MangaFoxParser extends AbstractSiteParser {
    static String DOMAIN = "http://mangafox.me";
    String name;
    String url;

    static def List<SiteParser> createParsers() {
        return createParsers(new DocumentFactoryFromURL());
    }

    static def List<SiteParser> createParsers(DocumentFactory factory) {
        def result = new ArrayList<SiteParser>();
        result.add(new MangaFoxParser("MangaFox Alphabetical", DOMAIN + "/manga", factory));
        return result;
    }

    def MangaFoxParser(String name, String url) {
        this.name = name;
        this.url = url;
    }

    def MangaFoxParser(String name, String url, DocumentFactory builder) {
        super(builder);
        this.name = name;
        this.url = url;
    }

    def String getBaseURL() {
        return SubmangaParserFactory.DOMAIN;
    }

    def String getURL() {
        return url;
    }

    public List<MangaSeriesInfo> retrieveMangaList(String url, EventsHandler eh) throws IOException {
        def doc = docBuilder.create(url);
        def elements = doc.select('div.manga_list li a'); //ul#idx_# li a[href]
        def result =  new ArrayList<MangaSeriesInfo>();
        elements.each { Element e -> result.add(new MangaSeriesInfo(e.ownText(), e.attr("href"))) }
        return result;
    }

    public List<MangaChaptersInfo> retrieveMangaChaptersList(String url, EventsHandler eh) throws IOException {
        def doc = docBuilder.create(url);
        def elements = doc.select("div#chapters a.tips");
        def result =  new ArrayList<MangaChaptersInfo>();
        elements.each { Element e -> result.add(new MangaChaptersInfo(e.attr("href"), e.ownText(), e.nextElementSibling()?.ownText() ?: "" )) }
        return result;
    }

    public MangaChapterInfo retrieveMangaPagesList(String url, EventsHandler eh) throws IOException {
        def result = new MangaChapterInfo();
        def doc = docBuilder.create(url);
        
        //Nombre y capitulo
        def elements = doc.select("div#series a");
        def element = elements.get(0);
        String nameAndNumber = element.ownText();
        def index = nameAndNumber.trim().lastIndexOf(" ");
        result.mangaName = nameAndNumber.getAt(1..index);
        def last = nameAndNumber.size() - 1;
        result.chapterNumber = nameAndNumber.getAt(index..last);

        //Nro de Paginas
        elements = doc.select("form#top_bar option")
        def pages = elements.size() - 1

        //TODO Recorrer páginas para conseguir URLs de imágenes
        def fileList = new ArrayList<MangaImageInfo>();
        (1..pages).each { fileList.add(new MangaImageInfo(head + String.format("%d", it) + extension, extension)) }

        result.images = fileList;
        return result;
    }

}
