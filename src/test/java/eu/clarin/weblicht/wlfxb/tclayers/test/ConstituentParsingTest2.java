/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test;

import eu.clarin.weblicht.wlfxb.tc.api.ConstituentParsingLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.ConstituentParsingLayerStored;
import eu.clarin.weblicht.wlfxb.test.utils.TestUtils;
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
public class ConstituentParsingTest2 {

    private static final String INPUT = "/data/tc-parsing/layer-input-2.xml";
    private static final String OUTPUT = "/tmp/layer-output-2.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = new FileInputStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        ConstituentParsingLayer layer = TestUtils.read(ConstituentParsingLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals("Tiger", layer.getTagset());
        Assert.assertEquals("TOP", layer.getParseRoot(0).getCategory());
        Assert.assertEquals("TOP", layer.getParseRoot(1).getCategory());
        Assert.assertEquals(2, layer.getParseRoot(0).getChildren().length);
        Assert.assertEquals(2, layer.getParseRoot(1).getChildren().length);
        Assert.assertEquals("HD", layer.getParseRoot(0).getChildren()[0].getChildren()[0].getChildren()[0].getEdge());
        Assert.assertEquals("SB", layer.getParseRoot(1).getChildren()[0].getChildren()[0].getEdge());
        Assert.assertEquals(true, layer.getParseRoot(0).getChildren()[0].getChildren()[0].getChildren()[0].getChildren()[0].isTerminal());
        Assert.assertEquals(true, layer.getParseRoot(1).getChildren()[0].getChildren()[0].getChildren()[0].isTerminal());
        Assert.assertEquals(1, layer.getParseRoot(0).getChildren()[0].getChildren()[2].getSecondaryEdgeChildren().length);
        Assert.assertEquals("some-secondary-edge-label", layer.getParseRoot(0).getChildren()[0].getChildren()[2].getSecondaryEdgeChildren()[0].getEdgeLabel());
    }
}
