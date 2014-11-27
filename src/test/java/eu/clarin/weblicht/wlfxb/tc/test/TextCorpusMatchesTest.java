/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.test;

import eu.clarin.weblicht.wlfxb.io.WLDObjector;
import eu.clarin.weblicht.wlfxb.md.xb.MetaData;
import eu.clarin.weblicht.wlfxb.tc.api.*;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusStored;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import java.io.File;

/**
 * @author Yana Panchenko
 *
 */
public class TextCorpusMatchesTest extends AbstractTextCorpusTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT_FILE_WITH_LAYER = "/data/tc-matches/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/tc-matches/output-expected.xml";
    private static final String OUTPUT_FILE = "output.xml";

    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterQuery =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.CORPUS_MATCHES);

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterQuery);
        MatchesLayer layer = tc.getMatchesLayer();
        Assert.assertEquals(1, layer.size());
        Token token = layer.getTokens(layer.getCorpus(0).getMatchedItems()[0])[0];
        Assert.assertEquals(tc.getTokensLayer().getToken(0), token);
    }

    @Test
    public void testWrite() throws Exception {
        String outfile = testFolder.getRoot() + File.separator + OUTPUT_FILE;
        OutputStream os = new FileOutputStream(outfile);
        TextCorpusStored tc = new TextCorpusStored("de");

        queryCorporaAndAddMatchesToTextCorpus(tc);

        WLDObjector.write(new MetaData(), tc, os, false);
        os.close();

        System.out.println(tc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, outfile);
    }

    private void queryCorporaAndAddMatchesToTextCorpus(TextCorpusStored tc) {

        Map<String, String> token2StrId = new LinkedHashMap<String, String>();


        token2StrId.put("Peter", "5-1023");
        token2StrId.put("aß", "5-1024");
        token2StrId.put("eine", "5-1025");
        token2StrId.put("Käsepizza", "5-1026");
        token2StrId.put(".", "5-1027");
        token2StrId.put("Sie", "16-116");
        token2StrId.put("schmeckte", "16-117");
        token2StrId.put("ihm", "16-118");


        TokensLayer tokensLayer = tc.createTokensLayer();
        MatchesLayer matchesLayer = tc.createMatchesLayer("sqp", "tb_lemma=\"Peter\" | tb_lemma=\"schmecken\"");
        MatchedCorpus corpus = matchesLayer.addCorpus("wcorp-1", "some-pid");
        for (String t : token2StrId.keySet()) {
            Token token = tokensLayer.addToken(t);
            if (t.equals("Peter") || t.equals("schmeckte")) {
                Map<String, String> targets = new HashMap<String, String>();
                targets.put("tname", token.getID());
                Map<String, String> cats = new HashMap<String, String>();
                cats.put("cname", "cval");
                List<Token> refToks = Arrays.asList(new Token[]{token});
                List<String> refOrigToks = Arrays.asList(new String[]{token2StrId.get(t)});
                matchesLayer.addItem(corpus,
                        refToks,
                        refOrigToks,
                        targets, cats);
            }
        }
    }
}
