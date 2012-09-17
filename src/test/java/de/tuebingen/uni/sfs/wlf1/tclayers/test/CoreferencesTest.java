/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tclayers.test;

import de.tuebingen.uni.sfs.wlf1.tc.api.CoreferencesLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.CoreferencesLayerStored;
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
public class CoreferencesTest {

    private static final String INPUT = "/data/tc-corefs/layer-input.xml";
    private static final String OUTPUT = "/tmp/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        CoreferencesLayer layer = TestUtils.read(CoreferencesLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals("BART", layer.getTagset());
        Assert.assertEquals(true, layer.hasExternalReferences());
        Assert.assertEquals("Wikipedia", layer.getExternalReferenceSource());
        Assert.assertEquals(2, layer.size());
        Assert.assertEquals("ORG", layer.getReferent(0).getType());
        Assert.assertEquals("http://de.wikipedia.org/wiki/Wahre_Finnen", layer.getReferent(0).getExternalId());
        Assert.assertEquals(2, layer.getReferent(0).getCoreferences().length);
        Assert.assertEquals(null, layer.getReferent(0).getCoreferences()[0].getSemanticRole());
        Assert.assertEquals("pro.per3", layer.getReferent(0).getCoreferences()[0].getType());
    }
}
