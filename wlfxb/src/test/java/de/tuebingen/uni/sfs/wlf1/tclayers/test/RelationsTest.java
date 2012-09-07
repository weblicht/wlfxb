/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tclayers.test;

import de.tuebingen.uni.sfs.wlf1.tc.api.RelationsLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.RelationsLayerStored;
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
@SuppressWarnings("deprecation")
public class RelationsTest {

    private static final String INPUT = "data/tc-rels/layer-input.xml";
    private static final String OUTPUT = "data/tc-rels/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = new FileInputStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        RelationsLayer layer = TestUtils.read(RelationsLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals("verb-arg", layer.getType());
        assertEquals(4, layer.size());
        assertEquals("subj", layer.getRelation(0).getFunction());

    }
}
