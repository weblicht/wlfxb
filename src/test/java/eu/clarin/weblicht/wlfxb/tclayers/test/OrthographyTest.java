/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test;

import eu.clarin.weblicht.wlfxb.tc.api.CorrectionOperation;
import eu.clarin.weblicht.wlfxb.tc.api.OrthographyLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.OrthographyLayerStored;
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
public class OrthographyTest {

    private static final String INPUT = "/data/tc-orth/layer-input.xml";
    private static final String OUTPUT = "/tmp/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
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
