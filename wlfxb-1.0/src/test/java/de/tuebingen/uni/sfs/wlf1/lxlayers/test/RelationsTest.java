/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.lxlayers.test;

import de.tuebingen.uni.sfs.wlf1.lx.api.Relation;
import de.tuebingen.uni.sfs.wlf1.lx.api.RelationsLayer;
import de.tuebingen.uni.sfs.wlf1.lx.xb.RelationsLayerStored;
import de.tuebingen.uni.sfs.wlf1.test.utils.TestUtils;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class RelationsTest {

    private static final String INPUT = "data/lx-rel/layer-input.xml";
    private static final String OUTPUT = "data/lx-rel/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = new FileInputStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        RelationsLayer layer = TestUtils.read(RelationsLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals(3, layer.size());
        Relation r0 = layer.getRelation(0);
        assertEquals("collocation", r0.getType());
        assertEquals("Adj-Noun", r0.getFunction());
        assertEquals(Integer.valueOf(100), r0.getFrequency());
        assertEquals("smt", r0.getSig().getMeasure());
        assertEquals((double) 3.1, r0.getSig().getValue(), 0.001);
        assertEquals("steife", layer.getWords(r0)[0]);
        assertEquals("Brise", layer.getWords(r0)[1]);
    }
}
