/**
 *
 */
package eu.clarin.weblicht.wlfxb.lx.test;

import eu.clarin.weblicht.wlfxb.io.LexiconStreamed;
import eu.clarin.weblicht.wlfxb.lx.api.Entry;
import eu.clarin.weblicht.wlfxb.lx.api.Lexicon;
import eu.clarin.weblicht.wlfxb.lx.api.PosTag;
import eu.clarin.weblicht.wlfxb.lx.api.PosTagsLayer;
import eu.clarin.weblicht.wlfxb.lx.xb.LexiconLayerTag;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import java.io.File;

/**
 * @author Yana Panchenko
 *
 */
public class LexiconPosTagsTest extends AbstractLexiconTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/lx-pos/lx-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/lx-pos/lx-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/lx-pos/output-expected.xml";
    private static final String OUTPUT_FILE = "output.xml";
    private static final EnumSet<LexiconLayerTag> layersToReadBeforePosTagging =
            EnumSet.of(LexiconLayerTag.ENTRIES);
    private static final EnumSet<LexiconLayerTag> layersToReadAfterPosTagging =
            EnumSet.of(LexiconLayerTag.ENTRIES, LexiconLayerTag.POSTAGS);
    public static final Map<String, String> lemma2Pos = new HashMap<String, String>();

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
        Assert.assertEquals(9, layer.size());
        Assert.assertEquals("NE", layer.getTag(0).getString());
        Assert.assertEquals(lex.getEntriesLayer().getEntry(0), layer.getEntry(layer.getTag(0)));
        Assert.assertEquals(lex.getEntriesLayer().getEntry(3), layer.getEntry(layer.getTag(3)));
        Assert.assertEquals(lex.getEntriesLayer().getEntry(3), layer.getEntry(layer.getTag(4)));
        Assert.assertArrayEquals(
                new PosTag[]{layer.getTag(3), layer.getTag(4)},
                layer.getTags(lex.getEntriesLayer().getEntry(3)));
    }

    @Test
    public void testReadWrite() throws Exception {
        String outfile = testFolder.getRoot() + File.separator + OUTPUT_FILE;
        LexiconStreamed lex = open(INPUT_FILE_WITHOUT_LAYER, outfile, layersToReadBeforePosTagging);
        System.out.println(lex);
        // create part of speech layer, it's empty at first
        PosTagsLayer tags = lex.createPosTagsLayer("STTS");
        for (int i = 0; i < lex.getEntriesLayer().size(); i++) {
            Entry lemma = lex.getEntriesLayer().getEntry(i);
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
        assertEqualXml(EXPECTED_OUTPUT_FILE, outfile);
    }

    private String tag(String tokenString) {
        return lemma2Pos.get(tokenString);
    }
}
