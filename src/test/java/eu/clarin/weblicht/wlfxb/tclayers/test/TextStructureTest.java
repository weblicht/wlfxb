/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test;

import eu.clarin.weblicht.wlfxb.tc.api.TextSpanType;
import eu.clarin.weblicht.wlfxb.tc.api.TextStructureLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.TextStructureLayerStored;
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
public class TextStructureTest {

    private static final String INPUT = "/data/tc-textstruct/layer-input.xml";
    private static final String INPUT_ANY_ATTRIBUTES = "/data/tc-textstruct/layer-inputExtraAtt.xml";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        TextStructureLayer layer = TestUtils.read(TextStructureLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals("page", layer.getSpan(0).getType());
        Assert.assertEquals("line", layer.getSpan(1).getType());
        Assert.assertEquals(Integer.valueOf(0), layer.getSpan(2).getStartChar());
        Assert.assertEquals("paragraph", layer.getSpan(2).getType());
    }

    @Test
    public void testReadAndWriteBack_ExtraAttribute() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_ANY_ATTRIBUTES);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        TextStructureLayer layer = TestUtils.read(TextStructureLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        String anyAttribute = layer.getSpan(2).getExtraAtrributes().keySet().iterator().next();
        Assert.assertEquals("baseForm", anyAttribute);
        Assert.assertEquals("baseFormSpan", layer.getSpan(2).getExtraAtrributes().get(anyAttribute));
    }
}
