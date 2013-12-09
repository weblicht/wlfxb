/**
 *
 */
package eu.clarin.weblicht.wlfxb.lxlayers.test;

import eu.clarin.weblicht.wlfxb.lx.api.Synonym;
import eu.clarin.weblicht.wlfxb.lx.api.SynonymsLayer;
import eu.clarin.weblicht.wlfxb.lx.xb.SynonymsLayerStored;
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
public class SynonymsTest {

    private static final String INPUT = "/data/lx-syns/layer-input.xml";
    private static final String OUTPUT = "/tmp/layer-output.xml";
    private static final double delta = 0.0001;

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        SynonymsLayer layer = TestUtils.read(SynonymsLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals(2, layer.size());
        Synonym c0 = layer.getSynonym(0);
        assertEquals(2.7, c0.getSig().getValue(), delta);
        assertEquals("sich ernähren", layer.getTermsAsStrings(c0, false)[0]);
        Synonym c1 = layer.getSynonym(1);
        assertEquals("bezwingen", layer.getTermsAsStrings(c1, false)[1]);
    }
}
