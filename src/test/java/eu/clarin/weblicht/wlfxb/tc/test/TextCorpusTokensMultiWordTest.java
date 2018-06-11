/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.test;

import eu.clarin.weblicht.wlfxb.io.TextCorpusStreamed;
import eu.clarin.weblicht.wlfxb.tc.api.TextCorpus;
import eu.clarin.weblicht.wlfxb.tc.api.Token;
import eu.clarin.weblicht.wlfxb.tc.api.TokensLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import eu.clarin.weblicht.wlfxb.test.utils.TestUtilCompositeTokenizer;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import java.io.File;

/**
 * @author Yana Panchenko and Mohammad Fazleh Elahi
 *
 */
public class TextCorpusTokensMultiWordTest extends AbstractTextCorpusTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    private static final String INPUT_FILE_SUTFACE_PARTS_WITHOUT_LAYER = "/data/tc-tokens/tcf-beforeSurfaceParts.xml";
    private static final String INPUT_FILE_SUTFACE_PARTS_WITH_LAYER = "/data/tc-tokens/tcf-afterSurfaceParts.xml";
    private static final String EXPECTED_SUTFACE_PARTS_OUTPUT_FILE = "/data/tc-tokens/output-expectedSurfaceParts.xml";
    
    private static final String INPUT_FILE_SURFACE_WITHOUT_LAYER = "/data/tc-tokens/tcf-beforeSurface.xml";
    private static final String INPUT_FILE_SURFACE_WITH_LAYER = "/data/tc-tokens/tcf-afterSurface.xml";
    private static final String EXPECTED_SL_OUTPUT_FILE = "/data/tc-tokens/output-expectedSurface.xml";
    
    private static final String OUTPUT_FILE = "output.xml";

    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeTokenization
            = EnumSet.of(TextCorpusLayerTag.TEXT);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterTokenization
            = EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.TEXT);

    @Test
    public void testRead_SurfaceForm_Parts() throws Exception {
        TextCorpus tc = read(INPUT_FILE_SUTFACE_PARTS_WITH_LAYER, layersToReadAfterTokenization);
        TokensLayer layer = tc.getTokensLayer();
        Assert.assertEquals(9, layer.size());
        Assert.assertEquals("in", layer.getToken(5).getString());
        Assert.assertEquals("im", layer.getToken(5).getSurfaceForm());
        Assert.assertEquals("t_5", layer.getToken(5).getParts()[0]);
        Assert.assertEquals("t_6", layer.getToken(5).getParts()[1]);
    }

    @Test
    public void testRead_SurfaceForm() throws Exception {
        TextCorpus tc = read(INPUT_FILE_SURFACE_WITH_LAYER, layersToReadAfterTokenization);
        TokensLayer layer = tc.getTokensLayer();
        Assert.assertEquals(3, layer.size());
        Assert.assertEquals("ponoči", layer.getToken(1).getString());
        Assert.assertEquals("po noči", layer.getToken(1).getSurfaceForm());
    }

    @Test
    public void testReadWrite_SurfaceForm_Parts() throws Exception {
        String outfile = testFolder.getRoot() + File.separator + OUTPUT_FILE;
        TextCorpusStreamed tc = open(INPUT_FILE_SUTFACE_PARTS_WITHOUT_LAYER, outfile, layersToReadBeforeTokenization);
        System.out.println(tc);

        List<String> tokenstrings = tokenize(tc.getTextLayer().getText());
        // create tokens layer, it is empty first
        TokensLayer tokens = tc.createTokensLayer();
        Token lastToken = null;
        for (String tokenString : tokenstrings) {
            // create and add Token objects to the tokens layer
            if (TestUtilCompositeTokenizer.isCompositeToken(tokenString)) {
                List<String> tokenPartStrings = TestUtilCompositeTokenizer.getParts();
                String[] nexttokenIDs = getNextIDs(lastToken, new Integer(tokenPartStrings.size()));
                for (int i = 0; i < nexttokenIDs.length; i++) {
                    if (i == 0) {
                        tokens.addToken(tokenPartStrings.get(i), nexttokenIDs[i], null, null, tokenString, nexttokenIDs);
                    } else {
                        tokens.addToken(tokenPartStrings.get(i), nexttokenIDs[i]);
                    }
                }

            } else {
                lastToken = tokens.addToken(tokenString);
            }
        }
        // IMPORTANT close the streams!!!
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_SUTFACE_PARTS_OUTPUT_FILE, outfile);
    }

    @Test
    public void testReadWrite_Surface() throws Exception {
        String outfile = testFolder.getRoot() + File.separator + OUTPUT_FILE;
        TextCorpusStreamed tc = open(INPUT_FILE_SURFACE_WITHOUT_LAYER, outfile, layersToReadBeforeTokenization);

        List<String> tokenstrings = tokenizeSurface(tc.getTextLayer().getText());
        // create tokens layer, it is empty first
        TokensLayer tokens = tc.createTokensLayer();
        Token lastToken = null;
        for (String tokenString : tokenstrings) {
            // create and add Token objects to the tokens layer
            if (TestUtilCompositeTokenizer.isCompositeToken(tokenString)) {
                List<String> tokenPartStrings = TestUtilCompositeTokenizer.getParts();
                String[] nexttokenIDs = getNextIDs(lastToken, new Integer(tokenPartStrings.size()));
                for (int i = 0; i < nexttokenIDs.length; i++) {
                    if (i == 0) {
                        tokens.addToken(tokenPartStrings.get(i), nexttokenIDs[i], null, null, tokenString, nexttokenIDs);
                    } else {
                        tokens.addToken(tokenPartStrings.get(i), nexttokenIDs[i]);
                    }
                }

            } else if (TestUtilCompositeTokenizer.isNoncompoundTokens(tokenString)) {
                tokens.addToken(TestUtilCompositeTokenizer.getCompositeForm(), TestUtilCompositeTokenizer.getSurFaceForm(), null, null);
            } else if (TestUtilCompositeTokenizer.isNoncompoundTokenParts(tokenString)) {
                ;
            } else {
                lastToken = tokens.addToken(tokenString);
            }
        }
        // IMPORTANT close the streams!!!
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_SL_OUTPUT_FILE, outfile);
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

    private List<String> tokenizeSurface(String text) {
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

    private String[] getNextIDs(Token lastToken, Integer length) throws Exception {
        String tokenID_Prefix = "t_";
        Integer start_id = 0;
        String[] IDs = new String[length];
        if (lastToken != null) {
            if (lastToken.getID().contains("_")) {
                String[] split = lastToken.getID().split("_");
                start_id = Integer.parseInt(split[1]) + 1;
            } else {
                throw new Exception("The tokenID string format is wrong!!");
            }
        }

        for (Integer id = 0; id < length; id++) {
            IDs[id] = tokenID_Prefix + (start_id).toString();
            start_id++;
        }
        return IDs;
    }
}
