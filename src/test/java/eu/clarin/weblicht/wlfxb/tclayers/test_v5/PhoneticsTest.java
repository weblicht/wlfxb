/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test_v5;

import eu.clarin.weblicht.wlfxb.tc.api.PhoneticsLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.PhoneticsLayerStored;
import eu.clarin.weblicht.wlfxb.tc.xb.PronunciationType;
import eu.clarin.weblicht.wlfxb.test.utils.TestUtils;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 * @author Yana Panchenko
 *
 */
public class PhoneticsTest {

    private static final String INPUT = "/data/tc-phon/layer-input.xml";
    private static final String INPUT_ANY_ATTRIBUTES = "/data_v5/tc-phon/layer-inputExtraAtt.xml";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        PhoneticsLayer layer = TestUtils.read(PhoneticsLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals("SAMPA", layer.getAlphabet());
        assertEquals(1, layer.size());
        assertEquals(PronunciationType.word, layer.getSegment(0).getPronunciations()[0].getType());
        assertEquals(PronunciationType.syllable, layer.getSegment(0).getPronunciations()[0].getChildren()[0].getType());
        assertEquals(PronunciationType.phone, layer.getSegment(0).getPronunciations()[0].getChildren()[0].getChildren()[0].getType());
        assertEquals("Sm'E.k@n", layer.getSegment(0).getPronunciations()[0].getCanonical());
        assertEquals("Sm'E.kN", layer.getSegment(0).getPronunciations()[0].getRealized());
        assertEquals(0, layer.getSegment(0).getPronunciations()[0].getChildren()[0].getOnsetInSeconds(), 0.00001);
        assertEquals(0.0002, layer.getSegment(0).getPronunciations()[0].getChildren()[0].getOffsetInSeconds(), 0.00001);
    }

    @Test
    public void testReadAndWriteBack_ExtraAttribute() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_ANY_ATTRIBUTES);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        PhoneticsLayer layer = TestUtils.read(PhoneticsLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        // testing extra attributes in word level
        String anyAttributeWord = layer.getSegment(0).getPronunciations()[0].getExtraAttributes().keySet().iterator().next();
        Assert.assertEquals("baseForm", anyAttributeWord);
        Assert.assertEquals("baseFormWord", layer.getSegment(0).getPronunciations()[0].getExtraAttributes().get(anyAttributeWord));

        // testing extra attributes in syllable level
        String anyAttribute = layer.getSegment(0).getPronunciations()[0].getChildren()[0].getExtraAttributes().keySet().iterator().next();
        Assert.assertEquals("baseForm", anyAttribute);
        Assert.assertEquals("baseFormSyllable", layer.getSegment(0).getPronunciations()[0].getChildren()[0].getExtraAttributes().get(anyAttribute));
    }
}
