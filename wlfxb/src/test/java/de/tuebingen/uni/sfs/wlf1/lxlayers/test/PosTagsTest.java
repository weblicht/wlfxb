/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.lxlayers.test;

import de.tuebingen.uni.sfs.wlf1.lx.api.PosTagsLayer;
import de.tuebingen.uni.sfs.wlf1.lx.xb.PosTagsLayerStored;
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
public class PosTagsTest {

    private static final String INPUT = "data/lx-pos/layer-input.xml";
    private static final String OUTPUT = "data/lx-pos/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = new FileInputStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        PosTagsLayer layer = TestUtils.read(PosTagsLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals("STTS", layer.getTagset());
        assertEquals(10, layer.size());
        assertEquals("NE", layer.getTag(0).getString());
        assertEquals("NE", layer.getTag(3).getString());
        assertEquals("NN", layer.getTag(4).getString());
        assertEquals("$.", layer.getTag(layer.size() - 1).getString());

    }
}
