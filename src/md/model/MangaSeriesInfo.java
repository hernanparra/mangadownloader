package md.model;

/**
 *
 * @author Hernan
 */
public class MangaSeriesInfo {
    public String name;
    public String url;
    public String additionalData = "";

    public MangaSeriesInfo(String name, String url, String additionalData) {
        this.name = name;
        this.url = url;
        this.additionalData = additionalData;
    }

    public MangaSeriesInfo(String name, String url) {
        this.name = name;
        this.url = url;
    }
    
    @Override
    public String toString() {
        String result = name;
        if( additionalData.length() > 0) {
            result += " - " + additionalData;
        }
        return result;
    }
}
