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
package md.documentfactorys;

import md.model.DocumentFactory;
import java.io.File;
import java.io.IOException;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Hernan
 */
public class DocumentFactoryFromFile implements DocumentFactory {

    private Map<String, ParseData> lookup = new HashMap<String, ParseData>();

    public void put(String url, String file, String charset, String baseURI ) {
        lookup.put(url, new ParseData(new File(file), charset, baseURI));
    }

    public void put(String url, File file, String charset, String baseURI ) {
        lookup.put(url, new ParseData(file, charset, baseURI));
    }

    public Document create(String url) throws IOException {
        Document result;
        
        ParseData data = lookup.get(url);
        if( data.baseURI == null) {
            result = Jsoup.parse(data.file, data.charset);
        } else {
            result = Jsoup.parse(data.file, data.charset, data.baseURI);            
        }
        return result;
    }

    private class ParseData {
        public File file;
        public String charset;
        public String baseURI;

        public ParseData(File file, String charset, String baseURI) {
            this.file = file;
            this.charset = charset;
            this.baseURI = baseURI;
        }
    }
}
