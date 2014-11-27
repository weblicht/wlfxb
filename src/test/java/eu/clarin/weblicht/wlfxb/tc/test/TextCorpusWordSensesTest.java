/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.test;

import eu.clarin.weblicht.wlfxb.io.TextCorpusStreamed;
import eu.clarin.weblicht.wlfxb.tc.api.TextCorpus;
import eu.clarin.weblicht.wlfxb.tc.api.Token;
import eu.clarin.weblicht.wlfxb.tc.api.WordSensesLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import java.io.File;

/**
 * @author Yana Panchenko
 *
 */
public class TextCorpusWordSensesTest extends AbstractTextCorpusTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/tc-ws/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/tc-ws/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/tc-ws/output-expected.xml";
    private static final String OUTPUT_FILE = "output.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBefore =
            EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfter =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.WORD_SENSES);
    public static final Map<String, String> token2lexunit = new HashMap<String, String>();
    public static final Map<String, String> token2comment = new HashMap<String, String>();

    // semantically this example doesn't make sense, but is given just for the sake of testing
    static {
        token2lexunit.put("aß", "75069 75197");
        token2lexunit.put("Käsepizza", "-1");
        token2lexunit.put("schmeckte", "82896");
    }
    static {
        token2comment.put("aß", "übertragen");
        token2comment.put("Käsepizza", "unbestimmbar");
    }

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfter);
        WordSensesLayer layer = tc.getWordSensesLayer();
        Assert.assertEquals(3, layer.size());
        Assert.assertEquals(tc.getTokensLayer().getToken(1), layer.getTokens(layer.getWordSense(0))[0]);
        Assert.assertEquals(tc.getTokensLayer().getToken(3), layer.getTokens(layer.getWordSense(1))[0]);
        Assert.assertEquals(tc.getTokensLayer().getToken(6), layer.getTokens(layer.getWordSense(2))[0]);
    }

    @Test
    public void testReadWrite() throws Exception {
        String outfile = testFolder.getRoot() + File.separator + OUTPUT_FILE;
        TextCorpusStreamed tc = open(INPUT_FILE_WITHOUT_LAYER, outfile, layersToReadBefore);
        System.out.println(tc);
        WordSensesLayer layer = tc.createWordSensesLayer("GermaNet8.0");
        for (int i = 0; i < tc.getTokensLayer().size(); i++) {
            Token token = tc.getTokensLayer().getToken(i);
            String[] lexunits = getLexunits(token.getString());
            String comment = getComment(token.getString());
            if (lexunits != null) {
                layer.addWordSense(Arrays.asList(new Token[]{token}), comment, lexunits);
            }
        }
        // IMPORTANT! close the streams!!!
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, outfile);
    }

    // semantically this example doesn't make sense, but is given just for the sake of testing
    private String getComment(String tokenString) {
        return token2comment.get(tokenString);
    }
    
        // semantically this example doesn't make sense, but is given just for the sake of testing
    private String[] getLexunits(String tokenString) {
        String lexunits = token2lexunit.get(tokenString);
        if (lexunits != null) {
            return lexunits.split(" ");
        }
        return null;
    }
}
