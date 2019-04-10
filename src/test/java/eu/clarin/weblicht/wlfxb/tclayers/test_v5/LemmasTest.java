/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test_v5;

import eu.clarin.weblicht.wlfxb.tc.api.LemmasLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.LemmasLayerStored;
import eu.clarin.weblicht.wlfxb.test.utils.TestUtils;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 * @author Yana Panchenko and Mohammad Fazleh Elahi
 *
 */
public class LemmasTest {

    private static final String INPUT = "/data/tc-lemmas/layer-input.xml";
    private static final String INPUT_ANY_ATTRIBUTES = "/data_v5/tc-lemmas/layer-inputExtraAtt.xml";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        LemmasLayer layer = TestUtils.read(LemmasLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals(9, layer.size());
        Assert.assertEquals("Peter", layer.getLemma(0).getString());
        Assert.assertEquals(".", layer.getLemma(layer.size() - 1).getString());
        Assert.assertEquals("l1", layer.getLemma(0).getLemmaId());

    }

    @Test
    public void testReadAndWriteBack_ExtraAttribute() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_ANY_ATTRIBUTES);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        LemmasLayer layer = TestUtils.read(LemmasLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        String anyAttribute = layer.getLemma(1).getExtraAttributes().keySet().iterator().next();
        Assert.assertEquals("baseForm", anyAttribute);
        Assert.assertEquals("essen", layer.getLemma(1).getExtraAttributes().get(anyAttribute));
    }
}
