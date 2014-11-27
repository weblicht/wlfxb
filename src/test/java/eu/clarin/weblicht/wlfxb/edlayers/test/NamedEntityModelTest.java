/**
 *
 */
package eu.clarin.weblicht.wlfxb.edlayers.test;

import eu.clarin.weblicht.wlfxb.ed.api.NamedEntityModelLayer;
import eu.clarin.weblicht.wlfxb.ed.api.SpeechSignalLayer;
import eu.clarin.weblicht.wlfxb.ed.xb.NamedEntityModelLayerStored;
import eu.clarin.weblicht.wlfxb.ed.xb.SpeechSignalLayerStored;
import eu.clarin.weblicht.wlfxb.test.utils.TestUtils;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 * @author Yana Panchenko
 *
 */
public class NamedEntityModelTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT = "/data/ed-nemodel/layer-input.xml";
    private static final String OUTPUT = "layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile(OUTPUT));


        NamedEntityModelLayer layer = TestUtils.read(NamedEntityModelLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals("application/octet-stream", layer.getDataMimeType());
        Assert.assertEquals("conll2003", layer.getNamedEntitiesType());
        Assert.assertEquals("opennlp-1.5", layer.getModelType());
        Assert.assertEquals("http://arc:8080/drop-off/storage/test.zip", layer.getLink());
    }
}
