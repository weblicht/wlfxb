/**
 *
 */
package eu.clarin.weblicht.wlfxb.lx.test;

import eu.clarin.weblicht.wlfxb.io.LexiconStreamed;
import eu.clarin.weblicht.wlfxb.lx.api.Cooccurrence;
import eu.clarin.weblicht.wlfxb.lx.api.CooccurrenceFunction;
import eu.clarin.weblicht.wlfxb.lx.api.EntriesLayer;
import eu.clarin.weblicht.wlfxb.lx.api.Lexicon;
import eu.clarin.weblicht.wlfxb.lx.api.CooccurrencesLayer;
import eu.clarin.weblicht.wlfxb.lx.api.EntriesLayer;
import eu.clarin.weblicht.wlfxb.lx.api.Term;
import eu.clarin.weblicht.wlfxb.lx.xb.LexiconLayerTag;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import java.io.File;

/**
 * @author Yana Panchenko
 *
 */
public class LexiconCooccurrencesTest extends AbstractLexiconTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/lx-cooc/lx-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/lx-cooc/lx-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/lx-cooc/output-expected.xml";
    private static final String OUTPUT_FILE = "output.xml";
    private static final EnumSet<LexiconLayerTag> layersToReadBeforeRelationAnnotation =
            EnumSet.of(LexiconLayerTag.ENTRIES);
    private static final EnumSet<LexiconLayerTag> layersToReadAfterRelationAnnotation =
            EnumSet.of(LexiconLayerTag.ENTRIES, LexiconLayerTag.COOCCURRENCES);

    @Test
    public void testRead() throws Exception {
        Lexicon lex = read(INPUT_FILE_WITH_LAYER, layersToReadAfterRelationAnnotation);
        CooccurrencesLayer layer = lex.getCooccurrencesLayer();
        Assert.assertEquals(2, layer.size());
        Assert.assertEquals(CooccurrenceFunction.sentence, layer.getCooccurrence(0).getFunction());
        assertEquals("essen", layer.getTermsAsStrings(layer.getCooccurrence(0), true)[0]);
        assertEquals("Banana", layer.getTermsAsStrings(layer.getCooccurrence(0), true)[1]);
        assertEquals("Banana", layer.getTermsAsStrings(layer.getCooccurrence(0), false)[0]);
        
        Cooccurrence[] coocs = layer.getCooccurrences(lex.getEntriesLayer().getEntry(0));
        Cooccurrence[] coocsExpected = new Cooccurrence[0];
        Assert.assertArrayEquals(coocsExpected, coocs);
        
        coocs = layer.getCooccurrences(lex.getEntriesLayer().getEntry(1));
        coocsExpected = layer.getCooccurrences(lex.getEntriesLayer().getEntry(1));
        Assert.assertArrayEquals(coocsExpected, coocs);
    }

    @Test
    public void testReadWrite() throws Exception {
        String outfile = testFolder.getRoot() + File.separator + OUTPUT_FILE;
        LexiconStreamed lex = open(INPUT_FILE_WITHOUT_LAYER, outfile, layersToReadBeforeRelationAnnotation);
        System.out.println(lex);
        // get lemmas layer
        EntriesLayer entries = lex.getEntriesLayer();
        // create relations layer, it's empty at first
        CooccurrencesLayer coocs = lex.createCooccurrencesLayer();
        annotateWithCooccurrences(entries, coocs);
        // IMPORTANT close the streams!!!
        lex.close();
        System.out.println(lex);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, outfile);
    }

    private void annotateWithCooccurrences(EntriesLayer entries, CooccurrencesLayer coocs) {
        List<Term> terms = new ArrayList<Term>();
        terms.add(coocs.createTerm(entries.getEntry(1)));
        terms.add(coocs.createTerm("Banana"));
        coocs.addCooccurrence(CooccurrenceFunction.sentence, coocs.createSig(null, (float) 2.7), terms);
        terms = new ArrayList<Term>();
        terms.add(coocs.createTerm(entries.getEntry(6)));
        terms.add(coocs.createTerm("Brot"));
        coocs.addCooccurrence(CooccurrenceFunction.left_neighbour, coocs.createSig(null, (float) 2.0), terms);
    }
}
