/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.lx.test;

import de.tuebingen.uni.sfs.wlf1.io.LexiconStreamed;
import de.tuebingen.uni.sfs.wlf1.lx.api.Lemma;
import de.tuebingen.uni.sfs.wlf1.lx.api.Lexicon;
import de.tuebingen.uni.sfs.wlf1.lx.api.PosTag;
import de.tuebingen.uni.sfs.wlf1.lx.api.PosTagsLayer;
import de.tuebingen.uni.sfs.wlf1.lx.xb.LexiconLayerTag;
import de.tuebingen.uni.sfs.wlf1.test.utils.TestUtils;
import java.io.*;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class LexiconPosTagsTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "data/lx-pos/lx-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "data/lx-pos/lx-after.xml";
    private static final String OUTPUT_FILE = "data/lx-pos/output.xml";
    private static final String EXPECTED_OUTPUT_FILE = "data/lx-pos/output-expected.xml";
    private static final EnumSet<LexiconLayerTag> layersToReadBeforePosTagging =
            EnumSet.of(LexiconLayerTag.LEMMAS);
    private static final EnumSet<LexiconLayerTag> layersToReadAfterPosTagging =
            EnumSet.of(LexiconLayerTag.LEMMAS, LexiconLayerTag.POSTAGS);
    public static Map<String, String> lemma2Pos = new HashMap<String, String>();

    static {
        lemma2Pos.put("Peter", "NE");
        lemma2Pos.put("essen", "VVFIN");
        lemma2Pos.put("ein", "ART");
        lemma2Pos.put("Pizza", "NE");
        lemma2Pos.put(".", "$.");
        lemma2Pos.put("sie", "PPER");
        lemma2Pos.put("schmecken", "VVFIN");
        lemma2Pos.put("er", "PPER");
    }

    @Test
    public void testRead() throws Exception {
        Lexicon lex = read(INPUT_FILE_WITH_LAYER, layersToReadAfterPosTagging);
        PosTagsLayer layer = lex.getPosTagsLayer();
        assertEquals(10, layer.size());
        assertEquals("NE", layer.getTag(0).getString());
        assertEquals(lex.getLemmasLayer().getLemma(0), layer.getLemma(layer.getTag(0)));
        assertEquals(lex.getLemmasLayer().getLemma(3), layer.getLemma(layer.getTag(3)));
        assertEquals(lex.getLemmasLayer().getLemma(3), layer.getLemma(layer.getTag(4)));
        Assert.assertArrayEquals(
                new PosTag[]{layer.getTag(3), layer.getTag(4)},
                layer.getTags(lex.getLemmasLayer().getLemma(3)));
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
        LexiconStreamed lex = new LexiconStreamed(is, layersToReadBeforePosTagging, os, false);
        System.out.println(lex);
        // create part of speech layer, it's empty at first
        PosTagsLayer tags = lex.createPosTagsLayer("STTS");
        for (int i = 0; i < lex.getLemmasLayer().size(); i++) {
            Lemma lemma = lex.getLemmasLayer().getLemma(i);
            String posTag = tag(lemma.getString());
            // create and add part-of-speech tag to the tags layer
            tags.addTag(posTag, lemma);
            if (i == 3) { // another tag option for this lemma
                posTag = "NN";
                tags.addTag(posTag, lemma);
            }
        }
        // IMPORTANT close the streams!!!
        lex.close();
        System.out.println(lex);
        // compare output xml with expected xml
        TestUtils.assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    private String tag(String tokenString) {
        return lemma2Pos.get(tokenString);
    }
}
