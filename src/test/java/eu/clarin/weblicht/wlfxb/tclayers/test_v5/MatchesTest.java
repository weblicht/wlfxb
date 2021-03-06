/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test_v5;

import eu.clarin.weblicht.wlfxb.tc.api.MatchesLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.MatchesLayerStored;
import eu.clarin.weblicht.wlfxb.test.utils.TestUtils;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 * @author Yana Panchenko and Mohammad Fazleh Elahi
 *
 */
public class MatchesTest {

    private static final String INPUT = "/data/tc-matches/layer-input.xml";
    private static final String INPUT_ANY_ATTRIBUTES = "/data_v5/tc-matches/layer-inputExtraAtt.xml";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        MatchesLayer layer = TestUtils.read(MatchesLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals("cqp", layer.getQueryType());
        Assert.assertEquals("tb_lemma=\"Peter\" | tb_lemma=\"schmecken\"", layer.getQueryString());
        Assert.assertEquals(1, layer.size());
        Assert.assertEquals("wcorp-1", layer.getCorpus(0).getName());
        Assert.assertEquals("some-pid", layer.getCorpus(0).getPID());
        Assert.assertEquals(2, layer.getCorpus(0).getMatchedItems().length);
        Assert.assertEquals("5-1023", layer.getCorpus(0).getMatchedItems()[0].getOriginCorpusTokenIds()[0]);
        Assert.assertEquals("t1", layer.getCorpus(0).getMatchedItems()[0].getTargetValue("tname"));
        Assert.assertEquals("cval", layer.getCorpus(0).getMatchedItems()[0].getCategoryValue("cname"));
    }

    @Test
    public void testReadAndWriteBack_ExtraAttribute() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_ANY_ATTRIBUTES);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        MatchesLayer layer = TestUtils.read(MatchesLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        String anyAttributeCategory = layer.getCorpus(0).getMatchedItems()[0].getCategoriesExtraAttributes("cname").keySet().iterator().next();
        Assert.assertEquals("baseForm", anyAttributeCategory);
        Assert.assertEquals("baseFormCategory", layer.getCorpus(0).getMatchedItems()[0].getCategoriesExtraAttributes("cname").get(anyAttributeCategory));

        String anyAttributeTarget = layer.getCorpus(0).getMatchedItems()[0].getTargetExtraAttributes("tname").keySet().iterator().next();
        Assert.assertEquals("baseForm", anyAttributeTarget);
        Assert.assertEquals("baseFormTarget", layer.getCorpus(0).getMatchedItems()[0].getTargetExtraAttributes("tname").get(anyAttributeTarget));
    }
}
