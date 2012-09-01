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
package md.parsers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import md.documentfactorys.DocumentFactoryFromFile;
import md.documentfactorys.DocumentFactoryFromURL;
import md.model.DocumentFactory;
import md.model.SiteParser;

/**
 *
 * @author Hernan
 */
public class MangareaderParserFactory implements ParserFactory {
    public static String DOMAIN = "http://www.mangareader.net";

    public List<SiteParser> createParsers(DocumentFactory factory) {
        List<SiteParser> result = new ArrayList<SiteParser>();
        result.add(new MangareaderSiteParser("Mangareader Alphabetical", DOMAIN + "/alphabetical", factory));
        return result;
    }

    public List<SiteParser> createParsers() {
        return createParsers(new DocumentFactoryFromURL());
    }

    public List<SiteParser> createParsersForTest() {
        String MANGAREADER_BASEDIR = "resources" + File.separator + "mangareader" + File.separator;

        final DocumentFactoryFromFile factory = new DocumentFactoryFromFile();
        factory.put("http://www.mangareader.net/alphabetical", MANGAREADER_BASEDIR + "alphabetical.htm", "UTF-8", null);
        factory.put("http://www.mangareader.net/224/anima.html", MANGAREADER_BASEDIR + "anima.html", "UTF-8", null);
        return createParsers(factory);
    }
}
