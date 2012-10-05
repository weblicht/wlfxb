/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.test;

import eu.clarin.weblicht.wlfxb.io.TextCorpusStreamed;
import eu.clarin.weblicht.wlfxb.tc.api.TextCorpus;
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
public class TextCorpusTokensTest extends AbstractTextCorpusTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/tc-tokens/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/tc-tokens/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/tc-tokens/output-expected.xml";
    private static final String OUTPUT_FILE = "/tmp/output.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeTokenization =
            EnumSet.of(TextCorpusLayerTag.TEXT);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterTokenization =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.TEXT);

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterTokenization);
        TokensLayer layer = tc.getTokensLayer();
        Assert.assertEquals(9, layer.size());
        Assert.assertEquals("Peter", layer.getToken(0).getString());
    }

    @Test
    public void testReadWrite() throws Exception {
        TextCorpusStreamed tc = open(INPUT_FILE_WITHOUT_LAYER, OUTPUT_FILE, layersToReadBeforeTokenization);
        System.out.println(tc);
        List<String> tokenstrings = tokenize(tc.getTextLayer().getText());
        // create tokens layer, it is empty first
        TokensLayer tokens = tc.createTokensLayer();
        for (String tokenString : tokenstrings) {
            // create and add Token objects to the tokens layer
            tokens.addToken(tokenString);
        }
        // IMPORTANT close the streams!!!
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    private List<String> tokenize(String text) {
        List<String> tokenstrings = new ArrayList<String>();
        StringBuilder tokenBuilder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                if (tokenBuilder.length() > 0) {
                    tokenstrings.add(tokenBuilder.toString());
                    tokenBuilder = new StringBuilder();
                }
            } else if (text.charAt(i) == '.') {
                tokenstrings.add(tokenBuilder.toString());
                tokenstrings.add(text.charAt(i) + "");
                tokenBuilder = new StringBuilder();
            } else {
                tokenBuilder.append(text.charAt(i));
            }
        }
        return tokenstrings;
    }
}
