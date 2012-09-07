/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tclayers.test;

import de.tuebingen.uni.sfs.wlf1.tc.api.ConstituentParsingLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.ConstituentParsingLayerStored;
import de.tuebingen.uni.sfs.wlf1.test.utils.TestUtils;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class ConstituentParsingTest {

    private static final String INPUT = "data/tc-parsing/layer-input.xml";
    private static final String OUTPUT = "data/tc-parsing/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = new FileInputStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        ConstituentParsingLayer layer = TestUtils.read(ConstituentParsingLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals("Tiger", layer.getTagset());
        assertEquals("TOP", layer.getParseRoot(0).getCategory());
        assertEquals("TOP", layer.getParseRoot(1).getCategory());
        assertEquals(2, layer.getParseRoot(0).getChildren().length);
        assertEquals(2, layer.getParseRoot(1).getChildren().length);
        assertEquals(true, layer.getParseRoot(0).getChildren()[0].getChildren()[0].getChildren()[0].getChildren()[0].isTerminal());
        assertEquals(true, layer.getParseRoot(1).getChildren()[0].getChildren()[0].getChildren()[0].isTerminal());

    }
}
