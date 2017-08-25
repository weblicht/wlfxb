/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.clarin.weblicht.wlfxb.tclayers.test;

import eu.clarin.weblicht.wlfxb.tc.api.ChunkLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.ChunkLayerStored;
import eu.clarin.weblicht.wlfxb.test.utils.TestUtils;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.namespace.QName;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author felahi
 */
public class ChunkTest {

    private static final String INPUT = "/data/tc-chunk/layer-input.xml";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        ChunkLayer layer = TestUtils.read(ChunkLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals("tagset", layer.getTagset());
        Assert.assertEquals(2, layer.size());

        for (Integer index = 0; index < layer.size(); index++) {
            for (QName type : layer.getChunk(index).getType().keySet()) {
                String value = layer.getChunk(index).getType().get(type).toString();
                if (index==0) {
                    Assert.assertEquals("type", type.toString());
                    Assert.assertEquals("NP", value);
                }
                /*if (index==1) {
                    Assert.assertEquals("voice", type.toString());
                    Assert.assertEquals("VP", value);
                }*/
            }
        }

    }

    /*private void types(QName type, String value) {
        //Assert.assertEquals("type", type);
        //Assert.assertEquals("NP", value);
        if (type.toString().contains("type")) {
            System.out.println(" " + type.toString() + " " + value.toString());
        }
        if (type.toString().contains("voice")) {
            System.out.print(" " + type.toString() + " " + value.toString());
        }
    }*/

}
