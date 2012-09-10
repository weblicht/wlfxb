/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tclayers.test;

import de.tuebingen.uni.sfs.wlf1.tc.api.NamedEntitiesLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.NamedEntitiesLayerStored;
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
public class NamedEntitiesTest {

    private static final String INPUT = "data/tc-nes/layer-input.xml";
    private static final String OUTPUT = "data/tc-nes/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = new FileInputStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        NamedEntitiesLayer layer = TestUtils.read(NamedEntitiesLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals("MUC1990", layer.getType());
        assertEquals(1, layer.size());
        assertEquals("PERSON", layer.getEntity(0).getType());

    }
}
