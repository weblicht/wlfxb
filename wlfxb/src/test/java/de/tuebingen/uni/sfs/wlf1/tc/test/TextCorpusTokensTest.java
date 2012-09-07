/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.test;

import de.tuebingen.uni.sfs.wlf1.io.TextCorpusStreamed;
import de.tuebingen.uni.sfs.wlf1.tc.api.TextCorpus;
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
public class TextCorpusTokensTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "data/tc-tokens/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "data/tc-tokens/tcf-after.xml";
    private static final String OUTPUT_FILE = "data/tc-tokens/output.xml";
    private static final String EXPECTED_OUTPUT_FILE = "data/tc-tokens/output-expected.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeTokenization =
            EnumSet.of(TextCorpusLayerTag.TEXT);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterTokenization =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.TEXT);

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterTokenization);
        TokensLayer layer = tc.getTokensLayer();
        assertEquals(9, layer.size());
        assertEquals("Peter", layer.getToken(0).getString());
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
        TextCorpusStreamed tc = new TextCorpusStreamed(is, layersToReadBeforeTokenization, os, false);
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
        TestUtils.assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
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
