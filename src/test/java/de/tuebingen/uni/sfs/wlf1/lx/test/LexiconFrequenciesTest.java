/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.lx.test;

import de.tuebingen.uni.sfs.wlf1.io.LexiconStreamed;
import de.tuebingen.uni.sfs.wlf1.lx.api.FrequenciesLayer;
import de.tuebingen.uni.sfs.wlf1.lx.api.Lemma;
import de.tuebingen.uni.sfs.wlf1.lx.api.Lexicon;
import de.tuebingen.uni.sfs.wlf1.lx.xb.LexiconLayerTag;
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
            EnumSet.of(LexiconLayerTag.LEMMAS);
    private static final EnumSet<LexiconLayerTag> layersToReadAfterFreqAnnotation =
            EnumSet.of(LexiconLayerTag.LEMMAS, LexiconLayerTag.FREQUENCIES);

    @Test
    public void testRead() throws Exception {
        Lexicon lex = read(INPUT_FILE_WITH_LAYER, layersToReadAfterFreqAnnotation);
        FrequenciesLayer layer = lex.getFrequenciesLayer();
        Assert.assertEquals(9, layer.size());
        Assert.assertEquals(100, layer.getFrequency(0).getValue());
        Assert.assertEquals(lex.getLemmasLayer().getLemma(0), layer.getLemma(layer.getFrequency(0)));
    }

    @Test
    public void testReadWrite() throws Exception {
        LexiconStreamed lex = open(INPUT_FILE_WITHOUT_LAYER, OUTPUT_FILE, layersToReadBeforeFreqAnnotation);
        System.out.println(lex);
        // create frequencies layer, it's empty at first
        FrequenciesLayer freqs = lex.createFrequenciesLayer();
        // assign frequencies
        int fr = 100;
        for (int i = 0; i < lex.getLemmasLayer().size(); i++) {
            Lemma lemma = lex.getLemmasLayer().getLemma(i);
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
