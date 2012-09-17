/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.test;

import de.tuebingen.uni.sfs.wlf1.io.TextCorpusStreamed;
import de.tuebingen.uni.sfs.wlf1.tc.api.TextCorpus;
import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.tc.api.WordSplittingLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.TextCorpusLayerTag;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class TextCorpusWordSplittingTest extends AbstractTextCorpusTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/tc-wsplit/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/tc-wsplit/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/tc-wsplit/output-expected.xml";
    private static final String OUTPUT_FILE = "/tmp/output.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeSplitting =
            EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterSplitting =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.WORD_SPLITTINGS);
    public static final Map<String, int[]> token2split = new HashMap<String, int[]>();

    static {
        token2split.put("Käsepizza", new int[]{2, 4, 6});
        token2split.put("schmeckte", new int[]{5});
    }

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterSplitting);
        WordSplittingLayer layer = tc.getWordSplittingLayer();
        Assert.assertEquals(2, layer.size());
        Assert.assertEquals("syllables", layer.getType());
        Assert.assertEquals(tc.getTokensLayer().getToken(3), layer.getToken(layer.getSplit(0)));
    }

    @Test
    public void testReadWrite() throws Exception {
        TextCorpusStreamed tc = open(INPUT_FILE_WITHOUT_LAYER, OUTPUT_FILE, layersToReadBeforeSplitting);
        System.out.println(tc);
        // create word splittings layer, it's empty at first
        WordSplittingLayer splitsLayer = tc.createWordSplittingLayer("syllables");
        for (int i = 0; i < tc.getTokensLayer().size(); i++) {
            Token token = tc.getTokensLayer().getToken(i);
            int[] split = split(token.getString());
            if (split != null) {
                // create and add split to the layer
                splitsLayer.addSplit(token, split);
            }
        }
        // IMPORTANT close the streams!!!
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    private int[] split(String tokenString) {
        return token2split.get(tokenString);
    }
}
