/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test;

import eu.clarin.weblicht.wlfxb.tc.api.SentencesLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.SentencesLayerStored;
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
public class SentencesTest {

    private static final String INPUT = "/data/tc-sents/layer-input.xml";
    private static final String INPUT_ANY_ATTRIBUTES = "/data/tc-sents/layer-inputAnyAtt.xml";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        SentencesLayer layer = TestUtils.read(SentencesLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals(2, layer.size());
        Assert.assertEquals(true, layer.hasCharOffsets());
        Assert.assertEquals(Integer.valueOf(0), layer.getSentence(0).getStartCharOffset());
        Assert.assertEquals(Integer.valueOf(24), layer.getSentence(0).getEndCharOffset());
    }
    @Test
    public void testReadAndWriteBackAnyAttributes() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_ANY_ATTRIBUTES);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        SentencesLayer layer = TestUtils.read(SentencesLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals(2, layer.size());
        Assert.assertEquals(true, layer.hasCharOffsets());
        Assert.assertEquals(Integer.valueOf(0), layer.getSentence(0).getStartCharOffset());
        Assert.assertEquals(Integer.valueOf(24), layer.getSentence(0).getEndCharOffset());
        
        Integer index = 0;
        for (String anyAttribute : layer.getSentence(0).getAnyAtrributes().keySet()) {
            if (index == 0) {
                Assert.assertEquals("baseForm", anyAttribute);
                Assert.assertEquals("base", layer.getSentence(0).getAnyAtrributes().get(anyAttribute));
            }
            if (index == 1) {
                Assert.assertEquals("alterForm", anyAttribute);
                Assert.assertEquals("modified", layer.getSentence(0).getAnyAtrributes().get(anyAttribute));
            }

            index++;
        }

    }
}
