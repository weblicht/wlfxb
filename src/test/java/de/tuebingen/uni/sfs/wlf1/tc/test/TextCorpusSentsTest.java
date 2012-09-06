/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.test;

import de.tuebingen.uni.sfs.wlf1.io.TextCorpusStreamed;
import de.tuebingen.uni.sfs.wlf1.tc.api.SentencesLayer;
import de.tuebingen.uni.sfs.wlf1.tc.api.TextCorpus;
import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.tc.api.TokensLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.TextCorpusLayerTag;
import de.tuebingen.uni.sfs.wlf1.test.utils.TestUtils;
import java.io.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class TextCorpusSentsTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "data/tc-sents/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "data/tc-sents/tcf-after.xml";
    private static final String OUTPUT_FILE = "data/tc-sents/output.xml";
    private static final String EXPECTED_OUTPUT_FILE = "data/tc-sents/output-expected.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeSentDetect =
            EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterSentDetect =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.SENTENCES);

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterSentDetect);
        SentencesLayer layer = tc.getSentencesLayer();
        assertEquals(2, layer.size());
        assertEquals(tc.getTokensLayer().getToken(0), layer.getTokens(layer.getSentence(0))[0]);
        assertEquals(tc.getTokensLayer().getToken(4), layer.getTokens(layer.getSentence(0))[4]);
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
        TextCorpusStreamed tc = new TextCorpusStreamed(is, layersToReadBeforeSentDetect, os, false);
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
        TestUtils.assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
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
