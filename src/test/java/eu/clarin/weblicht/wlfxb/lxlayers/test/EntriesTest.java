/**
 *
 */
package eu.clarin.weblicht.wlfxb.lxlayers.test;

import eu.clarin.weblicht.wlfxb.lx.api.EntriesLayer;
import eu.clarin.weblicht.wlfxb.lx.api.EntryType;
import eu.clarin.weblicht.wlfxb.lx.xb.EntriesLayerStored;
import eu.clarin.weblicht.wlfxb.test.utils.TestUtils;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class EntriesTest {

    private static final String INPUT = "/data/lx-entries/layer-input.xml";
    private static final String OUTPUT = "/tmp/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        EntriesLayer layer = TestUtils.read(EntriesLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals(8, layer.size());
        Assert.assertEquals(EntryType.lemmas, layer.getType());
        Assert.assertEquals("Peter", layer.getEntry(0).getString());
        Assert.assertEquals("er", layer.getEntry(layer.size() - 1).getString());

    }
}
