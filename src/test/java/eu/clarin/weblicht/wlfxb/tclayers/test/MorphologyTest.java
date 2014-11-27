/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test;

import eu.clarin.weblicht.wlfxb.tc.api.MorphologyLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.MorphologyLayerStored;
import eu.clarin.weblicht.wlfxb.test.utils.TestUtils;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 * @author Yana Panchenko
 *
 */
public class MorphologyTest {

    private static final String INPUT = "/data/tc-morph/layer-input.xml";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));


        MorphologyLayer layer = TestUtils.read(MorphologyLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals(true, layer.hasCharoffsets());
        Assert.assertEquals(true, layer.hasSegmentation());
        Assert.assertEquals(true, layer.getAnalysis(0).getFeatures()[0].isTerminal());
        Assert.assertEquals("cat", layer.getAnalysis(0).getFeatures()[0].getName());
        Assert.assertEquals("noun", layer.getAnalysis(0).getFeatures()[0].getValue());
        Assert.assertEquals(false, layer.getAnalysis(0).getFeatures()[4].isTerminal());
        Assert.assertEquals("test", layer.getAnalysis(0).getFeatures()[4].getName());
        Assert.assertEquals("noun", layer.getAnalysis(0).getFeatures()[4].getSubfeatures()[0].getValue());
        Assert.assertEquals(1, layer.size());

    }
}
