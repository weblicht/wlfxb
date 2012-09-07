/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.test;

import de.tuebingen.uni.sfs.wlf1.io.TextCorpusStreamed;
import de.tuebingen.uni.sfs.wlf1.tc.api.TextCorpus;
import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.tc.api.WordSplittingLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.TextCorpusLayerTag;
import de.tuebingen.uni.sfs.wlf1.test.utils.TestUtils;
import java.io.*;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class TextCorpusWordSplittingTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "data/tc-wsplit/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "data/tc-wsplit/tcf-after.xml";
    private static final String OUTPUT_FILE = "data/tc-wsplit/output.xml";
    private static final String EXPECTED_OUTPUT_FILE = "data/tc-wsplit/output-expected.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeSplitting =
            EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterSplitting =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.WORD_SPLITTINGS);
    public static Map<String, int[]> token2split = new HashMap<String, int[]>();

    static {
        token2split.put("Käsepizza", new int[]{2, 4, 6});
        token2split.put("schmeckte", new int[]{5});
    }

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterSplitting);
        WordSplittingLayer layer = tc.getWordSplittingLayer();
        assertEquals(2, layer.size());
        assertEquals("syllables", layer.getType());
        assertEquals(tc.getTokensLayer().getToken(3), layer.getToken(layer.getSplit(0)));
    }

    private TextCorpus read(String file, EnumSet<TextCorpusLayerTag> layersToRead) throws Exception {
        InputStream is = new FileInputStream(INPUT_FILE_WITH_LAYER);
        TextCorpusStreamed tc = new TextCorpusStreamed(is, layersToRead);
        tc.close();
        System.out.println(tc);
        return tc;
    }

    @Test
    public void testReadWrite() throws Exception {
        File file = new File(INPUT_FILE_WITHOUT_LAYER);
        InputStream is = new FileInputStream(file);
        File ofile = new File(OUTPUT_FILE);
        OutputStream os = new FileOutputStream(ofile);
        TextCorpusStreamed tc = new TextCorpusStreamed(is, layersToReadBeforeSplitting, os, false);
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
        TestUtils.assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    private int[] split(String tokenString) {
        return token2split.get(tokenString);
    }
}
