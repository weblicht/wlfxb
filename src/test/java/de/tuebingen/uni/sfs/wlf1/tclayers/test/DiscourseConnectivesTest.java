/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tclayers.test;

import de.tuebingen.uni.sfs.wlf1.tc.api.DiscourseConnectivesLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.DiscourseConnectivesLayerStored;
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
public class DiscourseConnectivesTest {

    private static final String INPUT = "/data/tc-dconn/layer-input.xml";
    private static final String OUTPUT = "/tmp/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


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
}
