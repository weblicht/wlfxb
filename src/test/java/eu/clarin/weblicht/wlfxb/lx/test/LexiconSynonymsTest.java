/**
 *
 */
package eu.clarin.weblicht.wlfxb.lx.test;

import eu.clarin.weblicht.wlfxb.io.LexiconStreamed;
import eu.clarin.weblicht.wlfxb.lx.api.Synonym;
import eu.clarin.weblicht.wlfxb.lx.api.Lexicon;
import eu.clarin.weblicht.wlfxb.lx.api.SynonymsLayer;
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
public class LexiconSynonymsTest extends AbstractLexiconTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/lx-syns/lx-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/lx-syns/lx-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/lx-syns/output-expected.xml";
    private static final String OUTPUT_FILE = "output.xml";
    private static final EnumSet<LexiconLayerTag> layersToReadBeforeRelationAnnotation =
            EnumSet.of(LexiconLayerTag.ENTRIES);
    private static final EnumSet<LexiconLayerTag> layersToReadAfterRelationAnnotation =
            EnumSet.of(LexiconLayerTag.ENTRIES, LexiconLayerTag.SYNONYMS);

    @Test
    public void testRead() throws Exception {
        Lexicon lex = read(INPUT_FILE_WITH_LAYER, layersToReadAfterRelationAnnotation);
        SynonymsLayer layer = lex.getSynonymsLayer();
        Assert.assertEquals(2, layer.size());
        assertEquals("essen", layer.getTermsAsStrings(layer.getSynonym(0), true)[0]);
        assertEquals("sich ernähren", layer.getTermsAsStrings(layer.getSynonym(0), true)[1]);
        assertEquals("sich ernähren", layer.getTermsAsStrings(layer.getSynonym(0), false)[0]);
        
        Synonym[] syns = layer.getSynonyms(lex.getEntriesLayer().getEntry(0));
        Synonym[] synsExpected = new Synonym[0];
        Assert.assertArrayEquals(synsExpected, syns);
        
        syns = layer.getSynonyms(lex.getEntriesLayer().getEntry(1));
        synsExpected = layer.getSynonyms(lex.getEntriesLayer().getEntry(1));
        Assert.assertArrayEquals(synsExpected, syns);
    }

    @Test
    public void testReadWrite() throws Exception {
        String outfile = testFolder.getRoot() + File.separator + OUTPUT_FILE;
        LexiconStreamed lex = open(INPUT_FILE_WITHOUT_LAYER, outfile, layersToReadBeforeRelationAnnotation);
        System.out.println(lex);
        // get lemmas layer
        EntriesLayer entries = lex.getEntriesLayer();
        // create relations layer, it's empty at first
        SynonymsLayer syns = lex.createSynonymsLayer();
        annotateWithSynonyms(entries, syns);
        // IMPORTANT close the streams!!!
        lex.close();
        System.out.println(lex);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, outfile);
    }

    private void annotateWithSynonyms(EntriesLayer entries, SynonymsLayer syns) {
        List<Term> terms = new ArrayList<Term>();
        terms.add(syns.createTerm(entries.getEntry(1)));
        terms.add(syns.createTerm("sich ernähren"));
        terms.add(syns.createTerm("speisen"));
        terms.add(syns.createTerm("aufzehren"));
        syns.addSynonym(syns.createSig(null, (float) 2.7), terms);
        terms = new ArrayList<Term>();
        terms.add(syns.createTerm(entries.getEntry(1)));
        terms.add(syns.createTerm("schlucken"));
        terms.add(syns.createTerm("bezwingen"));
        terms.add(syns.createTerm("hinunterschlingen"));
        syns.addSynonym(syns.createSig(null, (float) 2.0), terms);
    }
}
