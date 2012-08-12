package md.documentfactorys;

import md.model.DocumentFactory;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Hernan
 */
public class DocumentFactoryFromURL implements DocumentFactory {

    public Document create(String url) throws IOException {
        return Jsoup.connect(url).timeout(30000).get();
    }
    
}
