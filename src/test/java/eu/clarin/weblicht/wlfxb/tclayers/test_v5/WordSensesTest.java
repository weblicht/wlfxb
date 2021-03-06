/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test_v5;

import eu.clarin.weblicht.wlfxb.tc.api.WordSensesLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.WordSensesLayerStored;
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
public class WordSensesTest {

    private static final String INPUT = "/data/tc-ws/layer-input.xml";
    private static final String INPUT_ANY_ATTRIBUTES = "/data_v5/tc-ws/layer-inputExtraAtt.xml";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        WordSensesLayer layer = TestUtils.read(WordSensesLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals(3, layer.size());
        Assert.assertEquals("GermaNet8.0", layer.getSource());
        Assert.assertEquals("75197", layer.getWordSense(0).getLexicalUnits().get(1));
        Assert.assertEquals("unbestimmbar", layer.getWordSense(1).getComment());
    }

    @Test
    public void testReadAndWriteBack_ExtraAttribute() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_ANY_ATTRIBUTES);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        WordSensesLayer layer = TestUtils.read(WordSensesLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        String anyAttribute = layer.getWordSense(0).getExtraAttributes().keySet().iterator().next();
        Assert.assertEquals("baseForm", anyAttribute);
        Assert.assertEquals("baseFormSense", layer.getWordSense(0).getExtraAttributes().get(anyAttribute));
    }

}
