/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.io;

import de.tuebingen.uni.sfs.wlf1.xb.WLDProfile;
import java.io.InputStream;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class WLDProfileReadTest {

    private static final String INPUT_FILE_Textcorpus04 = "/data/profiler/input_tcf04.xml";
    private static final String INPUT_FILE_Textcorpus03 = "/data/profiler/input_tcf03.xml";
    private static final String INPUT_FILE_Lexicon04 = "/data/profiler/input_lex04.xml";

    public WLDProfileReadTest() {
    }

    @Test
    public void testRead_File1() throws Exception {
        WLDProfile profile = test(INPUT_FILE_Textcorpus04);
        assertEquals("0.4", profile.getVersion());
        assertEquals("de", profile.getTcProfile().getLang());
        assertEquals(null, profile.getLexProfile());
    }

    @Test
    public void testRead_File2() throws Exception {
        WLDProfile profile = test(INPUT_FILE_Textcorpus03);
        assertEquals("0.3", profile.getVersion());
        assertEquals("en", profile.getTcProfile().getLang());
        assertEquals(null, profile.getLexProfile());
    }

    @Test
    public void testRead_File3() throws Exception {
        WLDProfile profile = test(INPUT_FILE_Lexicon04);
        assertEquals("0.4", profile.getVersion());
        assertEquals("de", profile.getLexProfile().getLang());
        assertEquals(null, profile.getTcProfile());
    }

    private WLDProfile test(String file) throws Exception {
        System.out.println();
        System.out.println("--- READ TEST START ---");
        InputStream is = this.getClass().getResourceAsStream(file);
        WLDProfile wldProfile = WLDProfiler.read(is);
        System.out.println(wldProfile);
        System.out.println("--- READ TEST END ---");
        System.out.println();
        return wldProfile;
    }
}
