/**
 *
 */
package eu.clarin.weblicht.wlfxb.lx.test;

import eu.clarin.weblicht.wlfxb.io.LexiconStreamed;
import eu.clarin.weblicht.wlfxb.lx.api.FrequenciesLayer;
import eu.clarin.weblicht.wlfxb.lx.api.Entry;
import eu.clarin.weblicht.wlfxb.lx.api.FrequencyType;
import eu.clarin.weblicht.wlfxb.lx.api.Lexicon;
import eu.clarin.weblicht.wlfxb.lx.xb.LexiconLayerTag;
import java.util.EnumSet;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class LexiconFrequenciesTest extends AbstractLexiconTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/lx-freq/lx-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/lx-freq/lx-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/lx-freq/output-expected.xml";
    private static final String OUTPUT_FILE = "/tmp/output.xml";
    private static final EnumSet<LexiconLayerTag> layersToReadBeforeFreqAnnotation =
            EnumSet.of(LexiconLayerTag.ENTRIES);
    private static final EnumSet<LexiconLayerTag> layersToReadAfterFreqAnnotation =
            EnumSet.of(LexiconLayerTag.ENTRIES, LexiconLayerTag.FREQUENCIES);

    @Test
    public void testRead() throws Exception {
        Lexicon lex = read(INPUT_FILE_WITH_LAYER, layersToReadAfterFreqAnnotation);
        FrequenciesLayer layer = lex.getFrequenciesLayer();
        Assert.assertEquals(8, layer.size());
        Assert.assertEquals(100, layer.getFrequency(0).getValue(), 0.0001);
        Assert.assertEquals(lex.getEntriesLayer().getEntry(0), layer.getEntry(layer.getFrequency(0)));
    }

    @Test
    public void testReadWrite() throws Exception {
        LexiconStreamed lex = open(INPUT_FILE_WITHOUT_LAYER, OUTPUT_FILE, layersToReadBeforeFreqAnnotation);
        System.out.println(lex);
        // create frequencies layer, it's empty at first
        FrequenciesLayer freqs = lex.createFrequenciesLayer(FrequencyType.absolute);
        // assign frequencies
        int fr = 100;
        for (int i = 0; i < lex.getEntriesLayer().size(); i++) {
            Entry lemma = lex.getEntriesLayer().getEntry(i);
            // create and add frequency for the lemma
            freqs.addFrequency(lemma, fr++);
        }
        // IMPORTANT close the streams!!!
        lex.close();
        System.out.println(lex);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }
}
