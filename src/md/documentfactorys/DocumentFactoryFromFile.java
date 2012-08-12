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
