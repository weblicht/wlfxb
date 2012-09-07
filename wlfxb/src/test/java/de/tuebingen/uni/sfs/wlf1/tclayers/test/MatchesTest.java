/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tclayers.test;

import de.tuebingen.uni.sfs.wlf1.tc.api.MatchesLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.MatchesLayerStored;
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
public class MatchesTest {

    private static final String INPUT = "data/tc-matches/layer-input.xml";
    private static final String OUTPUT = "data/tc-matches/layer-output.xml";

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = new FileInputStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        MatchesLayer layer = TestUtils.read(MatchesLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals("cqp", layer.getQueryType());
        assertEquals("tb_lemma=\"Peter\" | tb_lemma=\"schmecken\"", layer.getQueryString());
        assertEquals(1, layer.size());
        assertEquals("wcorp-1", layer.getCorpus(0).getName());
        assertEquals("some-pid", layer.getCorpus(0).getPID());
        assertEquals(2, layer.getCorpus(0).getMatchedItems().length);
        assertEquals("5-1023", layer.getCorpus(0).getMatchedItems()[0].getOriginCorpusTokenIds()[0]);
        assertEquals("t1", layer.getCorpus(0).getMatchedItems()[0].getTargetValue("tname"));
        assertEquals("cval", layer.getCorpus(0).getMatchedItems()[0].getCategoryValue("cname"));
    }
}
