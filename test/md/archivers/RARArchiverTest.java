package md.archivers;

import java.io.File;
import md.model.NoPrerequisitesException;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Hernan
 */
public class RARArchiverTest {
    
    public RARArchiverTest() {
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
    public void testCheckPath() {
        try {
            new RARArchiver(".cbr").verifyPrerequisites();
            assertTrue(true);
        } catch(NoPrerequisitesException ex) {
            assertTrue(false);
        }
    }

    @Test
    @Ignore
    public void testArchive() throws Exception {
        String filename = "test";
        File directory = new File("\\temp\\docs");
        RARArchiver instance = new RARArchiver(".cbr");
        File expResult = new File(filename + ".cbr");
        File result = instance.archive(filename, directory);
        assertEquals(expResult, result);
    }
}
