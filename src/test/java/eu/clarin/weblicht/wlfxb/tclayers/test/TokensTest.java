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
    private static final String INPUT_SL = "/data/tc-tokens/layer-inputSL.xml";

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

        Assert.assertEquals(false, layer.hasCharOffsets());
        Assert.assertEquals(9, layer.size());

        Assert.assertEquals("Dan", layer.getToken(0).getString());
        Assert.assertEquals("t_0", layer.getToken(0).getID());
        Assert.assertEquals("Dann", layer.getToken(0).getSurfaceForm());
        Assert.assertEquals("t_0", layer.getToken(0).getParts()[0]);
        Assert.assertEquals("t_1", layer.getToken(0).getParts()[1]);

        Assert.assertEquals("n", layer.getToken(1).getString());
        Assert.assertEquals("t_1", layer.getToken(1).getID());
        Assert.assertEquals("schau", layer.getToken(2).getString());
        Assert.assertEquals("t_2", layer.getToken(2).getID());
        Assert.assertEquals("doch", layer.getToken(3).getString());
        Assert.assertEquals("t_3", layer.getToken(3).getID());
        Assert.assertEquals("mal", layer.getToken(4).getString());
        Assert.assertEquals("t_4", layer.getToken(4).getID());

        Assert.assertEquals("in", layer.getToken(5).getString());
        Assert.assertEquals("t_5", layer.getToken(5).getID());
        Assert.assertEquals("im", layer.getToken(5).getSurfaceForm());
        Assert.assertEquals("t_5", layer.getToken(5).getParts()[0]);
        Assert.assertEquals("t_6", layer.getToken(5).getParts()[1]);
    }

    @Test
    public void testReadAndWriteBackSL() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_SL);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-outputSL.xml"));

        TokensLayer layer = TestUtils.read(TokensLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals(false, layer.hasCharOffsets());
        Assert.assertEquals(4, layer.size());

        Assert.assertEquals("ne", layer.getToken(0).getString());
        Assert.assertEquals("t_0", layer.getToken(0).getID());
        Assert.assertEquals("neb", layer.getToken(0).getSurfaceForm());
        Assert.assertEquals("t_0", layer.getToken(0).getParts()[0]);
        Assert.assertEquals("t_1", layer.getToken(0).getParts()[1]);

        Assert.assertEquals("bi", layer.getToken(1).getString());
        Assert.assertEquals("t_1", layer.getToken(1).getID());

        Assert.assertEquals("ponoči", layer.getToken(2).getString());
        Assert.assertEquals("t_2", layer.getToken(2).getID());
        Assert.assertEquals("po noči", layer.getToken(2).getSurfaceForm());
        
        Assert.assertEquals(".", layer.getToken(3).getString());
        Assert.assertEquals("t_3", layer.getToken(3).getID());
    }
}
