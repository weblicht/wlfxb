/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tclayers.test;

import de.tuebingen.uni.sfs.wlf1.tc.api.WordSplittingLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.WordSplittingLayerStored;
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
public class WordSplittingTest {

    private static final String INPUT = "/data/tc-wsplit/layer-input.xml";
    private static final String OUTPUT = "/tmp/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        WordSplittingLayer layer = TestUtils.read(WordSplittingLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals("syllables", layer.getType());
        Assert.assertEquals(2, layer.size());
        Assert.assertArrayEquals(new int[]{2, 4, 6}, layer.getSplit(0).getIndices());

    }
}
