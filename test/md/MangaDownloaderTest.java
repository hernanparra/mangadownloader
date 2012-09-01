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
        assertEquals("0002", MangaDownloader.automaticVolumeName(selectedChapters));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","3"));
        assertEquals("0002 - 0003", MangaDownloader.automaticVolumeName(selectedChapters));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","1"));
        assertEquals("0001 - 0003", MangaDownloader.automaticVolumeName(selectedChapters));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","OMake"));
        assertEquals("0001 - 0003 & OMake", MangaDownloader.automaticVolumeName(selectedChapters));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","Pilot"));
        assertEquals("0001 - 0003 & Extras", MangaDownloader.automaticVolumeName(selectedChapters));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","0"));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","-1"));
        assertEquals("-001 - 0003 & Extras", MangaDownloader.automaticVolumeName(selectedChapters));
        selectedChapters.clear();
        selectedChapters.add(new MangaChaptersInfo("","One Piece","Pilot"));
        assertEquals("Pilot", MangaDownloader.automaticVolumeName(selectedChapters));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","OMake"));
        assertEquals("Extras", MangaDownloader.automaticVolumeName(selectedChapters));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","5"));
        assertEquals("0005 & Extras", MangaDownloader.automaticVolumeName(selectedChapters));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","6"));
        assertEquals("0005 - 0006 & Extras", MangaDownloader.automaticVolumeName(selectedChapters));
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
        assertEquals("0001 - 0003, 0005 - 0006", MangaDownloader.automaticVolumeName(selectedChapters));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","-1"));
        assertEquals("-001, 0001 - 0003, 0005 - 0006", MangaDownloader.automaticVolumeName(selectedChapters));
        selectedChapters.add(new MangaChaptersInfo("","One Piece","Pilot"));
        assertEquals("-001, 0001 - 0003, 0005 - 0006 & Pilot", MangaDownloader.automaticVolumeName(selectedChapters));
    }
}
