/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.clarin.weblicht.wlfxb.tclayers.test_v5;

import eu.clarin.weblicht.wlfxb.tc.xb.ChunksLayerStored;
import eu.clarin.weblicht.wlfxb.test.utils.TestUtils;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import eu.clarin.weblicht.wlfxb.tc.api.ChunksLayer;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public class ChunkTest {

    private static final String INPUT = "/data_v5/tc-chunk/layer-input.xml";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        ChunksLayer layer = TestUtils.read(ChunksLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals(null, layer.getTagset());
        Assert.assertEquals(8, layer.size());

        Integer index = 0;
        for (String anyAttribute : layer.getChunk(1).getTypes().keySet()) {
            if (index == 0) {
                Assert.assertEquals("type", anyAttribute);
                Assert.assertEquals("VP", layer.getChunk(1).getTypes().get(anyAttribute));
            }
            if (index == 1) {
                Assert.assertEquals("voice", anyAttribute);
                Assert.assertEquals("none", layer.getChunk(1).getTypes().get(anyAttribute));
            }
            index++;
        }
    }
}
