/**
 *
 */
package eu.clarin.weblicht.wlfxb.edlayers.test;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import eu.clarin.weblicht.wlfxb.ed.api.ExternalDataLayer;
import eu.clarin.weblicht.wlfxb.ed.xb.CanonicalSegmentationLayerStored;
import eu.clarin.weblicht.wlfxb.ed.xb.PhoneticSegmentationLayerStored;
import eu.clarin.weblicht.wlfxb.ed.xb.TokenSegmentationLayerStored;
import eu.clarin.weblicht.wlfxb.test.utils.TestUtils;

/**
 * @author Yana Panchenko
 *
 */
public class TokPhonCanSegmentationTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT_1 = "/data/ed-seg/layer-input-tseg.xml";
    private static final String OUTPUT_1 = "layer-output-tseg.xml";
    private static final String INPUT_2 = "/data/ed-seg/layer-input-pseg.xml";
    private static final String OUTPUT_2 = "layer-output-pseg.xml";
    private static final String INPUT_3 = "/data/ed-seg/layer-input-cseg.xml";
    private static final String OUTPUT_3 = "layer-output-cseg.xml";

    @Test
    public void test_tseg() throws Exception {
        String outfile = testFolder.getRoot() + File.separator + OUTPUT_1;
        testReadAndWriteBack(INPUT_1, outfile, TokenSegmentationLayerStored.class);
    }

    @Test
    public void test_pseg() throws Exception {
        String outfile = testFolder.getRoot() + File.separator + OUTPUT_2;
        testReadAndWriteBack(INPUT_2, outfile, PhoneticSegmentationLayerStored.class);
    }

    @Test
    public void test_cseg() throws Exception {
        String outfile = testFolder.getRoot() + File.separator + OUTPUT_3;
        testReadAndWriteBack(INPUT_3, outfile, CanonicalSegmentationLayerStored.class);
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
