/**
 *
 */
package eu.clarin.weblicht.wlfxb.lx.test;

import eu.clarin.weblicht.wlfxb.io.LexiconStreamed;
import eu.clarin.weblicht.wlfxb.lx.api.Entry;
import eu.clarin.weblicht.wlfxb.lx.api.Lexicon;
import eu.clarin.weblicht.wlfxb.lx.api.SyllabificationsLayer;
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
public class LexiconSyllabificationsTest extends AbstractLexiconTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/lx-syl/lx-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/lx-syl/lx-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/lx-syl/output-expected.xml";
    private static final String OUTPUT_FILE = "output.xml";
    private static final EnumSet<LexiconLayerTag> layersToReadBefore =
            EnumSet.of(LexiconLayerTag.ENTRIES);
    private static final EnumSet<LexiconLayerTag> layersToReadAfter =
            EnumSet.of(LexiconLayerTag.ENTRIES, LexiconLayerTag.SYLLABIFICATIONS);
    public static final Map<String, String> lemma2Syllab = new HashMap<String, String>();

    static {
        lemma2Syllab.put("Peter", "Pe-ter");
        lemma2Syllab.put("essen", "e-ssen");
        lemma2Syllab.put("ein", "ein");
        lemma2Syllab.put("Pizza", "Pi-zza");
        lemma2Syllab.put("sie", "sie");
        lemma2Syllab.put("schmecken", "schme-cken");
        lemma2Syllab.put("er", "er");
    }

    @Test
    public void testRead() throws Exception {
        Lexicon lex = read(INPUT_FILE_WITH_LAYER, layersToReadAfter);
        SyllabificationsLayer layer = lex.getSyllabificationsLayer();
        Assert.assertEquals(7, layer.size());
        Assert.assertEquals("pe-ter", layer.getSyllabification(0).getString().toLowerCase());
        Assert.assertEquals(lex.getEntriesLayer().getEntry(0), layer.getEntry(layer.getSyllabification(0)));
        Assert.assertEquals(layer.getSyllabification(5), layer.getSyllabification(lex.getEntriesLayer().getEntry(6)));
    }

    @Test
    public void testReadWrite() throws Exception {
        String outfile = testFolder.getRoot() + File.separator + OUTPUT_FILE;
        LexiconStreamed lex = open(INPUT_FILE_WITHOUT_LAYER, outfile, layersToReadBefore);
        System.out.println(lex);
        SyllabificationsLayer syls = lex.createSyllabificationsLayer();
        for (int i = 0; i < lex.getEntriesLayer().size(); i++) {
            Entry lemma = lex.getEntriesLayer().getEntry(i);
            String syl = syllabify(lemma.getString());
            if (syl != null) {
                syls.addSyllabification(syl, lemma);
            }
        }
        // IMPORTANT close the streams!!!
        lex.close();
        System.out.println(lex);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, outfile);
    }

    private String syllabify(String tokenString) {
        return lemma2Syllab.get(tokenString);
    }
}
