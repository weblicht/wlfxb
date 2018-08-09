/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test;

import eu.clarin.weblicht.wlfxb.tc.api.PosTagsLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.PosTagsLayerStored;
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
public class PosTagsTest {

    private static final String INPUT = "/data/tc-pos/layer-input.xml";
    private static final String INPUT_ANY_ATTRIBUTES = "/data/tc-pos/layer-inputExtraAtt.xml";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        PosTagsLayer layer = TestUtils.read(PosTagsLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals("STTS", layer.getTagset());
        Assert.assertEquals(9, layer.size());
        Assert.assertEquals("NE", layer.getTag(0).getString());
        Assert.assertEquals("$.", layer.getTag(layer.size() - 1).getString());

    }

    @Test
    public void testReadAndWriteBack_ExtraAttribute() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_ANY_ATTRIBUTES);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        PosTagsLayer layer = TestUtils.read(PosTagsLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        String anyAttribute = layer.getTag(0).getExtraAtrributes().keySet().iterator().next();
        Assert.assertEquals("baseForm", anyAttribute);
        Assert.assertEquals("NE", layer.getTag(0).getExtraAtrributes().get(anyAttribute));
    }

}
