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
 * @author Yana Panchenko
 *
 */
public class TokensTest {

    private static final String INPUT = "/data/tc-tokens/layer-input.xml";
    private static final String INPUT_UD = "/data/tc-tokens/layer-inputUD.xml";

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
    public void testReadAndWriteBackUD() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_UD);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-outputUD.xml"));

        TokensLayer layer = TestUtils.read(TokensLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals(true, layer.hasCharOffsets());
        Assert.assertEquals(2, layer.size());

        Assert.assertEquals("in", layer.getToken(0).getString());
        Assert.assertEquals("t_1", layer.getToken(0).getID());
        Assert.assertEquals(Long.valueOf(0), layer.getToken(0).getStart());
        Assert.assertEquals(Long.valueOf(2), layer.getToken(0).getEnd());
        Assert.assertEquals("im", layer.getToken(0).getSurfaceForm());
        Assert.assertEquals("t_1", layer.getToken(0).getParts()[0]);
        Assert.assertEquals("t_2", layer.getToken(0).getParts()[1]);

        Assert.assertEquals("dem", layer.getToken(1).getString());
        Assert.assertEquals("t_2", layer.getToken(1).getID());
        Assert.assertEquals(Long.valueOf(3), layer.getToken(1).getStart());
        Assert.assertEquals(Long.valueOf(6), layer.getToken(1).getEnd());
        Assert.assertNull(layer.getToken(1).getSurfaceForm());
        Assert.assertNull(layer.getToken(1).getParts());
    }
}
