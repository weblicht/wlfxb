/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.test;

import eu.clarin.weblicht.wlfxb.io.TextCorpusStreamed;
import eu.clarin.weblicht.wlfxb.tc.api.RelationsLayer;
import eu.clarin.weblicht.wlfxb.tc.api.TextCorpus;
import eu.clarin.weblicht.wlfxb.tc.api.Token;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
@SuppressWarnings("deprecation")
public class TextCorpusRelationsTest extends AbstractTextCorpusTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/tc-rels/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/tc-rels/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/tc-rels/output-expected.xml";
    private static final String OUTPUT_FILE = "/tmp/output.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeRelTagging =
            EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterRelTagging =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.RELATIONS);
    public static final Map<String, String> token2ann = new HashMap<String, String>();

    static {
        token2ann.put("Peter", "subj");
        token2ann.put("Käsepizza", "obj");
        token2ann.put("Sie", "subj");
        token2ann.put("ihm", "obj");
    }

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterRelTagging);
        RelationsLayer layer = tc.getRelationsLayer();
        Assert.assertEquals(4, layer.size());
        Assert.assertEquals("subj", layer.getRelation(0).getFunction());
        Assert.assertEquals(tc.getTokensLayer().getToken(0), layer.getTokens(layer.getRelation(0))[0]);
    }

    @Test
    public void testReadWrite() throws Exception {
        TextCorpusStreamed tc = open(INPUT_FILE_WITHOUT_LAYER, OUTPUT_FILE, layersToReadBeforeRelTagging);
        System.out.println(tc);
        // create relations layer, it's empty at first
        RelationsLayer rels = tc.createRelationsLayer("verb-arg");
        for (int i = 0; i < tc.getTokensLayer().size(); i++) {
            Token token = tc.getTokensLayer().getToken(i);
            String relFuncTag = tag(token.getString());
            if (relFuncTag != null) {
                // create and add relation tag to the layer
                rels.addRelation(relFuncTag, token);
            }
        }
        // IMPORTANT close the streams!!!
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    private String tag(String tokenString) {
        return token2ann.get(tokenString);
    }
}
