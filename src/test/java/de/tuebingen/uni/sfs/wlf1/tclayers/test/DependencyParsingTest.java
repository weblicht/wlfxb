/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tclayers.test;

import de.tuebingen.uni.sfs.wlf1.tc.api.DependencyParsingLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.DependencyParsingLayerStored;
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
public class DependencyParsingTest {

    private static final String INPUT = "/data/tc-dparsing/layer-input.xml";
    private static final String OUTPUT = "/tmp/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


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
}
