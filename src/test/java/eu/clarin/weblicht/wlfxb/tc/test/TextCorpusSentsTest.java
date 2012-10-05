/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.test;

import eu.clarin.weblicht.wlfxb.io.TextCorpusStreamed;
import eu.clarin.weblicht.wlfxb.tc.api.SentencesLayer;
import eu.clarin.weblicht.wlfxb.tc.api.TextCorpus;
import eu.clarin.weblicht.wlfxb.tc.api.Token;
import eu.clarin.weblicht.wlfxb.tc.api.TokensLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class TextCorpusSentsTest extends AbstractTextCorpusTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/tc-sents/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/tc-sents/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/tc-sents/output-expected.xml";
    private static final String OUTPUT_FILE = "/tmp/output.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeSentDetect =
            EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterSentDetect =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.SENTENCES);

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterSentDetect);
        SentencesLayer layer = tc.getSentencesLayer();
        Assert.assertEquals(2, layer.size());
        Assert.assertEquals(tc.getTokensLayer().getToken(0), layer.getTokens(layer.getSentence(0))[0]);
        Assert.assertEquals(tc.getTokensLayer().getToken(4), layer.getTokens(layer.getSentence(0))[4]);
    }

    @Test
    public void testReadWrite() throws Exception {
        TextCorpusStreamed tc = open(INPUT_FILE_WITHOUT_LAYER, OUTPUT_FILE, layersToReadBeforeSentDetect);
        System.out.println(tc);
        SentencesLayer layer = tc.createSentencesLayer();
        boolean[] boundaries = detectSentenceBoundaries(tc.getTokensLayer());

        List<Token> tokens = new ArrayList<Token>();
        for (int i = 0; i < tc.getTokensLayer().size(); i++) {
            Token token = tc.getTokensLayer().getToken(i);
            tokens.add(token);
            if (boundaries[i]) {
                // create and add sentence to the sentences layer
                layer.addSentence(tokens);
                tokens = new ArrayList<Token>();
            }
        }
        // IMPORTANT! close the streams!!!
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    private boolean[] detectSentenceBoundaries(TokensLayer tokensLayer) {
        boolean[] b = new boolean[tokensLayer.size()];
        for (int i = 0; i < tokensLayer.size(); i++) {
            if (tokensLayer.getToken(i).getString().equals(".")) {
                b[i] = true;
            }
        }
        return b;
    }
}
