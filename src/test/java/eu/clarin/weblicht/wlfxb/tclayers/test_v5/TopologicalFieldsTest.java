/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test;

import eu.clarin.weblicht.wlfxb.tc.api.TopologicalFieldsLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.TopologicalFieldsLayerStored;
import eu.clarin.weblicht.wlfxb.test.utils.TestUtils;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 * @author Neele Witte
 *
 */
public class TopologicalFieldsTest {

    private static final String INPUT = "/data_v5/tc-topo/layer-input.xml";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));


        TopologicalFieldsLayer layer = TestUtils.read(TopologicalFieldsLayerStored.class, is);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        /**
         * Checks if the topolofical field tagset matches the expected tagset: DANEIKDK
         * Checks if there are 9 topological fields that have been tagged
         * Checks if the first topological field is VF (Vorfeld) and the last field is UNK (topo field for punctuation)
         */
        Assert.assertEquals("DANIELDK", layer.getTagset());
        Assert.assertEquals(9, layer.size());
        Assert.assertEquals("VF", layer.getTag(0).getString());
        Assert.assertEquals("UNK", layer.getTag(layer.size() - 1).getString());

    }
}
