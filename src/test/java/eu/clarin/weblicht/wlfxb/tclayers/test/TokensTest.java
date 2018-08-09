/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test;

import eu.clarin.weblicht.wlfxb.tc.api.TokensLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.TokensLayerStored;
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
public class TokensTest {

    private static final String INPUT = "/data/tc-tokens/layer-input.xml";
    private static final String INPUT_SURFACE_PARTS = "/data/tc-tokens/layer-inputSurfaceParts.xml";
    private static final String INPUT_SURFACE = "/data/tc-tokens/layer-inputSurface.xml";
    private static final String INPUT_ANY_ATTRIBUTE = "/data/tc-tokens/layer-inputExtraAtt.xml";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        TokensLayer layer = TestUtils.read(TokensLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals(true, layer.hasCharOffsets());
        Assert.assertEquals(9, layer.size());
        Assert.assertEquals("Peter", layer.getToken(0).getString());
        Assert.assertEquals("t1", layer.getToken(0).getID());
        Assert.assertEquals(Long.valueOf(0), layer.getToken(0).getStart());
        Assert.assertEquals(Long.valueOf(5), layer.getToken(0).getEnd());
        Assert.assertEquals(".", layer.getToken(layer.size() - 1).getString());

    }

    @Test
    public void testReadAndWriteBack_SurfaceForm_Parts() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_SURFACE_PARTS);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-outputSurfaceParts.xml"));

        TokensLayer layer = TestUtils.read(TokensLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals("t_4", layer.getToken(4).getID());
        Assert.assertEquals("in", layer.getToken(4).getString());
        Assert.assertEquals("im", layer.getToken(4).getSurfaceForm());
        Assert.assertEquals("t_4", layer.getToken(4).getParts()[0]);
        Assert.assertEquals("t_5", layer.getToken(4).getParts()[1]);
        Assert.assertEquals("dem", layer.getToken(5).getString());

    }

    @Test
    public void testReadAndWriteBack_SurfaceForm() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_SURFACE);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-outputSurface.xml"));

        TokensLayer layer = TestUtils.read(TokensLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals("ponoči", layer.getToken(1).getString());
        Assert.assertEquals("t_1", layer.getToken(1).getID());
        Assert.assertEquals("po noči", layer.getToken(1).getSurfaceForm());
        Assert.assertNull(layer.getToken(1).getParts());
    }

    @Test
    public void testReadAndWriteBack_ExtraAttribute() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_ANY_ATTRIBUTE);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-outputAnyAttribute.xml"));

        TokensLayer layer = TestUtils.read(TokensLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        String anyAttribute = layer.getToken(2).getExtraAtrributes().keySet().iterator().next();
        Assert.assertEquals("baseForm", anyAttribute);
        Assert.assertEquals("schau", layer.getToken(2).getExtraAtrributes().get(anyAttribute));
    }
}
