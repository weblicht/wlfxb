/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.lx.test;

import de.tuebingen.uni.sfs.wlf1.io.LexiconStreamed;
import de.tuebingen.uni.sfs.wlf1.lx.api.FrequenciesLayer;
import de.tuebingen.uni.sfs.wlf1.lx.api.Lemma;
import de.tuebingen.uni.sfs.wlf1.lx.api.Lexicon;
import de.tuebingen.uni.sfs.wlf1.lx.xb.LexiconLayerTag;
import de.tuebingen.uni.sfs.wlf1.test.utils.TestUtils;
import java.io.*;
import java.util.EnumSet;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class LexiconFrequenciesTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "data/lx-freq/lx-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "data/lx-freq/lx-after.xml";
    private static final String OUTPUT_FILE = "data/lx-freq/output.xml";
    private static final String EXPECTED_OUTPUT_FILE = "data/lx-freq/output-expected.xml";
    private static final EnumSet<LexiconLayerTag> layersToReadBeforeFreqAnnotation =
            EnumSet.of(LexiconLayerTag.LEMMAS);
    private static final EnumSet<LexiconLayerTag> layersToReadAfterFreqAnnotation =
            EnumSet.of(LexiconLayerTag.LEMMAS, LexiconLayerTag.FREQUENCIES);

    @Test
    public void testRead() throws Exception {
        Lexicon lex = read(INPUT_FILE_WITH_LAYER, layersToReadAfterFreqAnnotation);
        FrequenciesLayer layer = lex.getFrequenciesLayer();
        assertEquals(9, layer.size());
        assertEquals(100, layer.getFrequency(0).getValue());
        assertEquals(lex.getLemmasLayer().getLemma(0), layer.getLemma(layer.getFrequency(0)));
    }

    private Lexicon read(String file, EnumSet<LexiconLayerTag> layersToRead) throws Exception {
        InputStream is = new FileInputStream(INPUT_FILE_WITH_LAYER);
        LexiconStreamed lex = new LexiconStreamed(is, layersToRead);
        lex.close();
        System.out.println(lex);
        return lex;
    }

    @Test
    public void testReadWrite() throws Exception {
        File file = new File(INPUT_FILE_WITHOUT_LAYER);
        InputStream is = new FileInputStream(file);
        File ofile = new File(OUTPUT_FILE);
        OutputStream os = new FileOutputStream(ofile);
        LexiconStreamed lex = new LexiconStreamed(is, layersToReadBeforeFreqAnnotation, os, false);
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
        TestUtils.assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }
}
