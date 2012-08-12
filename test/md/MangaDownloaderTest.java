/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package md;

import java.util.ArrayList;
import java.util.List;
import md.model.MangaChaptersInfo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Hernan
 */
public class MangaDownloaderTest {
    
    public MangaDownloaderTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAutomaticTomeName() {
        System.out.println("automaticTomeName");
        List<MangaChaptersInfo> selectedChapters = new ArrayList<MangaChaptersInfo>();
        selectedChapters.add(new MangaChaptersInfo("","One Piece","2"));
        assertEquals("0002", MangaDownloader.automaticTomeName(selectedChapters));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","3"));
        assertEquals("0002 - 0003", MangaDownloader.automaticTomeName(selectedChapters));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","1"));
        assertEquals("0001 - 0003", MangaDownloader.automaticTomeName(selectedChapters));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","OMake"));
        assertEquals("0001 - 0003 & OMake", MangaDownloader.automaticTomeName(selectedChapters));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","Pilot"));
        assertEquals("0001 - 0003 & Extras", MangaDownloader.automaticTomeName(selectedChapters));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","0"));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","-1"));
        assertEquals("-001 - 0003 & Extras", MangaDownloader.automaticTomeName(selectedChapters));
        selectedChapters.clear();
        selectedChapters.add(new MangaChaptersInfo("","One Piece","Pilot"));
        assertEquals("Pilot", MangaDownloader.automaticTomeName(selectedChapters));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","OMake"));
        assertEquals("Extras", MangaDownloader.automaticTomeName(selectedChapters));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","5"));
        assertEquals("0005 & Extras", MangaDownloader.automaticTomeName(selectedChapters));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","6"));
        assertEquals("0005 - 0006 & Extras", MangaDownloader.automaticTomeName(selectedChapters));
    }

    @Test
    public void testComplexTomeNames() {
        System.out.println("complexTomeNames");
        List<MangaChaptersInfo> selectedChapters = new ArrayList<MangaChaptersInfo>();
        selectedChapters.add(new MangaChaptersInfo("","One Piece","1"));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","2"));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","3"));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","5"));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","6"));
        assertEquals("0001 - 0003, 0005 - 0006", MangaDownloader.automaticTomeName(selectedChapters));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","-1"));
        assertEquals("-001, 0001 - 0003, 0005 - 0006", MangaDownloader.automaticTomeName(selectedChapters));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","Pilot"));
        assertEquals("-001, 0001 - 0003, 0005 - 0006 & Pilot", MangaDownloader.automaticTomeName(selectedChapters));
    }
}
