/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tclayers.test;

import de.tuebingen.uni.sfs.wlf1.tc.api.ReferencesLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.ReferencesLayerStored;
import de.tuebingen.uni.sfs.wlf1.test.utils.TestUtils;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class ReferencesTest {

    private static final String INPUT = "/data/tc-refs/layer-input.xml";
    private static final String OUTPUT = "/tmp/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        ReferencesLayer layer = TestUtils.read(ReferencesLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals("BART", layer.getTypetagset());
        Assert.assertEquals("TuebaDZ", layer.getReltagset());
        Assert.assertEquals(true, layer.hasExternalReferences());
        Assert.assertEquals("Wikipedia", layer.getExternalReferenceSource());
        Assert.assertEquals(2, layer.size());
        Assert.assertEquals("http://de.wikipedia.org/wiki/Wahre_Finnen", layer.getReferencedEntity(0).getExternalId());
        Assert.assertEquals(2, layer.getReferencedEntity(0).getReferences().length);
        Assert.assertEquals("pro.per3", layer.getReferencedEntity(0).getReferences()[0].getType());
        Assert.assertEquals("cataphoric", layer.getReferencedEntity(0).getReferences()[0].getRelation());
    }
}
