/**
 *
 */
package eu.clarin.weblicht.wlfxb.edlayers.test;

import eu.clarin.weblicht.wlfxb.ed.api.SpeechSignalLayer;
import eu.clarin.weblicht.wlfxb.ed.xb.SpeechSignalLayerStored;
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
