package md.model;

/**
 *
 * @author Hernan
 */
public class MangaChaptersInfo {
    public String url;
    public String name;
    public String chapterNumber;
    public String addtionalData = "";

    public MangaChaptersInfo(String url, String name, String chapterNumber) {
        this.url = url;
        this.name = name;
        this.chapterNumber = chapterNumber;
    }

    public MangaChaptersInfo(String url, String name, String chapterNumber, String addtionalData) {
        this.url = url;
        this.name = name;
        this.chapterNumber = chapterNumber;
        this.addtionalData = addtionalData;
    }

    @Override
    public String toString() {
        String result = name + " " + chapterNumber;
        if(addtionalData.length() > 0) {
            result += " (" + addtionalData + ")";
        }
        return result;
    }
}
