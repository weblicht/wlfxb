/**
 *
 */
package eu.clarin.weblicht.wlfxb.lxlayers.test;

import eu.clarin.weblicht.wlfxb.lx.api.SyllabificationsLayer;
import eu.clarin.weblicht.wlfxb.lx.xb.SyllabificationsLayerStored;
import eu.clarin.weblicht.wlfxb.test.utils.TestUtils;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class SyllabificationsTest {

    private static final String INPUT = "/data/lx-syl/layer-input.xml";
    private static final String OUTPUT = "/tmp/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        SyllabificationsLayer layer = TestUtils.read(SyllabificationsLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals(7, layer.size());
        assertEquals("pe-ter", layer.getSyllabification(0).getString().toLowerCase());

    }
}
