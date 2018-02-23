/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test;

import eu.clarin.weblicht.wlfxb.tc.api.LexicalSemanticsLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.AntonymyLayerStored;
import eu.clarin.weblicht.wlfxb.tc.xb.HyperonymyLayerStored;
import eu.clarin.weblicht.wlfxb.tc.xb.HyponymyLayerStored;
import eu.clarin.weblicht.wlfxb.tc.xb.SynonymyLayerStored;
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
public class LexicalSemanticsTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT_SYN = "/data/tc-lexsem/layer-input-syn.xml";
    private static final String INPUT_ANT = "/data/tc-lexsem/layer-input-ant.xml";
    private static final String INPUT_HYPO = "/data/tc-lexsem/layer-input-hypo.xml";
    private static final String INPUT_HYPER = "/data/tc-lexsem/layer-input-hyper.xml";

    private static final String INPUT_SYN_ANY_ATTRIBUTES = "/data/tc-lexsem/layer-inputSynAnyAtt.xml";

    @Test
    public void testReadAndWriteBackSyn() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_SYN);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output-syn.xml"));

        LexicalSemanticsLayer layer = TestUtils.read(SynonymyLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals(1, layer.size());
        //assertEquals("GermaNet", layer.getSource());
        Assert.assertArrayEquals(new String[]{"futtern", "nehmen"}, layer.getOrthform(0).getValue());

    }

    @Test
    public void testReadAndWriteBackAnt() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_ANT);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output-ant.xml"));

        LexicalSemanticsLayer layer = TestUtils.read(AntonymyLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals(1, layer.size());
        //assertEquals("GermaNet", layer.getSource());
        Assert.assertArrayEquals(new String[]{"verhungern"}, layer.getOrthform(0).getValue());

    }

    @Test
    public void testReadAndWriteBackHypo() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_HYPO);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output-hypo.xml"));

        LexicalSemanticsLayer layer = TestUtils.read(HyponymyLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals(2, layer.size());
        //assertEquals("GermaNet", layer.getSource());
        assertArrayEquals(
                new String[]{"Art essen", "EssensOrt spezifiziert", "EssensZeit spezifiziert", "acheln", "aufessen", "aufnehmen", "einverleiben", "essen mit Instrument", "hermachen",
                    "stärken", "vertilgen", "wegessen"},
                layer.getOrthform(0).getValue());
    }

    @Test
    public void testReadAndWriteBackHyper() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_HYPER);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output-hyper.xml"));

        LexicalSemanticsLayer layer = TestUtils.read(HyperonymyLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        assertEquals(2, layer.size());
        //assertEquals("GermaNet", layer.getSource());
        Assert.assertArrayEquals(new String[]{"verzehren"}, layer.getOrthform(0).getValue());

    }

    @Test
    public void testReadAndWriteBackSynAnyAttributes() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_SYN_ANY_ATTRIBUTES);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output-syn.xml"));

        LexicalSemanticsLayer layer = TestUtils.read(SynonymyLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);
        Integer index = 0;
        for (String anyAttribute : layer.getOrthform(0).getExtraAtrributes().keySet()) {
            if (index == 0) {
                Assert.assertEquals("baseForm", anyAttribute);
                Assert.assertEquals("baseFormSyn", layer.getOrthform(0).getExtraAtrributes().get(anyAttribute));
            }
            if (index == 1) {
                Assert.assertEquals("alterForm", anyAttribute);
                Assert.assertEquals("alterFormSyn", layer.getOrthform(0).getExtraAtrributes().get(anyAttribute));
            }

            index++;
        }

        is.close();
        os.close();
    }
}
