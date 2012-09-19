/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.test;

import de.tuebingen.uni.sfs.wlf1.io.TextCorpusStreamed;
import de.tuebingen.uni.sfs.wlf1.tc.api.DiscourseConnectivesLayer;
import de.tuebingen.uni.sfs.wlf1.tc.api.TextCorpus;
import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.tc.xb.TextCorpusLayerTag;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class TextCorpusDiscourseConnectivesTest extends AbstractTextCorpusTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/tc-dconn/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/tc-dconn/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/tc-dconn/output-expected.xml";
    private static final String OUTPUT_FILE = "/tmp/output.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeDConnDetect =
            EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterDConnDetect =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.DISCOURSE_CONNECTIVES);
    public static final Map<String, String> token2dct = new HashMap<String, String>();

    // semantically this example doesn't make sense, but is given just for the sake of testing
    static {
        token2dct.put("Käsepizza", "expansion");
        token2dct.put("Sie", "temporal");
    }

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterDConnDetect);
        DiscourseConnectivesLayer layer = tc.getDiscourseConnectivesLayer();
        Assert.assertEquals(2, layer.size());
        Assert.assertEquals(tc.getTokensLayer().getToken(3), layer.getTokens(layer.getConnective(0))[0]);
        Assert.assertEquals(tc.getTokensLayer().getToken(5), layer.getTokens(layer.getConnective(1))[0]);
    }

    @Test
    public void testReadWrite() throws Exception {
        TextCorpusStreamed tc = open(INPUT_FILE_WITHOUT_LAYER, OUTPUT_FILE, layersToReadBeforeDConnDetect);
        System.out.println(tc);
        DiscourseConnectivesLayer layer = tc.createDiscourseConnectivesLayer("TuebaDZ");
        for (int i = 0; i < tc.getTokensLayer().size(); i++) {
            Token token = tc.getTokensLayer().getToken(i);
            String connectiveType = recognize(token.getString());
            if (connectiveType != null) {
                // create and add part-of-speech tag to the tags layer
                layer.addConnective(Arrays.asList(new Token[]{token}), connectiveType);
            }
        }
        // IMPORTANT! close the streams!!!
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    // semantically this example doesn't make sense, but is given just for the sake of testing
    private String recognize(String tokenString) {
        return token2dct.get(tokenString);
    }
}
