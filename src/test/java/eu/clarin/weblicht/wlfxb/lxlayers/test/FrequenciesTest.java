/**
 *
 */
package eu.clarin.weblicht.wlfxb.lxlayers.test;

import eu.clarin.weblicht.wlfxb.lx.api.FrequenciesLayer;
import eu.clarin.weblicht.wlfxb.lx.xb.FrequenciesLayerStored;
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
public class FrequenciesTest {

    private static final String INPUT = "/data/lx-freq/layer-input.xml";
    private static final String OUTPUT = "/tmp/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        FrequenciesLayer layer = TestUtils.read(FrequenciesLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals(9, layer.size());
        assertEquals(100, layer.getFrequency(0).getValue());
        assertEquals(108, layer.getFrequency(layer.size() - 1).getValue());

    }
}
