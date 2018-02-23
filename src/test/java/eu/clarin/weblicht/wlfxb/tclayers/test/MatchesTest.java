/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test;

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
 * @author Yana Panchenko
 *
 */
public class MatchesTest {

    private static final String INPUT = "/data/tc-matches/layer-input.xml";
    private static final String INPUT_ANY_ATTRIBUTES = "/data/tc-matches/layer-inputAnyAtt.xml";

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
    public void testReadAndWriteBackAnyAttributes() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_ANY_ATTRIBUTES);
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
        
        System.out.println(layer.getCorpus(0).getMatchedItems()[0].getCategoriesExtraAtrributes("cname"));

        Integer index = 0;
        for (String anyAttribute : layer.getCorpus(0).getMatchedItems()[0].getCategoriesExtraAtrributes("cname").keySet()) {
            if (index == 0) {
                Assert.assertEquals("baseForm", anyAttribute);
                Assert.assertEquals("baseFormCategory", layer.getCorpus(0).getMatchedItems()[0].getCategoriesExtraAtrributes("cname").get(anyAttribute));
            }
            if (index == 1) {
                Assert.assertEquals("alterForm", anyAttribute);
                Assert.assertEquals("alterFormCategory", layer.getCorpus(0).getMatchedItems()[0].getCategoriesExtraAtrributes("cname").get(anyAttribute));
            }

            index++;
        }
        
        index = 0;
        for (String anyAttribute : layer.getCorpus(0).getMatchedItems()[0].getTargetExtraAtrributes("tname").keySet()) {
            if (index == 0) {
                Assert.assertEquals("baseForm", anyAttribute);
                Assert.assertEquals("baseFormTarget", layer.getCorpus(0).getMatchedItems()[0].getTargetExtraAtrributes("tname").get(anyAttribute));
            }
            if (index == 1) {
                Assert.assertEquals("alterForm", anyAttribute);
                Assert.assertEquals("alterFormTarget", layer.getCorpus(0).getMatchedItems()[0].getTargetExtraAtrributes("tname").get(anyAttribute));
            }

            index++;
        }
    }
}
