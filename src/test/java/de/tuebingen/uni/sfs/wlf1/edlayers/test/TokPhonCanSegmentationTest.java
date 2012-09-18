/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.edlayers.test;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

import de.tuebingen.uni.sfs.wlf1.ed.api.ExternalDataLayer;
import de.tuebingen.uni.sfs.wlf1.ed.xb.CanonicalSegmentationLayerStored;
import de.tuebingen.uni.sfs.wlf1.ed.xb.PhoneticSegmentationLayerStored;
import de.tuebingen.uni.sfs.wlf1.ed.xb.TokenSegmentationLayerStored;
import de.tuebingen.uni.sfs.wlf1.test.utils.TestUtils;

/**
 * @author Yana Panchenko
 *
 */
public class TokPhonCanSegmentationTest {

    private static final String INPUT_1 = "/data/ed-seg/layer-input-tseg.xml";
    private static final String OUTPUT_1 = "/tmp/layer-output-tseg.xml";
    private static final String INPUT_2 = "/data/ed-seg/layer-input-pseg.xml";
    private static final String OUTPUT_2 = "/tmp/layer-output-pseg.xml";
    private static final String INPUT_3 = "/data/ed-seg/layer-input-cseg.xml";
    private static final String OUTPUT_3 = "/tmp/layer-output-cseg.xml";

    @Test
    public void test_tseg() throws Exception {
        testReadAndWriteBack(INPUT_1, OUTPUT_1, TokenSegmentationLayerStored.class);
    }

    @Test
    public void test_pseg() throws Exception {
        testReadAndWriteBack(INPUT_2, OUTPUT_2, PhoneticSegmentationLayerStored.class);
    }

    @Test
    public void test_cseg() throws Exception {
        testReadAndWriteBack(INPUT_3, OUTPUT_3, CanonicalSegmentationLayerStored.class);
    }

    private void testReadAndWriteBack(String input, String output, Class<? extends ExternalDataLayer> layerClass) throws Exception {

        InputStream is = this.getClass().getResourceAsStream(input);
        OutputStream os = new FileOutputStream(output);

        ExternalDataLayer layer = TestUtils.read(layerClass, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals("text/xml+an", layer.getDataMimeType());
        assertEquals("http://ws1-clarind.esc.rzg.mpg.de/drop-off/storage/g046acn1_037_AFI.an", layer.getLink());

    }
}
