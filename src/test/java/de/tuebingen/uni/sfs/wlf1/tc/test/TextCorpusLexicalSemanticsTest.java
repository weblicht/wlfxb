/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.test;

import de.tuebingen.uni.sfs.wlf1.io.TextCorpusStreamed;
import de.tuebingen.uni.sfs.wlf1.tc.api.Lemma;
import de.tuebingen.uni.sfs.wlf1.tc.api.LexicalSemanticsLayer;
import de.tuebingen.uni.sfs.wlf1.tc.api.TextCorpus;
import de.tuebingen.uni.sfs.wlf1.tc.xb.TextCorpusLayerTag;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class TextCorpusLexicalSemanticsTest extends AbstractTextCorpusTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/tc-lexsem/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/tc-lexsem/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/tc-lexsem/output-expected.xml";
    private static final String OUTPUT_FILE = "/tmp/output.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeLexSemAnnotation =
            EnumSet.of(TextCorpusLayerTag.LEMMAS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterLexSemAnnotation =
            EnumSet.of(TextCorpusLayerTag.LEMMAS,
            TextCorpusLayerTag.SYNONYMY, TextCorpusLayerTag.ANTONYMY,
            TextCorpusLayerTag.HYPONYMY, TextCorpusLayerTag.HYPERONYMY);
    public static final Map<String, String[]> syno = new HashMap<String, String[]>();
    public static final Map<String, String[]> anto = new HashMap<String, String[]>();
    public static final Map<String, String[]> hypo = new HashMap<String, String[]>();
    public static final Map<String, String[]> hyper = new HashMap<String, String[]>();

    static {
        syno.put("essen", new String[]{"futtern", "nehmen"});
        anto.put("essen", new String[]{"verhungern"});
        hypo.put("essen", new String[]{"Art essen", "EssensOrt spezifiziert", "EssensZeit spezifiziert", "acheln", "aufessen", "aufnehmen", "einverleiben", "essen mit Instrument", "hermachen",
                    "stärken", "vertilgen", "wegessen"});
        hypo.put("schmecken", new String[]{"abschmecken", "gustieren", "kosten", "munden", "probieren",
                    "versuchen", "würzen"});
        hyper.put("essen", new String[]{"verzehren"});
        hyper.put("schmecken", new String[]{"Genuss essen", "gut tun", "guttun", "perzipieren", "wahrnehmen"});
    }

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterLexSemAnnotation);
        LexicalSemanticsLayer layer;

        layer = tc.getSynonymyLayer();
        Assert.assertEquals(1, layer.size());
        Assert.assertEquals("GermaNet", layer.getSource());
        Assert.assertEquals(tc.getLemmasLayer().getLemma(1), layer.getLemmas(layer.getOrthform(0))[0]);
        Assert.assertEquals(layer.getOrthform(0), layer.getOrthform(tc.getLemmasLayer().getLemma(1)));

        layer = tc.getAntonymyLayer();
        Assert.assertEquals(1, layer.size());
        Assert.assertEquals("GermaNet", layer.getSource());
        Assert.assertEquals(tc.getLemmasLayer().getLemma(1), layer.getLemmas(layer.getOrthform(0))[0]);
        Assert.assertEquals(layer.getOrthform(0), layer.getOrthform(tc.getLemmasLayer().getLemma(1)));

        layer = tc.getHyponymyLayer();
        Assert.assertEquals(2, layer.size());
        Assert.assertEquals("GermaNet", layer.getSource());
        Assert.assertEquals(tc.getLemmasLayer().getLemma(1), layer.getLemmas(layer.getOrthform(0))[0]);
        Assert.assertEquals(layer.getOrthform(0), layer.getOrthform(tc.getLemmasLayer().getLemma(1)));

        layer = tc.getHyperonymyLayer();
        Assert.assertEquals(2, layer.size());
        Assert.assertEquals("GermaNet", layer.getSource());
        Assert.assertEquals(tc.getLemmasLayer().getLemma(1), layer.getLemmas(layer.getOrthform(0))[0]);
        Assert.assertEquals(layer.getOrthform(0), layer.getOrthform(tc.getLemmasLayer().getLemma(1)));
    }

    @Test
    public void testReadWrite() throws Exception {
        TextCorpusStreamed tc = open(INPUT_FILE_WITHOUT_LAYER, OUTPUT_FILE, layersToReadBeforeLexSemAnnotation);
        System.out.println(tc);
        // create lexical semantics layers, empty at first
        String source = "GermaNet";
        LexicalSemanticsLayer synonymy = tc.createSynonymyLayer(source);
        LexicalSemanticsLayer antonymy = tc.createAntonymyLayer(source);
        LexicalSemanticsLayer hyponymy = tc.createHyponymyLayer(source);
        LexicalSemanticsLayer hyperonymy = tc.createHyperonymyLayer(source);
        for (int i = 0; i < tc.getLemmasLayer().size(); i++) {
            Lemma lemma = tc.getLemmasLayer().getLemma(i);
            String[] synonyms = getSynonyms(lemma.getString());
            if (synonyms != null) {
                // create and add synonyms to the lemma
                synonymy.addOrthform(synonyms, lemma);
            }
            String[] antonyms = getAntonyms(lemma.getString());
            if (antonyms != null) {
                // create and add antonyms to the lemma
                antonymy.addOrthform(antonyms, lemma);
            }
            String[] hyponyms = getHyponyms(lemma.getString());
            if (hyponyms != null) {
                // create and add hyponyms to the lemma
                hyponymy.addOrthform(hyponyms, lemma);
            }
            String[] hyperonyms = getHyperonyms(lemma.getString());
            if (hyperonyms != null) {
                // create and add hyperonyms to the lemma
                hyperonymy.addOrthform(hyperonyms, lemma);
            }
        }
        // IMPORTANT close the streams!!!
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    private String[] getSynonyms(String lemmaString) {
        return syno.get(lemmaString);
    }

    private String[] getAntonyms(String lemmaString) {
        return anto.get(lemmaString);
    }

    private String[] getHyponyms(String lemmaString) {
        return hypo.get(lemmaString);
    }

    private String[] getHyperonyms(String lemmaString) {
        return hyper.get(lemmaString);
    }
}
