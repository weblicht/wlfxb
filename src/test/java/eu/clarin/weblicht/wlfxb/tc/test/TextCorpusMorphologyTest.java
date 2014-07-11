/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.test;

import eu.clarin.weblicht.wlfxb.io.TextCorpusStreamed;
import eu.clarin.weblicht.wlfxb.tc.api.*;
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
public class TextCorpusMorphologyTest extends AbstractTextCorpusTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/tc-morph-tagset/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/tc-morph-tagset/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/tc-morph-tagset/output-expected.xml";
    private static final String OUTPUT_FILE = "/tmp/output.xml";
    private static final String EXP_TAGSET = "PENN";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeMorphologyAnnotation =
            EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterMorphologyAnnotation =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.MORPHOLOGY);


    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterMorphologyAnnotation);
        MorphologyLayer layer = tc.getMorphologyLayer();
        Assert.assertEquals(true, layer.hasCharoffsets());
        Assert.assertEquals(true, layer.hasSegmentation());
        Assert.assertEquals(tc.getTokensLayer().getToken(3), layer.getTokens(layer.getAnalysis(0))[0]);
        Assert.assertEquals(true, layer.getAnalysis(0).getFeatures()[0].isTerminal());
        Assert.assertEquals("cat", layer.getAnalysis(0).getFeatures()[0].getName());
        Assert.assertEquals("noun", layer.getAnalysis(0).getFeatures()[0].getValue());
        Assert.assertEquals(false, layer.getAnalysis(0).getFeatures()[4].isTerminal());
        Assert.assertEquals("test", layer.getAnalysis(0).getFeatures()[4].getName());
        Assert.assertEquals("noun", layer.getAnalysis(0).getFeatures()[4].getSubfeatures()[0].getValue());
        Assert.assertEquals(1, layer.size());
    }

    @Test
    public void testReadWrite() throws Exception {
        TextCorpusStreamed tc = open(INPUT_FILE_WITHOUT_LAYER, OUTPUT_FILE, layersToReadBeforeMorphologyAnnotation);
        System.out.println(tc);
        MorphologyLayer morphology = tc.createMorphologyLayer(EXP_TAGSET, true, true);
        for (int i = 0; i < tc.getTokensLayer().size(); i++) {
            Token token = tc.getTokensLayer().getToken(i);
            // create morphology annotation for the test token (for the 4th token)
            if (i == 3) {
                addAnalysis(token, morphology);
            }
        }
        tc.close();
        Assert.assertEquals(EXP_TAGSET, tc.getMorphologyLayer().getTagset());
        System.out.println(tc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    private void addAnalysis(Token token, MorphologyLayer morphology) {

        List<Feature> features = new ArrayList<Feature>();
//		<fs>
//        <f name="cat">noun</f>
//        <f name="case">acc</f>
//        <f name="gender">fem</f>
//        <f name="number">singular</f>
//        <f name="test">
//        	<fs>
//	          <f name="cat">noun</f>
//	          <f name="case">acc</f>
//	          <f name="gender">fem</f>
//	          <f name="number">singular</f>
//	          <f name="number">singular</f>
//      	</fs>
//        </f>
//      </fs>
        Feature feature1 = morphology.createFeature("cat", "noun");
        features.add(feature1);
        Feature feature2 = morphology.createFeature("case", "acc");
        features.add(feature2);
        Feature feature3 = morphology.createFeature("gender", "fem");
        features.add(feature3);
        Feature feature4 = morphology.createFeature("number", "singular");
        features.add(feature4);

        List<Feature> subfeatures5 = new ArrayList<Feature>();
        Feature feature5_1 = morphology.createFeature("cat", "noun");
        subfeatures5.add(feature5_1);
        Feature feature5_2 = morphology.createFeature("case", "acc");
        subfeatures5.add(feature5_2);
        Feature feature5_3 = morphology.createFeature("gender", "fem");
        subfeatures5.add(feature5_3);
        Feature feature5_4 = morphology.createFeature("number", "singular");
        subfeatures5.add(feature5_4);
        Feature feature5_5 = morphology.createFeature("number", "singular");
        subfeatures5.add(feature5_5);
        Feature feature5 = morphology.createFeature("test", subfeatures5);

        features.add(feature5);

        List<MorphologySegment> segments = new ArrayList<MorphologySegment>();
//		<segment cat="noun" start="0" end="9">
//        <segment type="stem" cat="noun" func="comp" start="0" end="4">Käse</segment>
//        <segment type="stem" cat="noun" func="base" start="4" end="9">Pizza</segment>
//      </segment>
        List<MorphologySegment> subsegments1 = new ArrayList<MorphologySegment>();
        MorphologySegment s1_1 = morphology.createSegment("stem", "noun", "comp", 0, 4, "Käse");
        subsegments1.add(s1_1);
        MorphologySegment s1_2 = morphology.createSegment("stem", "noun", "base", 4, 9, "Pizza");
        subsegments1.add(s1_2);
        MorphologySegment s1 = morphology.createSegment(null, "noun", null, 0, 9, subsegments1);
        segments.add(s1);
        morphology.addAnalysis(token, features, segments);
    }
}
