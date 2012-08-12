package md.model;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Hernan
 */
//TODO PARSERS Generar eventos desde los SiteParsers para los casos que requieran parsear muchas páginas
//TODO PARSERS Soportar problemas de comunicación con reintentos
public interface SiteParser {

    public String getName();
    public String getURL();
    public String getBaseURL();

    public List<MangaSeriesInfo> retrieveMangaList(String url, EventsHandler eh) throws IOException;

    public List<MangaChaptersInfo> retrieveMangaChaptersList(String url, EventsHandler eh) throws IOException;

    public MangaChapterInfo retrieveMangaPagesList(String url, EventsHandler eh) throws IOException;

}
