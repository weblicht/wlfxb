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

/**
 * @author Yana Panchenko
 *
 */
public class TokensTest {

    private static final String INPUT = "/data/tc-tokens/layer-input.xml";
    private static final String OUTPUT = "/tmp/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


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
}
