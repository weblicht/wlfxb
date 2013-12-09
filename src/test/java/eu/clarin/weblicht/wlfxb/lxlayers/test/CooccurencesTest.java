/**
 *
 */
package eu.clarin.weblicht.wlfxb.lxlayers.test;

import eu.clarin.weblicht.wlfxb.lx.api.Cooccurrence;
import eu.clarin.weblicht.wlfxb.lx.api.CooccurrenceFunction;
import eu.clarin.weblicht.wlfxb.lx.api.CooccurrencesLayer;
import eu.clarin.weblicht.wlfxb.lx.xb.CooccurrencesLayerStored;
import eu.clarin.weblicht.wlfxb.test.utils.TestUtils;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class CooccurencesTest {

    private static final String INPUT = "/data/lx-cooc/layer-input.xml";
    private static final String OUTPUT = "/tmp/layer-output.xml";
    private static final double delta = 0.0001;

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        CooccurrencesLayer layer = TestUtils.read(CooccurrencesLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals(2, layer.size());
        Cooccurrence c0 = layer.getCooccurrence(0);
        assertEquals(CooccurrenceFunction.sentence, c0.getFunction());
        assertEquals(2.7, c0.getSig().getValue(), delta);
        assertEquals("Banana", layer.getTermsAsStrings(c0, false)[0]);
        Cooccurrence c1 = layer.getCooccurrence(1);
        assertEquals(CooccurrenceFunction.left_neighbour, c1.getFunction());
    }
}
