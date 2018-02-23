/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test;

import eu.clarin.weblicht.wlfxb.tc.api.DiscourseConnectivesLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.DiscourseConnectivesLayerStored;
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
public class DiscourseConnectivesTest {

    private static final String INPUT = "/data/tc-dconn/layer-input.xml";
    private static final String INPUT_ANY_ATTRIBUTES = "/data/tc-dconn/layer-inputAnyAtt.xml";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        DiscourseConnectivesLayer layer = TestUtils.read(DiscourseConnectivesLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals(2, layer.size());
        Assert.assertEquals("TuebaDZ", layer.getTypesTagset());
        Assert.assertEquals("expansion", layer.getConnective(0).getType());
        Assert.assertEquals("temporal", layer.getConnective(1).getType());
    }

    @Test
    public void testReadAndWriteBackAnyAttributes() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_ANY_ATTRIBUTES);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        DiscourseConnectivesLayer layer = TestUtils.read(DiscourseConnectivesLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Integer index = 0;
        for (String anyAttribute : layer.getConnective(0).getExtraAtrributes().keySet()) {
            if (index == 0) {
                Assert.assertEquals("baseForm", anyAttribute);
                Assert.assertEquals("expansion1", layer.getConnective(0).getExtraAtrributes().get(anyAttribute));
            }
            if (index == 1) {
                Assert.assertEquals("alterForm", anyAttribute);
                Assert.assertEquals("expansion2", layer.getConnective(0).getExtraAtrributes().get(anyAttribute));
            }

            index++;
        }
    }
}
