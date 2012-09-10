/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tclayers.test;

import de.tuebingen.uni.sfs.wlf1.tc.api.MorphologyLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.MorphologyLayerStored;
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
public class MorphologyTest {

    private static final String INPUT = "data/tc-morph/layer-input.xml";
    private static final String OUTPUT = "data/tc-morph/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = new FileInputStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        MorphologyLayer layer = TestUtils.read(MorphologyLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals(true, layer.hasCharoffsets());
        assertEquals(true, layer.hasSegmentation());
        assertEquals(true, layer.getAnalysis(0).getFeatures()[0].isTerminal());
        assertEquals("cat", layer.getAnalysis(0).getFeatures()[0].getName());
        assertEquals("noun", layer.getAnalysis(0).getFeatures()[0].getValue());
        assertEquals(false, layer.getAnalysis(0).getFeatures()[4].isTerminal());
        assertEquals("test", layer.getAnalysis(0).getFeatures()[4].getName());
        assertEquals("noun", layer.getAnalysis(0).getFeatures()[4].getSubfeatures()[0].getValue());
        assertEquals(1, layer.size());

    }
}
