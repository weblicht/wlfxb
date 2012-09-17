/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.test;

import de.tuebingen.uni.sfs.wlf1.io.TextCorpusStreamed;
import de.tuebingen.uni.sfs.wlf1.tc.api.LemmasLayer;
import de.tuebingen.uni.sfs.wlf1.tc.api.TextCorpus;
import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
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
public class TextCorpusLemmasTest extends AbstractTextCorpusTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/tc-lemmas/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/tc-lemmas/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/tc-lemmas/output-expected.xml";
    private static final String OUTPUT_FILE = "/tmp/output.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeLemmatization =
            EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterLemmatization =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.LEMMAS);
    public static final Map<String, String> token2Lemma = new HashMap<String, String>();

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
        Assert.assertEquals(9, layer.size());
        Assert.assertEquals("Peter", layer.getLemma(0).getString());
        Assert.assertEquals(tc.getTokensLayer().getToken(0), layer.getTokens(layer.getLemma(0))[0]);
    }

    @Test
    public void testReadWrite() throws Exception {
        TextCorpusStreamed tc = open(INPUT_FILE_WITHOUT_LAYER, OUTPUT_FILE, layersToReadBeforeLemmatization);
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
        assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    private String lemmatize(String tokenString) {
        return token2Lemma.get(tokenString);
    }
}
