package md.parsers;

import java.io.IOException;
import java.util.*;
import md.model.*;

/**
 *
 * @author Hernan
 */
public class NullSiteParser extends AbstractSiteParser {
    public String getName() {
        return "(Select one)";
    }

    public String getURL() {
        return "";
    }

    public String getBaseURL() {
        return "";
    }

    @Override
    public MangaChapterInfo retrieveMangaPagesList(String url, EventsHandler eh) throws IOException {
        return new MangaChapterInfo();
    }

    @Override
    public List<MangaChaptersInfo> retrieveMangaChaptersList(String url, EventsHandler eh) throws IOException {
        return new ArrayList<MangaChaptersInfo>();
    }

    @Override
    public List<MangaSeriesInfo> retrieveMangaList(String url, EventsHandler eh) throws IOException {
        return new ArrayList<MangaSeriesInfo>();
    }
}
