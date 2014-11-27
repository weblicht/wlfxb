/**
 *
 */
package eu.clarin.weblicht.wlfxb.lxlayers.test;

import eu.clarin.weblicht.wlfxb.lx.api.PosTagsLayer;
import eu.clarin.weblicht.wlfxb.lx.xb.PosTagsLayerStored;
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
public class PosTagsTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT = "/data/lx-pos/layer-input.xml";
    private static final String OUTPUT = "layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile(OUTPUT));


        PosTagsLayer layer = TestUtils.read(PosTagsLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals("STTS", layer.getTagset());
        Assert.assertEquals(9, layer.size());
        Assert.assertEquals("NE", layer.getTag(0).getString());
        Assert.assertEquals("NE", layer.getTag(3).getString());
        Assert.assertEquals("NN", layer.getTag(4).getString());
        Assert.assertEquals("PPER", layer.getTag(layer.size() - 1).getString());

    }
}
