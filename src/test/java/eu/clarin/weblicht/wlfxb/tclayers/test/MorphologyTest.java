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
 * @author Yana Panchenko
 *
 */
public class MorphologyTest {

    private static final String INPUT = "/data/tc-morph/layer-input.xml";
    private static final String INPUT_TAG = "/data/tc-morph/layer-inputTagSet.xml";
    private static final String INPUT_SCORE = "/data/tc-morph/layer-inputScore.xml";
    private static final String INPUT_ANY_ATTRIBUTES = "/data/tc-morph/layer-inputAnyAtt.xml";

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
    public void testReadAndWriteBackTag() throws Exception {

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
        Assert.assertEquals(true, layer.getAnalysis(0).getFeatures()[0].isTerminal());
        Assert.assertEquals("cat", layer.getAnalysis(0).getFeatures()[0].getName());
        Assert.assertEquals("noun", layer.getAnalysis(0).getFeatures()[0].getValue());
        Assert.assertEquals(false, layer.getAnalysis(0).getFeatures()[4].isTerminal());
        Assert.assertEquals("test", layer.getAnalysis(0).getFeatures()[4].getName());
        Assert.assertEquals("noun", layer.getAnalysis(0).getFeatures()[4].getSubfeatures()[0].getValue());
        Assert.assertEquals(1, layer.size());

    }

    @Test
    public void testReadAndWriteBackScore() throws Exception {

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
        
        //reading feature structure from second tag element
        Assert.assertEquals(true, layer.getAnalysis(0).getTags().get(1).isScore());
        Assert.assertEquals(new Double(0.6), layer.getAnalysis(0).getTags().get(1).getScore());
        Assert.assertEquals(true, layer.getAnalysis(0).getTags().get(1).getFeatures()[0].isTerminal());
        Assert.assertEquals("cat", layer.getAnalysis(0).getTags().get(1).getFeatures()[0].getName());
        Assert.assertEquals("pronoun", layer.getAnalysis(0).getTags().get(1).getFeatures()[0].getValue());
        Assert.assertEquals(false, layer.getAnalysis(0).getTags().get(1).getFeatures()[4].isTerminal());
        Assert.assertEquals("test2", layer.getAnalysis(0).getTags().get(1).getFeatures()[4].getName());
        Assert.assertEquals("pronoun", layer.getAnalysis(0).getTags().get(1).getFeatures()[4].getSubfeatures()[0].getValue());

        //reading first feature structure  layer directly
        Assert.assertEquals(true, layer.getAnalysis(0).getFeatures(0)[0].isTerminal());
        Assert.assertEquals("cat", layer.getAnalysis(0).getFeatures(0)[0].getName());
        Assert.assertEquals("noun", layer.getAnalysis(0).getFeatures(0)[0].getValue());
        Assert.assertEquals(false, layer.getAnalysis(0).getFeatures(0)[4].isTerminal());
        Assert.assertEquals("test1", layer.getAnalysis(0).getFeatures(0)[4].getName());
        Assert.assertEquals("noun", layer.getAnalysis(0).getFeatures(0)[4].getSubfeatures()[0].getValue());

        //reading second feature structure layer directly
        Assert.assertEquals(true, layer.getAnalysis(0).getFeatures(1)[0].isTerminal());
        Assert.assertEquals("cat", layer.getAnalysis(0).getFeatures(1)[0].getName());
        Assert.assertEquals("pronoun", layer.getAnalysis(0).getFeatures(1)[0].getValue());
        Assert.assertEquals(false, layer.getAnalysis(0).getFeatures(1)[4].isTerminal());
        Assert.assertEquals("test2", layer.getAnalysis(0).getFeatures(1)[4].getName());
        Assert.assertEquals("pronoun", layer.getAnalysis(0).getFeatures(1)[4].getSubfeatures()[0].getValue());
        Assert.assertEquals(1, layer.size());

    }
     @Test
    public void testReadAndWriteBackAnyAttributes() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_ANY_ATTRIBUTES);
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
        Assert.assertEquals("noun", layer.getAnalysis(0).getTags().get(0).getFeatures()[4].getSubfeatures()[0].getValue());
        
        //testing extra attributes in feature
        Integer index=0;
         for (String anyAttribute :layer.getAnalysis(0).getTags().get(0).getFeatures()[0].getExtraAtrributes().keySet()) {
            if (index == 0) {
                Assert.assertEquals("baseForm", anyAttribute);
                Assert.assertEquals("baseFormFeature", layer.getAnalysis(0).getTags().get(0).getFeatures()[0].getExtraAtrributes().get(anyAttribute));
            }
            if (index == 1) {
                Assert.assertEquals("alterForm", anyAttribute);
                Assert.assertEquals("alterFormFeature", layer.getAnalysis(0).getTags().get(0).getFeatures()[0].getExtraAtrributes().get(anyAttribute));
            }

            index++;
        }
         
        //testing extra attributes in sub features 
        index=0;
         for (String anyAttribute :layer.getAnalysis(0).getTags().get(0).getFeatures()[4].getSubfeatures()[0].getExtraAtrributes().keySet()) {
            if (index == 0) {
                Assert.assertEquals("baseForm", anyAttribute);
                Assert.assertEquals("baseFormSubFeature", layer.getAnalysis(0).getTags().get(0).getFeatures()[4].getSubfeatures()[0].getExtraAtrributes().get(anyAttribute));
            }
            if (index == 1) {
                Assert.assertEquals("alterForm", anyAttribute);
                Assert.assertEquals("alterFormSubFeature", layer.getAnalysis(0).getTags().get(0).getFeatures()[4].getSubfeatures()[0].getExtraAtrributes().get(anyAttribute));
            }

            index++;
        }
         
          //testing extra attributes in segment features 
         index=0;
         for (String anyAttribute :layer.getAnalysis(0).getSegmentation()[0].getExtraAtrributes().keySet()) {
             System.out.println(anyAttribute);
            if (index == 0) {
                Assert.assertEquals("baseForm", anyAttribute);
                Assert.assertEquals("baseFormSegment", layer.getAnalysis(0).getSegmentation()[0].getExtraAtrributes().get(anyAttribute));
            }
            if (index == 1) {
                Assert.assertEquals("alterForm", anyAttribute);
                Assert.assertEquals("alterFormSegment", layer.getAnalysis(0).getSegmentation()[0].getExtraAtrributes().get(anyAttribute));
            }

            index++;
        }
         
         
        
    }
    
}
