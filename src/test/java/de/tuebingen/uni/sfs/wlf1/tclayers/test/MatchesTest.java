/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tclayers.test;

import de.tuebingen.uni.sfs.wlf1.tc.api.MatchesLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.MatchesLayerStored;
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
public class MatchesTest {

    private static final String INPUT = "/data/tc-matches/layer-input.xml";
    private static final String OUTPUT = "/tmp/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


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
}
