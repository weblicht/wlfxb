package eu.clarin.weblicht.wlfxb.io;

import eu.clarin.weblicht.wlfxb.test.utils.TestUtils;
import eu.clarin.weblicht.wlfxb.xb.WLData;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 * @author Yana Panchenko
 *
 */
public class WLDObjector3Test {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();


    private static final String INPUT_FILE = "/data/objector/corpus-all.xml";
    private static final String OUTPUT_FILE = "corpus-all-output.xml";

    public WLDObjector3Test() {
    }

    @Test
    public void testReadWrite() throws Exception {
        InputStream is = this.getClass().getResourceAsStream(INPUT_FILE);
        WLData wld = WLDObjector.read(is);
        WLDObjector.write(wld, testFolder.newFile(OUTPUT_FILE), false);
        InputStream is2 = new FileInputStream(testFolder.newFile(OUTPUT_FILE));
       
        // TODO: can use after equal methods are implemented for TCF components
        //WLData wld2 = WLDObjector.read(is2);
        //Assert.assertEquals("tcf after read->write->read should be equal", wld, wld2);
        
        InputStream is3 = this.getClass().getResourceAsStream(INPUT_FILE);
        TestUtils.assertEqualXml(is3, is2);
    }

}
