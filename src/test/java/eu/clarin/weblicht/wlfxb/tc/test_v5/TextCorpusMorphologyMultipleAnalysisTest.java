/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.test_v5;

import eu.clarin.weblicht.wlfxb.io.TextCorpusStreamed;
import eu.clarin.weblicht.wlfxb.tc.api.*;
import eu.clarin.weblicht.wlfxb.tc.test.AbstractTextCorpusTest;
import eu.clarin.weblicht.wlfxb.tc.xb.MorphologyTagStored;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import java.util.ArrayList;
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
public class TextCorpusMorphologyMultipleAnalysisTest extends AbstractTextCorpusTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data_v5/tc-morph/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER_SCORE = "/data_v5/tc-morph/tcf-afterScore.xml";
    private static final String EXPECTED_OUTPUT_FILE_SCORE = "/data_v5/tc-morph/output-expectedScore.xml";
    private static final String OUTPUT_FILE = "output.xml";

    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeMorphologyAnnotation
            = EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterMorphologyAnnotation
            = EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.MORPHOLOGY);

    @Test
    public void testRead_WhenMultipleAnalysis() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER_SCORE, layersToReadAfterMorphologyAnnotation);
        MorphologyLayer layer = tc.getMorphologyLayer();
        Assert.assertEquals(true, layer.hasCharoffsets());
        Assert.assertEquals(true, layer.hasSegmentation());
        Assert.assertEquals("STTS", layer.getTagset());
        Assert.assertEquals(tc.getTokensLayer().getToken(3), layer.getTokens(layer.getAnalysis(0))[0]);

        //reading feature structure from first tag element
        Assert.assertEquals(true, layer.getAnalysis(0).getTags().get(0).isScore());
        Assert.assertEquals(new Double(0.8), layer.getAnalysis(0).getTags().get(0).getScore());
        Assert.assertEquals(true, layer.getAnalysis(0).getTags().get(0).getFeatures()[0].isTerminal());
        Assert.assertEquals("cat", layer.getAnalysis(0).getTags().get(0).getFeatures()[0].getName());
        Assert.assertEquals("noun", layer.getAnalysis(0).getTags().get(0).getFeatures()[0].getValue());
        Assert.assertEquals(false, layer.getAnalysis(0).getTags().get(0).getFeatures()[4].isTerminal());
        Assert.assertEquals("test1", layer.getAnalysis(0).getTags().get(0).getFeatures()[4].getName());
        Assert.assertEquals("noun", layer.getAnalysis(0).getTags().get(0).getFeatures()[4].getSubfeatures()[0].getValue());
    }

    @Test
    public void testReadWrite_WhenMultipleAnalysis() throws Exception {
        String outfile = testFolder.getRoot() + File.separator + OUTPUT_FILE;
        TextCorpusStreamed tc = open(INPUT_FILE_WITHOUT_LAYER, outfile, layersToReadBeforeMorphologyAnnotation);
        System.out.println(tc);
        MorphologyLayer morphology = tc.createMorphologyLayer("STTS", true, true);
        for (int i = 0; i < tc.getTokensLayer().size(); i++) {
            Token token = tc.getTokensLayer().getToken(i);
            // create morphology layer (for the 4th token)
            if (i == 3) {
                addMultipleAnalysis(token, morphology);
            }
        }
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE_SCORE, outfile);
    }

    private void addMultipleAnalysis(Token token, MorphologyLayer morphology) {

        List<Feature> featuresForTag1 = new ArrayList<Feature>();
        List<Feature> featuresForTag2 = new ArrayList<Feature>();

        //Create fist tag element...
        Feature feature1 = morphology.createFeature("cat", "noun");
        featuresForTag1.add(feature1);
        Feature feature2 = morphology.createFeature("case", "acc");
        featuresForTag1.add(feature2);
        Feature feature3 = morphology.createFeature("gender", "fem");
        featuresForTag1.add(feature3);
        Feature feature4 = morphology.createFeature("number", "singular");
        featuresForTag1.add(feature4);

        List<Feature> subfeatures5 = new ArrayList<Feature>();
        Feature feature5_1 = morphology.createFeature("cat", "noun");
        subfeatures5.add(feature5_1);
        Feature feature5_2 = morphology.createFeature("case", "acc");
        subfeatures5.add(feature5_2);
        Feature feature5_3 = morphology.createFeature("gender", "fem");
        subfeatures5.add(feature5_3);
        Feature feature5_4 = morphology.createFeature("number", "singular");
        subfeatures5.add(feature5_4);
        Feature feature5 = morphology.createFeature("test1", subfeatures5);
        featuresForTag1.add(feature5);

        //Create second tag element...
        Feature feature11 = morphology.createFeature("cat", "pronoun");
        featuresForTag2.add(feature11);
        Feature feature12 = morphology.createFeature("case", "acc");
        featuresForTag2.add(feature12);
        Feature feature13 = morphology.createFeature("gender", "fem");
        featuresForTag2.add(feature13);
        Feature feature14 = morphology.createFeature("number", "plural");
        featuresForTag2.add(feature14);

        List<Feature> subfeatures15 = new ArrayList<Feature>();
        Feature feature15_1 = morphology.createFeature("cat", "pronoun");
        subfeatures15.add(feature15_1);
        Feature feature15_2 = morphology.createFeature("case", "acc");
        subfeatures15.add(feature15_2);
        Feature feature15_3 = morphology.createFeature("gender", "fem");
        subfeatures15.add(feature15_3);
        Feature feature15_4 = morphology.createFeature("number", "plural");
        subfeatures15.add(feature15_4);
        Feature feature15 = morphology.createFeature("test2", subfeatures15);
        featuresForTag2.add(feature15);

        //Create segment
        List<MorphologySegment> segments = new ArrayList<MorphologySegment>();
        List<MorphologySegment> subsegments1 = new ArrayList<MorphologySegment>();
        MorphologySegment s1_1 = morphology.createSegment("stem", "noun", "comp", 0, 4, "Käse");
        subsegments1.add(s1_1);
        MorphologySegment s1_2 = morphology.createSegment("stem", "noun", "base", 4, 9, "Pizza");
        subsegments1.add(s1_2);
        MorphologySegment s1 = morphology.createSegment(null, "noun", null, 0, 9, subsegments1);
        segments.add(s1);

        MorphologyTagStored tag1 = morphology.createTag(0.8, featuresForTag1);
        MorphologyTagStored tag2 = morphology.createTag(0.6, featuresForTag2);
        List<MorphologyTagStored> tags = new ArrayList<MorphologyTagStored>();
        tags.add(tag1);
        tags.add(tag2);
        morphology.addMultipleAnalysis(token, tags, segments);
    }
}
