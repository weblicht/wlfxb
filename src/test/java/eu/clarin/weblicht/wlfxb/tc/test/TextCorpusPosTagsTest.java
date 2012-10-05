/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.test;

import eu.clarin.weblicht.wlfxb.io.TextCorpusStreamed;
import eu.clarin.weblicht.wlfxb.tc.api.PosTagsLayer;
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
public class TextCorpusPosTagsTest extends AbstractTextCorpusTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/tc-pos/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/tc-pos/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/tc-pos/output-expected.xml";
    private static final String OUTPUT_FILE = "/tmp/output.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforePosTagging =
            EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterPosTagging =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.POSTAGS);
    public static final Map<String, String> token2Pos = new HashMap<String, String>();

    static {
        token2Pos.put("Peter", "NE");
        token2Pos.put("aß", "VVFIN");
        token2Pos.put("eine", "ART");
        token2Pos.put("Käsepizza", "NE");
        token2Pos.put(".", "$.");
        token2Pos.put("Sie", "PPER");
        token2Pos.put("schmeckte", "VVFIN");
        token2Pos.put("ihm", "PPER");
    }

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterPosTagging);
        PosTagsLayer layer = tc.getPosTagsLayer();
        Assert.assertEquals(9, layer.size());
        Assert.assertEquals("NE", layer.getTag(0).getString());
        Assert.assertEquals(tc.getTokensLayer().getToken(0), layer.getTokens(layer.getTag(0))[0]);
    }

    @Test
    public void testReadWrite() throws Exception {
        TextCorpusStreamed tc = open(INPUT_FILE_WITHOUT_LAYER, OUTPUT_FILE, layersToReadBeforePosTagging);
        System.out.println(tc);
        // create part of speech layer, it's empty at first
        PosTagsLayer tags = tc.createPosTagsLayer("STTS");
        for (int i = 0; i < tc.getTokensLayer().size(); i++) {
            Token token = tc.getTokensLayer().getToken(i);
            String posTag = tag(token.getString());
            // create and add part-of-speech tag to the tags layer
            tags.addTag(posTag, token);
        }
        // IMPORTANT close the streams!!!
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    private String tag(String tokenString) {
        return token2Pos.get(tokenString);
    }
}
