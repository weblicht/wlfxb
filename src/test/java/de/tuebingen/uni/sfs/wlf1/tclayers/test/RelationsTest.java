/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tclayers.test;

import de.tuebingen.uni.sfs.wlf1.tc.api.RelationsLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.RelationsLayerStored;
import de.tuebingen.uni.sfs.wlf1.test.utils.TestUtils;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
@SuppressWarnings("deprecation")
public class RelationsTest {

    private static final String INPUT = "/data/tc-rels/layer-input.xml";
    private static final String OUTPUT = "/tmp/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        RelationsLayer layer = TestUtils.read(RelationsLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals("verb-arg", layer.getType());
        Assert.assertEquals(4, layer.size());
        Assert.assertEquals("subj", layer.getRelation(0).getFunction());

    }
}
