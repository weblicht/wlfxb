/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.test;

import de.tuebingen.uni.sfs.wlf1.io.TextCorpusStreamed;
import de.tuebingen.uni.sfs.wlf1.tc.api.*;
import de.tuebingen.uni.sfs.wlf1.tc.xb.TextCorpusLayerTag;
import de.tuebingen.uni.sfs.wlf1.test.utils.TestUtils;
import java.io.*;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class TextCorpusCoreferencesTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "data/tc-corefs/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "data/tc-corefs/tcf-after.xml";
    private static final String OUTPUT_FILE = "data/tc-corefs/output.xml";
    private static final String EXPECTED_OUTPUT_FILE = "data/tc-corefs/output-expected.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeCoreferenceAnnotation =
            EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterCoreferenceAnnotation =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.COREFERENCES);

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterCoreferenceAnnotation);
        CoreferencesLayer layer = tc.getCoreferencesLayer();
        assertEquals(2, layer.size());
        assertEquals("BART", layer.getTagset());
        assertEquals(false, layer.hasExternalReferences());
        assertEquals("ORG", layer.getReferent(0).getType());
        assertEquals(tc.getTokensLayer().getToken(0), layer.getTokens(layer.getReferent(0).getCoreferences()[0])[0]);
        assertEquals(tc.getTokensLayer().getToken(0), layer.getMinimumTokens(layer.getReferent(0).getCoreferences()[0])[0]);
        assertEquals(tc.getTokensLayer().getToken(17), layer.getTokens(layer.getReferent(0).getCoreferences()[1])[0]);
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
        TextCorpusStreamed tc = new TextCorpusStreamed(is, layersToReadBeforeCoreferenceAnnotation, os, false);
        System.out.println(tc);
        // get tokens layer
        TokensLayer tokensLayer = tc.getTokensLayer();
        // create coreferences layer, it's empty at first
        CoreferencesLayer corefsLayer = tc.createCoreferencesLayer("BART");
        // add coreferences
        addCoreferenceAnnotations(tokensLayer, corefsLayer);
        // IMPORTANT close the streams!!!
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        TestUtils.assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    private void addCoreferenceAnnotations(TokensLayer tokensLayer,
            CoreferencesLayer corefsLayer) {

//		  <coreferences tagset="BART">
//		    <referent type="ORG" ID="rft_0">
//		      <coreference tokenIDs="t1" mintokIDs="t1" type="pro.per3"></coreference>
//		      <coreference tokenIDs="t18 t19 t20 t21 t22" mintokIDs="t20 t21" type="nam"></coreference>
//		    </referent>
//		    <referent type="CURRENCY" ID="rft_1">
//		      <coreference tokenIDs="t4 t5" mintokIDs="t5" type="nom.indef"></coreference>
//		    </referent>
//	      </coreferences>

        Coreference coref1_1 = corefsLayer.createCoreference("pro.per3",
                Arrays.asList(new Token[]{tokensLayer.getToken(0)}),
                Arrays.asList(new Token[]{tokensLayer.getToken(0)}));
        Coreference coref1_2 = corefsLayer.createCoreference("nam",
                Arrays.asList(new Token[]{tokensLayer.getToken(17), tokensLayer.getToken(18),
                    tokensLayer.getToken(19), tokensLayer.getToken(20), tokensLayer.getToken(21)}),
                Arrays.asList(new Token[]{tokensLayer.getToken(19), tokensLayer.getToken(20)}));
        List<Coreference> corefs1 = Arrays.asList(new Coreference[]{coref1_1, coref1_2});
        corefsLayer.addReferent("ORG", corefs1);

        Coreference coref2_1 = corefsLayer.createCoreference("nom.indef",
                Arrays.asList(new Token[]{tokensLayer.getToken(3), tokensLayer.getToken(4)}),
                Arrays.asList(new Token[]{tokensLayer.getToken(4)}));
        List<Coreference> corefs2 = Arrays.asList(new Coreference[]{coref2_1});
        corefsLayer.addReferent("CURRENCY", corefs2);

    }
}
