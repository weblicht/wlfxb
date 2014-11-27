/**
 *
 */
package eu.clarin.weblicht.wlfxb.ed.test;

import eu.clarin.weblicht.wlfxb.ed.api.ExternalData;
import eu.clarin.weblicht.wlfxb.ed.api.SpeechSignalLayer;
import eu.clarin.weblicht.wlfxb.ed.xb.ExternalDataLayerTag;
import eu.clarin.weblicht.wlfxb.io.ExternalDataWithTextCorpusStreamed;
import eu.clarin.weblicht.wlfxb.tc.api.TextCorpus;
import eu.clarin.weblicht.wlfxb.tc.api.TokensLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.io.File;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 * @author Yana Panchenko
 *
 */
public class ExtDataSpeechSignalTest extends AbstractExternalDataTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/ed-speechsig/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/ed-speechsig/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/ed-speechsig/output-expected.xml";
    private static final String OUTPUT_FILE = "output.xml";
    private static final EnumSet<ExternalDataLayerTag> edLayersToReadBeforeSpeechSig =
            EnumSet.noneOf(ExternalDataLayerTag.class);
    private static final EnumSet<TextCorpusLayerTag> tcLayersToReadBeforeSpeechSig =
            EnumSet.of(TextCorpusLayerTag.TEXT);
    private static final EnumSet<ExternalDataLayerTag> edLayersToReadAfterSpeechSig =
            EnumSet.of(ExternalDataLayerTag.SPEECH_SIGNAL);
    private static final EnumSet<TextCorpusLayerTag> tcLayersToReadAfterSpeechSig =
            EnumSet.of(TextCorpusLayerTag.TOKENS);

    @Test
    public void testRead() throws Exception {
        ExternalDataWithTextCorpusStreamed edtc = read(INPUT_FILE_WITH_LAYER, edLayersToReadAfterSpeechSig, tcLayersToReadAfterSpeechSig);
        TextCorpus tc = edtc.getTextCorpus();
        TokensLayer tLayer = tc.getTokensLayer();
        ExternalData ed = edtc.getExternalData();
        SpeechSignalLayer sLayer = ed.getSpeechSignalLayer();
        assertEquals("ich", tLayer.getToken(1).getString());
        assertEquals("audio/wav", sLayer.getDataMimeType());

    }

    @Test
    public void testReadWrite() throws Exception {
        String outfile = testFolder.getRoot() + File.separator + OUTPUT_FILE; 
        ExternalDataWithTextCorpusStreamed edtc = open(INPUT_FILE_WITHOUT_LAYER, outfile, edLayersToReadBeforeSpeechSig, tcLayersToReadBeforeSpeechSig);
        System.out.println(edtc);

        TextCorpus tc = edtc.getTextCorpus();
        List<String> tokenstrings = tokenize(tc.getTextLayer().getText());
        // create tokens layer, it is empty first
        TokensLayer tokens = tc.createTokensLayer();
        for (String tokenString : tokenstrings) {
            // create and add Token objects to the tokens layer
            tokens.addToken(tokenString);
        }
        ExternalData ed = edtc.getExternalData();
        SpeechSignalLayer speechsig = ed.createSpeechSignalLayer("audio/wav", 1);
        speechsig.addLink("http://arc:8080/drop-off/storage/g046acn1_037_AFI.wav");

        // IMPORTANT close the streams!!!
        edtc.close();
        System.out.println(edtc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, outfile);
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
            } else if (text.charAt(i) == '.' || text.charAt(i) == ',') {
                tokenstrings.add(tokenBuilder.toString());
                tokenstrings.add(text.charAt(i) + "");
                tokenBuilder = new StringBuilder();
            } else {
                tokenBuilder.append(text.charAt(i));
            }
        }
        if (tokenBuilder.length() > 0) {
            tokenstrings.add(tokenBuilder.toString());
        }
        return tokenstrings;
    }
}
