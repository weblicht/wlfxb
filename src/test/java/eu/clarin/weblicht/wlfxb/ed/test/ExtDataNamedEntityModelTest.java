/**
 *
 */
package eu.clarin.weblicht.wlfxb.ed.test;

import eu.clarin.weblicht.wlfxb.ed.api.ExternalData;
import eu.clarin.weblicht.wlfxb.ed.api.NamedEntityModelLayer;
import eu.clarin.weblicht.wlfxb.ed.api.SpeechSignalLayer;
import eu.clarin.weblicht.wlfxb.ed.xb.ExternalDataLayerTag;
import eu.clarin.weblicht.wlfxb.io.ExternalDataWithTextCorpusStreamed;
import eu.clarin.weblicht.wlfxb.tc.api.TextCorpus;
import eu.clarin.weblicht.wlfxb.tc.api.TokensLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class ExtDataNamedEntityModelTest extends AbstractExternalDataTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/ed-nemodel/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/ed-nemodel/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/ed-nemodel/output-expected.xml";
    private static final String OUTPUT_FILE = "/tmp/output.xml";
    private static final EnumSet<ExternalDataLayerTag> edLayersToReadBefore =
            EnumSet.noneOf(ExternalDataLayerTag.class);
//    private static final EnumSet<TextCorpusLayerTag> tcLayersToReadBefore =
//            EnumSet.of(TextCorpusLayerTag.TEXT);
    private static final EnumSet<TextCorpusLayerTag> tcLayersToReadBefore =
            EnumSet.noneOf(TextCorpusLayerTag.class);
    
    private static final EnumSet<ExternalDataLayerTag> edLayersToReadAfter =
            EnumSet.of(ExternalDataLayerTag.NAMEDENTITY_MODEL);
    private static final EnumSet<TextCorpusLayerTag> tcLayersToReadAfter =
            //EnumSet.of(TextCorpusLayerTag.TOKENS);
            EnumSet.noneOf(TextCorpusLayerTag.class);

    @Test
    public void testRead() throws Exception {
        ExternalDataWithTextCorpusStreamed edtc = read(INPUT_FILE_WITH_LAYER, edLayersToReadAfter, tcLayersToReadAfter);
        TextCorpus tc = edtc.getTextCorpus();
        TokensLayer tLayer = tc.getTokensLayer();
        ExternalData ed = edtc.getExternalData();
        NamedEntityModelLayer mLayer = ed.getNamedEntityModelLayer();
        assertEquals("application/octet-stream", mLayer.getDataMimeType());
        assertEquals("conll2003", mLayer.getNamedEntitiesType());
        assertEquals("opennlp-1.5", mLayer.getModelType());
        assertEquals("http://arc:8080/drop-off/storage/test.zip", mLayer.getLink());
        assertEquals(null, tLayer);
    }

    @Test
    public void testReadWrite() throws Exception {
        ExternalDataWithTextCorpusStreamed edtc = open(INPUT_FILE_WITHOUT_LAYER, OUTPUT_FILE, edLayersToReadBefore, tcLayersToReadBefore);
        System.out.println(edtc);

        ExternalData ed = edtc.getExternalData();
        NamedEntityModelLayer mLayer = ed.createNamedEntityModelLayer("application/octet-stream", "conll2003","opennlp-1.5");
        mLayer.addLink("http://arc:8080/drop-off/storage/test.zip");

        // IMPORTANT close the streams!!!
        edtc.close();
        System.out.println(edtc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }
}
