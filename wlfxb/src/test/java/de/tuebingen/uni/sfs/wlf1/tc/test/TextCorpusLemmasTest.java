/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.test;

import de.tuebingen.uni.sfs.wlf1.io.TextCorpusStreamed;
import de.tuebingen.uni.sfs.wlf1.tc.api.LemmasLayer;
import de.tuebingen.uni.sfs.wlf1.tc.api.TextCorpus;
import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
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
public class TextCorpusLemmasTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "data/tc-lemmas/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "data/tc-lemmas/tcf-after.xml";
    private static final String OUTPUT_FILE = "data/tc-lemmas/output.xml";
    private static final String EXPECTED_OUTPUT_FILE = "data/tc-lemmas/output-expected.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeLemmatization =
            EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterLemmatization =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.LEMMAS);
    public static Map<String, String> token2Lemma = new HashMap<String, String>();

    static {
        token2Lemma.put("Peter", "Peter");
        token2Lemma.put("aß", "essen");
        token2Lemma.put("eine", "ein");
        token2Lemma.put("Käsepizza", "Käsepizza");
        token2Lemma.put(".", ".");
        token2Lemma.put("Sie", "sie");
        token2Lemma.put("schmeckte", "schmecken");
        token2Lemma.put("ihm", "er");
    }

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterLemmatization);
        LemmasLayer layer = tc.getLemmasLayer();
        assertEquals(9, layer.size());
        assertEquals("Peter", layer.getLemma(0).getString());
        assertEquals(tc.getTokensLayer().getToken(0), layer.getTokens(layer.getLemma(0))[0]);
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
        TextCorpusStreamed tc = new TextCorpusStreamed(is, layersToReadBeforeLemmatization, os, false);
        System.out.println(tc);
        // create lemmas layer, empty at first
        LemmasLayer lemmas = tc.createLemmasLayer();
        for (int i = 0; i < tc.getTokensLayer().size(); i++) {
            Token token = tc.getTokensLayer().getToken(i);
            String lemmaString = lemmatize(token.getString());
            // create and add lemma to the lemmas layer
            lemmas.addLemma(lemmaString, token);
        }
        // IMPORTANT close the streams!!!
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        TestUtils.assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    private String lemmatize(String tokenString) {
        return token2Lemma.get(tokenString);
    }
}
