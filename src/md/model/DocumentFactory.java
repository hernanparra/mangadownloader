package md.model;

import java.io.IOException;
import org.jsoup.nodes.Document;

/**
 *
 * @author Hernan
 */
public interface DocumentFactory {
    Document create(String url) throws IOException;
}
