/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.edlayers.test;

import de.tuebingen.uni.sfs.wlf1.ed.api.SpeechSignalLayer;
import de.tuebingen.uni.sfs.wlf1.ed.xb.SpeechSignalLayerStored;
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
public class SpeechSignalTest {

    private static final String INPUT = "/data/ed-speechsig/layer-input.xml";
    private static final String OUTPUT = "/tmp/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        SpeechSignalLayer layer = TestUtils.read(SpeechSignalLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals("audio/wav", layer.getDataMimeType());
        Assert.assertEquals(1, layer.getNumberOfChannels().intValue());
        Assert.assertEquals("http://arc:8080/drop-off/storage/g046acn1_037_AFI.wav", layer.getLink());

    }
}
