/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.lxlayers.test;

import de.tuebingen.uni.sfs.wlf1.lx.api.LemmasLayer;
import de.tuebingen.uni.sfs.wlf1.lx.xb.LemmasLayerStored;
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
public class LemmasTest {

    private static final String INPUT = "data/lx-lemmas/layer-input.xml";
    private static final String OUTPUT = "data/lx-lemmas/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = new FileInputStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        LemmasLayer layer = TestUtils.read(LemmasLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals(9, layer.size());
        assertEquals("Peter", layer.getLemma(0).getString());
        assertEquals(".", layer.getLemma(layer.size() - 1).getString());

    }
}
