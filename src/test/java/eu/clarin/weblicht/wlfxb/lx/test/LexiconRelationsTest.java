/**
 *
 */
package eu.clarin.weblicht.wlfxb.lx.test;

import eu.clarin.weblicht.wlfxb.io.LexiconStreamed;
import eu.clarin.weblicht.wlfxb.lx.api.Lemma;
import eu.clarin.weblicht.wlfxb.lx.api.LemmasLayer;
import eu.clarin.weblicht.wlfxb.lx.api.Lexicon;
import eu.clarin.weblicht.wlfxb.lx.api.Relation;
import eu.clarin.weblicht.wlfxb.lx.api.RelationsLayer;
import eu.clarin.weblicht.wlfxb.lx.api.Term;
import eu.clarin.weblicht.wlfxb.lx.xb.LexiconLayerTag;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class LexiconRelationsTest extends AbstractLexiconTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/lx-rel/lx-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/lx-rel/lx-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/lx-rel/output-expected.xml";
    private static final String OUTPUT_FILE = "/tmp/output.xml";
    private static final EnumSet<LexiconLayerTag> layersToReadBeforeRelationAnnotation =
            EnumSet.of(LexiconLayerTag.LEMMAS);
    private static final EnumSet<LexiconLayerTag> layersToReadAfterRelationAnnotation =
            EnumSet.of(LexiconLayerTag.LEMMAS, LexiconLayerTag.RELATIONS);

    @Test
    public void testRead() throws Exception {
        Lexicon lex = read(INPUT_FILE_WITH_LAYER, layersToReadAfterRelationAnnotation);
        RelationsLayer layer = lex.getRelationsLayer();
        Assert.assertEquals(3, layer.size());
        Assert.assertEquals("collocation", layer.getRelation(0).getType());
        Assert.assertArrayEquals(new String[]{"steife", "Brise"}, layer.getWords(layer.getRelation(0)));
        Assert.assertArrayEquals(new String[]{"essen", "Frühstück"}, layer.getWords(layer.getRelation(2)));
        
        Relation[] relations = layer.getRelations(lex.getLemmasLayer().getLemma(0));
        Relation[] relationsExpected = new Relation[0];
        Assert.assertArrayEquals(relationsExpected, relations);
    }

    @Test
    public void testReadWrite() throws Exception {
        LexiconStreamed lex = open(INPUT_FILE_WITHOUT_LAYER, OUTPUT_FILE, layersToReadBeforeRelationAnnotation);
        System.out.println(lex);
        // get lemmas layer
        LemmasLayer lemmas = lex.getLemmasLayer();
        // create relations layer, it's empty at first
        RelationsLayer rels = lex.createRelationsLayer();
        annotateWithRelations(lemmas, rels);
        // IMPORTANT close the streams!!!
        lex.close();
        System.out.println(lex);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    private void annotateWithRelations(LemmasLayer lemmas, RelationsLayer rels) {
        List<Term> terms = new ArrayList<Term>();
        terms.add(rels.createTerm("steife"));
        terms.add(rels.createTerm("Brise"));
        rels.addRelation("collocation", "Adj-Noun", 100, rels.createSig("smt", 3.1), terms);
        terms = new ArrayList<Term>();
        terms.add(rels.createTerm(lemmas.getLemma(1)));
        terms.add(rels.createTerm(lemmas.getLemma(3)));
        rels.addRelation("collocation", "Verb-Object", 60, rels.createSig(null, 2.7), terms);
        terms = new ArrayList<Term>();
        terms.add(rels.createTerm(lemmas.getLemma(1)));
        terms.add(rels.createTerm("Frühstück"));
        rels.addRelation("collocation", "Verb-Object", 50, rels.createSig(null, 2.7), terms);
    }
}
