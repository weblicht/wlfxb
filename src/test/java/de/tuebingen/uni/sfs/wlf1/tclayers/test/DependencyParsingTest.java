/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tclayers.test;

import de.tuebingen.uni.sfs.wlf1.tc.api.DependencyParsingLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.DependencyParsingLayerStored;
import de.tuebingen.uni.sfs.wlf1.test.utils.TestUtils;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class DependencyParsingTest {

    private static final String INPUT = "data/tc-dparsing/layer-input.xml";
    private static final String OUTPUT = "data/tc-dparsing/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = new FileInputStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        DependencyParsingLayer layer = TestUtils.read(DependencyParsingLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals("Tiger", layer.getTagset());
        assertEquals(true, layer.hasEmptyTokens());
        assertEquals(false, layer.hasMultipleGovernors());
        assertEquals(2, layer.size());
        assertEquals(4, layer.getParse(0).getDependencies().length);
        assertEquals("SUBJ", layer.getParse(0).getDependencies()[1].getFunction());
    }
}
