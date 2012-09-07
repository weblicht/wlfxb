/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.test;

import de.tuebingen.uni.sfs.wlf1.io.TextCorpusStreamed;
import de.tuebingen.uni.sfs.wlf1.tc.api.PhoneticsLayer;
import de.tuebingen.uni.sfs.wlf1.tc.api.Pronunciation;
import de.tuebingen.uni.sfs.wlf1.tc.api.TextCorpus;
import de.tuebingen.uni.sfs.wlf1.tc.api.TokensLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.PronunciationType;
import de.tuebingen.uni.sfs.wlf1.tc.xb.TextCorpusLayerTag;
import de.tuebingen.uni.sfs.wlf1.test.utils.TestUtils;
import java.io.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class TextCorpusPhoneticsTest {

    private static final String INPUT_FILE_WITHOUT_PARSING = "data/tc-phon/tcf-before.xml";
    private static final String INPUT_FILE_WITH_PARSING = "data/tc-phon/tcf-after.xml";
    private static final String OUTPUT_FILE = "data/tc-phon/output.xml";
    private static final String EXPECTED_OUTPUT_FILE = "data/tc-phon/output-expected.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforePhoneticsAnnotation =
            EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterPhoneticsAnnotation =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.PHONETICS);

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_PARSING, layersToReadAfterPhoneticsAnnotation);
        PhoneticsLayer layer = tc.getPhoneticsLayer();

        assertEquals("SAMPA", layer.getAlphabet());
        assertEquals(1, layer.size());
        assertEquals(PronunciationType.word, layer.getSegment(0).getPronunciations()[0].getType());
        assertEquals(PronunciationType.syllable, layer.getSegment(0).getPronunciations()[0].getChildren()[0].getType());
        assertEquals(PronunciationType.phone, layer.getSegment(0).getPronunciations()[0].getChildren()[0].getChildren()[0].getType());
        assertEquals("Sm'E.k@n", layer.getSegment(0).getPronunciations()[0].getCanonical());
        assertEquals("Sm'E.kN", layer.getSegment(0).getPronunciations()[0].getRealized());
        assertEquals(0, layer.getSegment(0).getPronunciations()[0].getChildren()[0].getOnsetInSeconds(), 0.00001);
        assertEquals(0.0002, layer.getSegment(0).getPronunciations()[0].getChildren()[0].getOffsetInSeconds(), 0.00001);

        assertEquals(tc.getTokensLayer().getToken(6), layer.getToken(layer.getSegment(0)));
    }

    private TextCorpus read(String file, EnumSet<TextCorpusLayerTag> layersToRead) throws Exception {
        InputStream is = new FileInputStream(INPUT_FILE_WITH_PARSING);
        TextCorpusStreamed tc = new TextCorpusStreamed(is, layersToRead);
        tc.close();
        System.out.println(tc);
        return tc;
    }

    @Test
    public void testReadWrite() throws Exception {
        File file = new File(INPUT_FILE_WITHOUT_PARSING);
        InputStream is = new FileInputStream(file);
        File ofile = new File(OUTPUT_FILE);
        OutputStream os = new FileOutputStream(ofile);
        TextCorpusStreamed tc = new TextCorpusStreamed(is, layersToReadBeforePhoneticsAnnotation, os, false);
        System.out.println(tc);
        TokensLayer tokensLayer = tc.getTokensLayer();
        PhoneticsLayer phoneticsLayer = tc.createPhotenicsLayer("SAMPA");
        for (int i = 0; i < tokensLayer.size(); i++) {
            // creates phonetic segment for the test token
            if (tokensLayer.getToken(i).getString().equals("schmeckte")) {
                Pronunciation pron = pronunciationForSchmeckte(phoneticsLayer);
                phoneticsLayer.addSegment(pron, tokensLayer.getToken(i));
            }
        }
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        TestUtils.assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    private Pronunciation pronunciationForSchmeckte(PhoneticsLayer phoneticsLayer) {
//		<pron type="word" cp="Sm'E.k@n" rp="Sm'E.kN">
//  		<pron type="syllable" onset="0" offset="0.0002" rp="SmE">
//  		 <pron type="phone" onset="0" offset="0.0001" rp="S"/> 
//           <pron type="phone" onset="0.0001" offset="0.00015" rp="m"/>
//           <pron type="phone" onset="0.00015" offset="0.0002" rp="E"/>
//  		</pron>
//          <pron type="syllable" onset="0.0002" offset="0.0003" rp="kN">
//  		 <pron type="phone" onset="0.0002" offset="0.00025" rp="k"/>
//           <pron type="phone" onset="0.00025" offset="0.0003" rp="N"/>
//          </pron>
//     </pron>

        Pronunciation phone1_1 = phoneticsLayer.createPronunciation(
                PronunciationType.phone,
                "S", (float) 0, (float) 0.0001);
        Pronunciation phone1_2 = phoneticsLayer.createPronunciation(
                PronunciationType.phone,
                "m", (float) 0.0001, (float) 0.00015);
        Pronunciation phone1_3 = phoneticsLayer.createPronunciation(
                PronunciationType.phone,
                "E", (float) 0.00015, (float) 0.0002);
        List<Pronunciation> phones1 = new ArrayList<Pronunciation>();
        phones1.add(phone1_1);
        phones1.add(phone1_2);
        phones1.add(phone1_3);
        Pronunciation syllable1 = phoneticsLayer.createPronunciation(
                PronunciationType.syllable,
                "SmE", (float) 0, (float) 0.0002, phones1);


        Pronunciation phone2_1 = phoneticsLayer.createPronunciation(
                PronunciationType.phone,
                "k", (float) 0.0002, (float) 0.00025);
        Pronunciation phone2_2 = phoneticsLayer.createPronunciation(
                PronunciationType.phone,
                "N", (float) 0.00025, (float) 0.0003);
        List<Pronunciation> phones2 = new ArrayList<Pronunciation>();
        phones2.add(phone2_1);
        phones2.add(phone2_2);
        Pronunciation syllable2 = phoneticsLayer.createPronunciation(
                PronunciationType.syllable,
                "kN", (float) 0.0002, (float) 0.0003, phones2);

        List<Pronunciation> syllables = new ArrayList<Pronunciation>();
        syllables.add(syllable1);
        syllables.add(syllable2);
        Pronunciation word = phoneticsLayer.createPronunciation(
                PronunciationType.word, "Sm'E.k@n", "Sm'E.kN", syllables);

        return word;

    }
}
