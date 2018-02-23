/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test;

import eu.clarin.weblicht.wlfxb.tc.api.WordSplittingLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.WordSplittingLayerStored;
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
public class WordSplittingTest {

    private static final String INPUT = "/data/tc-wsplit/layer-input.xml";
    private static final String INPUT_ANY_ATTRIBUTES = "/data/tc-wsplit/layer-inputAnyAtt.xml";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));


        WordSplittingLayer layer = TestUtils.read(WordSplittingLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals("syllables", layer.getType());
        Assert.assertEquals(2, layer.size());
        Assert.assertArrayEquals(new int[]{2, 4, 6}, layer.getSplit(0).getIndices());

    }
    
    @Test
    public void testReadAndWriteBackAnyAttributes() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_ANY_ATTRIBUTES);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));


        WordSplittingLayer layer = TestUtils.read(WordSplittingLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

         Integer index=0;
        for (String anyAttribute : layer.getSplit(0).getExtraAtrributes().keySet()) {
            if (index == 0) {
                Assert.assertEquals("baseForm", anyAttribute);
                Assert.assertEquals("baseFormSplit", layer.getSplit(0).getExtraAtrributes().get(anyAttribute));
            }
            if (index == 1) {
                Assert.assertEquals("alterForm", anyAttribute);
                Assert.assertEquals("alterFormSplit",layer.getSplit(0).getExtraAtrributes().get(anyAttribute));
            }

            index++;
        }

    }
}
