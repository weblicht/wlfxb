/**
 *
 */
package eu.clarin.weblicht.wlfxb.lxlayers.test;

import eu.clarin.weblicht.wlfxb.lx.api.FrequenciesLayer;
import eu.clarin.weblicht.wlfxb.lx.api.FrequencyType;
import eu.clarin.weblicht.wlfxb.lx.xb.FrequenciesLayerStored;
import eu.clarin.weblicht.wlfxb.test.utils.TestUtils;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 * @author Yana Panchenko
 *
 */
public class FrequenciesTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT = "/data/lx-freq/layer-input.xml";
    private static final String OUTPUT = "layer-output.xml";
    private static final double delta = 0.0001;

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile(OUTPUT));


        FrequenciesLayer layer = TestUtils.read(FrequenciesLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals(8, layer.size());
        assertEquals(FrequencyType.absolute, layer.getType());
        assertEquals(100, layer.getFrequency(0).getValue(), delta);
        assertEquals(107, layer.getFrequency(layer.size() - 1).getValue(), delta);

    }
}
