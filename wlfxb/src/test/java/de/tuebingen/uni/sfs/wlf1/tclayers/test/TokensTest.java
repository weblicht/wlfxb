/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tclayers.test;

import de.tuebingen.uni.sfs.wlf1.tc.api.TokensLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.TokensLayerStored;
import de.tuebingen.uni.sfs.wlf1.test.utils.TestUtils;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class TokensTest {

    private static final String INPUT = "data/tc-tokens/layer-input.xml";
    private static final String OUTPUT = "data/tc-tokens/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = new FileInputStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        TokensLayer layer = TestUtils.read(TokensLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals(true, layer.hasCharOffsets());
        assertEquals(9, layer.size());
        assertEquals("Peter", layer.getToken(0).getString());
        assertEquals("t1", layer.getToken(0).getID());
        assertEquals(Integer.valueOf(0), layer.getToken(0).getStart());
        assertEquals(Integer.valueOf(5), layer.getToken(0).getEnd());
        assertEquals(".", layer.getToken(layer.size() - 1).getString());

    }
}
