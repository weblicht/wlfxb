/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test_v5;

import eu.clarin.weblicht.wlfxb.tc.api.DependencyParsingLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.DependencyParsingLayerStored;
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
public class DependencyParsingTest {

    private static final String INPUT = "/data/tc-dparsing/layer-input.xml";
    private static final String INPUT_ANY_ATTRIBUTES = "/data_v5/tc-dparsing/layer-inputExtraAtt.xml";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        DependencyParsingLayer layer = TestUtils.read(DependencyParsingLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals("Tiger", layer.getTagset());
        Assert.assertEquals(true, layer.hasEmptyTokens());
        Assert.assertEquals(false, layer.hasMultipleGovernors());
        Assert.assertEquals(2, layer.size());
        Assert.assertEquals(4, layer.getParse(0).getDependencies().length);
        Assert.assertEquals("SUBJ", layer.getParse(0).getDependencies()[1].getFunction());
    }

    @Test
    public void testReadAndWriteBack_ExtraAttribute() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_ANY_ATTRIBUTES);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        DependencyParsingLayer layer = TestUtils.read(DependencyParsingLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        String anyAttribute = layer.getParse(0).getDependencies()[1].getExtraAttributes().keySet().iterator().next();
        Assert.assertEquals("baseForm", anyAttribute);
        Assert.assertEquals("deps", layer.getParse(0).getDependencies()[1].getExtraAttributes().get(anyAttribute));

    }
}
