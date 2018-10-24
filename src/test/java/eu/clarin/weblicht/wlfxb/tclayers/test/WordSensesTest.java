/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test;

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
 * @author Yana Panchenko
 *
 */
public class WordSensesTest {

    private static final String INPUT = "/data/tc-ws/layer-input.xml";

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
}
