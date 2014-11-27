/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.test;

import eu.clarin.weblicht.wlfxb.io.TextCorpusStreamed;
import eu.clarin.weblicht.wlfxb.tc.api.*;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import java.io.File;

/**
 * @author Yana Panchenko
 *
 */
public class TextCorpusReferencesTest extends AbstractTextCorpusTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/tc-refs/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/tc-refs/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/tc-refs/output-expected.xml";
    private static final String OUTPUT_FILE = "output.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeReferenceAnnotation =
            EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterReferenceAnnotation =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.REFERENCES);

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterReferenceAnnotation);
        ReferencesLayer layer = tc.getReferencesLayer();
        Assert.assertEquals(1, layer.size());
        Assert.assertEquals("BART", layer.getTypetagset());
        Assert.assertEquals(false, layer.hasExternalReferences());
        Assert.assertEquals(tc.getTokensLayer().getToken(0), layer.getTokens(layer.getReferencedEntity(0).getReferences()[0])[0]);
        Assert.assertEquals(tc.getTokensLayer().getToken(0), layer.getMinimumTokens(layer.getReferencedEntity(0).getReferences()[0])[0]);
        Assert.assertEquals(tc.getTokensLayer().getToken(17), layer.getTokens(layer.getReferencedEntity(0).getReferences()[1])[0]);
        Assert.assertEquals("cataphoric", layer.getReferencedEntity(0).getReferences()[0].getRelation());
        Assert.assertEquals(layer.getReferencedEntity(0).getReferences()[1], layer.getTarget(layer.getReferencedEntity(0).getReferences()[0])[0]);
    }

    @Test
    public void testReadWrite() throws Exception {
        String outfile = testFolder.getRoot() + File.separator + OUTPUT_FILE;
        TextCorpusStreamed tc = open(INPUT_FILE_WITHOUT_LAYER, outfile, layersToReadBeforeReferenceAnnotation);
        System.out.println(tc);
        // get tokens layer
        TokensLayer tokensLayer = tc.getTokensLayer();
        // create coreferences layer, it's empty at first
        ReferencesLayer refsLayer = tc.createReferencesLayer("BART", "TuebaDZ", null);
        // add coreferences
        addReferenceAnnotations(tokensLayer, refsLayer);
        // IMPORTANT close the streams!!!
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, outfile);
    }

    private void addReferenceAnnotations(TokensLayer tokensLayer,
            ReferencesLayer refsLayer) {

//		  <references typetagset="BART" reltagset="TuebaDZ">
//		    <entity ID="rft_0">
//		      <reference ID="rc_0" rel="cataphoric" target="rc_1" tokenIDs="t1" mintokIDs="t1" type="pro.per3"/>
//            <reference ID="rc_1" tokenIDs="t18 t19 t20 t21 t22" mintokIDs="t20 t21" type="nam"/>
//		    </entity>
//	      </references>

        Reference ref1_1 = refsLayer.createReference("pro.per3",
                Arrays.asList(new Token[]{tokensLayer.getToken(0)}),
                Arrays.asList(new Token[]{tokensLayer.getToken(0)}));
        Reference ref1_2 = refsLayer.createReference("nam",
                Arrays.asList(new Token[]{tokensLayer.getToken(17), tokensLayer.getToken(18),
                    tokensLayer.getToken(19), tokensLayer.getToken(20), tokensLayer.getToken(21)}),
                Arrays.asList(new Token[]{tokensLayer.getToken(19), tokensLayer.getToken(20)}));


//		Reference ref1_3 = refsLayer.createReference("blah", 
//				Arrays.asList(new Token[]{tokensLayer.getToken(5)}),
//				Arrays.asList(new Token[]{tokensLayer.getToken(5)}));
//		refsLayer.addRelation(ref1_3, "expletive", new Reference[0]);

        refsLayer.addRelation(ref1_1, "cataphoric", new Reference[]{ref1_2});
        List<Reference> refs1 = Arrays.asList(new Reference[]{ref1_1, ref1_2});
        //List<Reference> refs1 = Arrays.asList(new Reference[]{ref1_1, ref1_2, ref1_3});
        refsLayer.addReferent(refs1);

    }
}
