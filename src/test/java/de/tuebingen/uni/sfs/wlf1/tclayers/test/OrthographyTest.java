/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tclayers.test;

import de.tuebingen.uni.sfs.wlf1.tc.api.CorrectionOperation;
import de.tuebingen.uni.sfs.wlf1.tc.api.OrthographyLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.OrthographyLayerStored;
import de.tuebingen.uni.sfs.wlf1.test.utils.TestUtils;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class OrthographyTest {

    private static final String INPUT = "/data/tc-orth/layer-input.xml";
    private static final String OUTPUT = "/tmp/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = new FileInputStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        OrthographyLayer layer = TestUtils.read(OrthographyLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals(2, layer.size());
        Assert.assertEquals("will", layer.getCorrection(0).getString());
        Assert.assertEquals("essen", layer.getCorrection(1).getString());
        Assert.assertEquals(CorrectionOperation.replace, layer.getCorrection(0).getOperation());
    }
}
