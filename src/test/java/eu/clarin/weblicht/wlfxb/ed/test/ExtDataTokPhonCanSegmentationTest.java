/**
 *
 */
package eu.clarin.weblicht.wlfxb.ed.test;

import eu.clarin.weblicht.wlfxb.ed.api.*;
import eu.clarin.weblicht.wlfxb.ed.xb.ExternalDataLayerTag;
import eu.clarin.weblicht.wlfxb.io.ExternalDataWithTextCorpusStreamed;
import eu.clarin.weblicht.wlfxb.tc.api.TextCorpus;
import eu.clarin.weblicht.wlfxb.tc.api.TokensLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import java.util.EnumSet;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class ExtDataTokPhonCanSegmentationTest extends AbstractExternalDataTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/ed-seg/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/ed-seg/tcf-after.xml";
    private static final String OUTPUT_FILE = "/tmp/output.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/ed-seg/output-expected.xml";
    private static final EnumSet<ExternalDataLayerTag> edLayersToReadBeforeSeg =
            EnumSet.of(ExternalDataLayerTag.SPEECH_SIGNAL);
    private static final EnumSet<TextCorpusLayerTag> tcLayersToReadBeforeSeg =
            EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<ExternalDataLayerTag> edLayersToReadAfterSeg =
            EnumSet.of(
            ExternalDataLayerTag.SPEECH_SIGNAL,
            ExternalDataLayerTag.TOKEN_SEGMENTATION,
            ExternalDataLayerTag.PHONETIC_SEGMENTATION,
            ExternalDataLayerTag.CANONICAL_SEGMENTATION);
    private static final EnumSet<TextCorpusLayerTag> tcLayersToReadAfterSeg =
            EnumSet.of(TextCorpusLayerTag.TOKENS);

    @Test
    public void testRead() throws Exception {
        ExternalDataWithTextCorpusStreamed edtc = read(INPUT_FILE_WITH_LAYER, edLayersToReadAfterSeg, tcLayersToReadAfterSeg);
        TextCorpus tc = edtc.getTextCorpus();
        TokensLayer tLayer = tc.getTokensLayer();
        assertEquals("ich", tLayer.getToken(1).getString());

        ExternalData ed = edtc.getExternalData();
        SpeechSignalLayer sLayer = ed.getSpeechSignalLayer();
        assertEquals("audio/wav", sLayer.getDataMimeType());
        TokenSegmentationLayer tsLayer = ed.getTokenSegmentationLayer();
        assertEquals("text/xml+an", tsLayer.getDataMimeType());
        PhoneticSegmentationLayer psLayer = ed.getPhoneticSegmentationLayer();
        assertEquals("text/xml+an", psLayer.getDataMimeType());
        CanonicalSegmentationLayer csLayer = ed.getCanonicalSegmentationLayer();
        assertEquals("text/xml+an", csLayer.getDataMimeType());

    }

    @Test
    public void testReadWrite() throws Exception {
        ExternalDataWithTextCorpusStreamed edtc = open(INPUT_FILE_WITHOUT_LAYER, OUTPUT_FILE, edLayersToReadBeforeSeg, tcLayersToReadBeforeSeg);
        System.out.println(edtc);

        TextCorpus tc = edtc.getTextCorpus();
        TokensLayer tokens = tc.getTokensLayer();

        ExternalData ed = edtc.getExternalData();
        SpeechSignalLayer speechsig = ed.getSpeechSignalLayer();
        String speechSignalUrl = speechsig.getLink();
        String speechSignalName = extractName(speechSignalUrl);
        // assuming you process tokens and speech signal here in order 
        // to produce token, phonetic and canonical segmentation in AN format
        String outputAN = process(tokens, speechSignalUrl);
        // assuming you put the output somewhere externally on a server
        String outputANUrl = upload(outputAN, speechSignalName);

        TokenSegmentationLayer tseg = ed.createTokenSegmentationLayer("text/xml+an");
        tseg.addLink(outputANUrl);
        PhoneticSegmentationLayer pseg = ed.createPhoneticSegmentationLayer("text/xml+an");
        pseg.addLink(outputANUrl);
        CanonicalSegmentationLayer cseg = ed.createCanonicalSegmentationLayer("text/xml+an");
        cseg.addLink(outputANUrl);

        // IMPORTANT close the streams!!!
        edtc.close();
        System.out.println(edtc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    private String extractName(String speechSignalUrl) {
        int start = speechSignalUrl.lastIndexOf('/');
        int end = speechSignalUrl.indexOf('.', start);
        return speechSignalUrl.substring(start + 1, end);
    }

    private String process(TokensLayer tokens, String speechSignalUrl) {
        // faked processing
        return "here should be some output in, e.g., AN format with annotations "
                + "for token, phonetic and canonical segmentation";
    }

    private String upload(String outputAN, String speechSignalName) {
        // faked upload of outputAN to drop-off service
        return "http://ws1-clarind.esc.rzg.mpg.de/drop-off/storage/" + speechSignalName + ".an";
    }
}
