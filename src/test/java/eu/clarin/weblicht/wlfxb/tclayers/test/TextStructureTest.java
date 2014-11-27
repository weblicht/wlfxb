/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test;

import eu.clarin.weblicht.wlfxb.tc.api.TextSpanType;
import eu.clarin.weblicht.wlfxb.tc.api.TextStructureLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.TextStructureLayerStored;
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
public class TextStructureTest {

    private static final String INPUT = "/data/tc-textstruct/layer-input.xml";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));


        TextStructureLayer layer = TestUtils.read(TextStructureLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals("page", layer.getSpan(0).getType());
        Assert.assertEquals("line", layer.getSpan(1).getType());
        Assert.assertEquals(Integer.valueOf(0), layer.getSpan(2).getStartChar());
        Assert.assertEquals("paragraph", layer.getSpan(2).getType());
    }
}
