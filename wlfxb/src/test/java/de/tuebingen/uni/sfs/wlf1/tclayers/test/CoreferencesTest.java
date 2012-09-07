/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tclayers.test;

import de.tuebingen.uni.sfs.wlf1.tc.api.CoreferencesLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.CoreferencesLayerStored;
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
public class CoreferencesTest {

    private static final String INPUT = "data/tc-corefs/layer-input.xml";
    private static final String OUTPUT = "data/tc-corefs/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = new FileInputStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        CoreferencesLayer layer = TestUtils.read(CoreferencesLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals("BART", layer.getTagset());
        assertEquals(true, layer.hasExternalReferences());
        assertEquals("Wikipedia", layer.getExternalReferenceSource());
        assertEquals(2, layer.size());
        assertEquals("ORG", layer.getReferent(0).getType());
        assertEquals("http://de.wikipedia.org/wiki/Wahre_Finnen", layer.getReferent(0).getExternalId());
        assertEquals(2, layer.getReferent(0).getCoreferences().length);
        assertEquals(null, layer.getReferent(0).getCoreferences()[0].getSemanticRole());
        assertEquals("pro.per3", layer.getReferent(0).getCoreferences()[0].getType());
    }
}
