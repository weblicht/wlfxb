/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test;

import eu.clarin.weblicht.wlfxb.tc.api.MorphologyLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.MorphologyLayerStored;
import eu.clarin.weblicht.wlfxb.test.utils.TestUtils;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 * @author Yana Panchenko and Mohammad Fazleh Elahi
 *
 */
public class MorphologyTest {

    private static final String INPUT = "/data/tc-morph/layer-input.xml";
    private static final String INPUT_TAG = "/data/tc-morph/layer-inputTagSet.xml";
    private static final String INPUT_SCORE = "/data/tc-morph/layer-inputScore.xml";
    private static final String INPUT_ANY_ATTRIBUTES = "/data/tc-morph/layer-inputExtraAtt.xml";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        MorphologyLayer layer = TestUtils.read(MorphologyLayerStored.class, is);
        //System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals(true, layer.hasCharoffsets());
        Assert.assertEquals(true, layer.hasSegmentation());
        Assert.assertEquals(true, layer.getAnalysis(0).getFeatures()[0].isTerminal());
        Assert.assertEquals("cat", layer.getAnalysis(0).getFeatures()[0].getName());
        Assert.assertEquals("noun", layer.getAnalysis(0).getFeatures()[0].getValue());
        Assert.assertEquals(false, layer.getAnalysis(0).getFeatures()[4].isTerminal());
        Assert.assertEquals("test", layer.getAnalysis(0).getFeatures()[4].getName());
        Assert.assertEquals("noun", layer.getAnalysis(0).getFeatures()[4].getSubfeatures()[0].getValue());
        Assert.assertEquals(1, layer.size());

    }

    @Test
    public void testReadAndWriteBack_WhenTagSet() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_TAG);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        MorphologyLayer layer = TestUtils.read(MorphologyLayerStored.class, is);
        //System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals(true, layer.hasCharoffsets());
        Assert.assertEquals(true, layer.hasSegmentation());
        Assert.assertEquals("STTS", layer.getTagset());
    }

    @Test
    public void testReadAndWriteBack_WhenMultipleAnalysis() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_SCORE);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        MorphologyLayer layer = TestUtils.read(MorphologyLayerStored.class, is);
        //System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals(true, layer.hasCharoffsets());
        Assert.assertEquals(true, layer.hasSegmentation());
        Assert.assertEquals("STTS", layer.getTagset());
        Assert.assertNotNull(layer.getAnalysis(0).getTags());

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
    public void testReadAndWriteBack_ExtraAttribute() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_ANY_ATTRIBUTES);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        MorphologyLayer layer = TestUtils.read(MorphologyLayerStored.class, is);
        //System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        //testing extra attributes in feature
        String anyAttributeFeature = layer.getAnalysis(0).getTags().get(0).getFeatures()[0].getExtraAtrributes().keySet().iterator().next();
        Assert.assertEquals("baseForm", anyAttributeFeature);
        Assert.assertEquals("baseFormFeature", layer.getAnalysis(0).getTags().get(0).getFeatures()[0].getExtraAtrributes().get(anyAttributeFeature));
        //testing extra attributes in sub features 
        String anyAttributeSubFeature = layer.getAnalysis(0).getTags().get(0).getFeatures()[4].getSubfeatures()[0].getExtraAtrributes().keySet().iterator().next();
        Assert.assertEquals("baseForm", anyAttributeSubFeature);
        Assert.assertEquals("baseFormSubFeature", layer.getAnalysis(0).getTags().get(0).getFeatures()[4].getSubfeatures()[0].getExtraAtrributes().get(anyAttributeSubFeature));
        //testing extra attributes in segment features 
        String anyAttributeSegment = layer.getAnalysis(0).getSegmentation()[0].getExtraAtrributes().keySet().iterator().next();
        Assert.assertEquals("baseForm", anyAttributeSegment);
        Assert.assertEquals("baseFormSegment", layer.getAnalysis(0).getSegmentation()[0].getExtraAtrributes().get(anyAttributeSegment));
    }

}
