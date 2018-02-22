/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test;

import eu.clarin.weblicht.wlfxb.tc.api.ConstituentParsingLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.ConstituentParsingLayerStored;
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
public class ConstituentParsingTest {

    private static final String INPUT = "/data/tc-parsing/layer-input.xml";
    private static final String  INPUT_ANY_ATTRIBUTE  = "/data/tc-parsing/layer-inputAnyAtt.xml";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

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
        Assert.assertEquals(true, layer.getParseRoot(0).getChildren()[0].getChildren()[0].getChildren()[0].getChildren()[0].isTerminal());
        Assert.assertEquals(true, layer.getParseRoot(1).getChildren()[0].getChildren()[0].getChildren()[0].isTerminal());

    }
    @Test
    public void testReadAndWriteBackAnyAttributes() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_ANY_ATTRIBUTE);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

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
        Assert.assertEquals(true, layer.getParseRoot(0).getChildren()[0].getChildren()[0].getChildren()[0].getChildren()[0].isTerminal());
        Assert.assertEquals(true, layer.getParseRoot(1).getChildren()[0].getChildren()[0].getChildren()[0].isTerminal());
        
        System.out.println(layer.getParseRoot(0).getChildren()[0].getChildren()[0].getChildren()[0].getChildren()[0]);
        
        Integer index=0;
         for (String anyAttribute : layer.getParseRoot(0).getChildren()[0].getChildren()[0].getChildren()[0].getChildren()[0].getExtraAtrributes().keySet()) {
            if (index == 0) {
                Assert.assertEquals("baseForm", anyAttribute);
                Assert.assertEquals("NE-HD", layer.getParseRoot(0).getChildren()[0].getChildren()[0].getChildren()[0].getChildren()[0].getExtraAtrributes().get(anyAttribute));
            }
            if (index == 1) {
                Assert.assertEquals("alterForm", anyAttribute);
                Assert.assertEquals("NE-HD-Nom.Sg", layer.getParseRoot(0).getChildren()[0].getChildren()[0].getChildren()[0].getChildren()[0].getExtraAtrributes().get(anyAttribute));
            }

            index++;
        }


    }
    
}
