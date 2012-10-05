/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test;

import eu.clarin.weblicht.wlfxb.tc.api.SentencesLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.SentencesLayerStored;
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
public class SentencesTest {

    private static final String INPUT = "/data/tc-sents/layer-input.xml";
    private static final String OUTPUT = "/tmp/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        SentencesLayer layer = TestUtils.read(SentencesLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals(2, layer.size());
        Assert.assertEquals(true, layer.hasCharOffsets());
        Assert.assertEquals(Integer.valueOf(0), layer.getSentence(0).getStartCharOffset());
        Assert.assertEquals(Integer.valueOf(24), layer.getSentence(0).getEndCharOffset());
    }
}
