/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.lx.test;

import de.tuebingen.uni.sfs.wlf1.io.LexiconStreamed;
import de.tuebingen.uni.sfs.wlf1.lx.api.LemmasLayer;
import de.tuebingen.uni.sfs.wlf1.lx.api.Lexicon;
import de.tuebingen.uni.sfs.wlf1.lx.api.RelationsLayer;
import de.tuebingen.uni.sfs.wlf1.lx.api.Term;
import de.tuebingen.uni.sfs.wlf1.lx.xb.LexiconLayerTag;
import de.tuebingen.uni.sfs.wlf1.test.utils.TestUtils;
import java.io.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class LexiconRelationsTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "data/lx-rel/lx-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "data/lx-rel/lx-after.xml";
    private static final String OUTPUT_FILE = "data/lx-rel/output.xml";
    private static final String EXPECTED_OUTPUT_FILE = "data/lx-rel/output-expected.xml";
    private static final EnumSet<LexiconLayerTag> layersToReadBeforeRelationAnnotation =
            EnumSet.of(LexiconLayerTag.LEMMAS);
    private static final EnumSet<LexiconLayerTag> layersToReadAfterRelationAnnotation =
            EnumSet.of(LexiconLayerTag.LEMMAS, LexiconLayerTag.RELATIONS);

    @Test
    public void testRead() throws Exception {
        Lexicon lex = read(INPUT_FILE_WITH_LAYER, layersToReadAfterRelationAnnotation);
        RelationsLayer layer = lex.getRelationsLayer();
        assertEquals(3, layer.size());
        assertEquals("collocation", layer.getRelation(0).getType());
        Assert.assertArrayEquals(new String[]{"steife", "Brise"}, layer.getWords(layer.getRelation(0)));
        Assert.assertArrayEquals(new String[]{"essen", "Frühstück"}, layer.getWords(layer.getRelation(2)));
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
        LexiconStreamed lex = new LexiconStreamed(is, layersToReadBeforeRelationAnnotation, os, false);
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
        TestUtils.assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
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
