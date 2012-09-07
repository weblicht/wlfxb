/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.test;

import de.tuebingen.uni.sfs.wlf1.io.TextCorpusStreamed;
import de.tuebingen.uni.sfs.wlf1.tc.api.RelationsLayer;
import de.tuebingen.uni.sfs.wlf1.tc.api.TextCorpus;
import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.tc.xb.TextCorpusLayerTag;
import de.tuebingen.uni.sfs.wlf1.test.utils.TestUtils;
import java.io.*;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
@SuppressWarnings("deprecation")
public class TextCorpusRelationsTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "data/tc-rels/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "data/tc-rels/tcf-after.xml";
    private static final String OUTPUT_FILE = "data/tc-rels/output.xml";
    private static final String EXPECTED_OUTPUT_FILE = "data/tc-rels/output-expected.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeRelTagging =
            EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterRelTagging =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.RELATIONS);
    public static Map<String, String> token2ann = new HashMap<String, String>();

    static {
        token2ann.put("Peter", "subj");
        token2ann.put("Käsepizza", "obj");
        token2ann.put("Sie", "subj");
        token2ann.put("ihm", "obj");
    }

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterRelTagging);
        RelationsLayer layer = tc.getRelationsLayer();
        assertEquals(4, layer.size());
        assertEquals("subj", layer.getRelation(0).getFunction());
        assertEquals(tc.getTokensLayer().getToken(0), layer.getTokens(layer.getRelation(0))[0]);
    }

    private TextCorpus read(String file, EnumSet<TextCorpusLayerTag> layersToRead) throws Exception {
        InputStream is = new FileInputStream(INPUT_FILE_WITH_LAYER);
        TextCorpusStreamed tc = new TextCorpusStreamed(is, layersToRead);
        tc.close();
        System.out.println(tc);
        return tc;
    }

    @Test
    public void testReadWrite() throws Exception {
        File file = new File(INPUT_FILE_WITHOUT_LAYER);
        InputStream is = new FileInputStream(file);
        File ofile = new File(OUTPUT_FILE);
        OutputStream os = new FileOutputStream(ofile);
        TextCorpusStreamed tc = new TextCorpusStreamed(is, layersToReadBeforeRelTagging, os, false);
        System.out.println(tc);
        // create relations layer, it's empty at first
        RelationsLayer rels = tc.createRelationsLayer("verb-arg");
        for (int i = 0; i < tc.getTokensLayer().size(); i++) {
            Token token = tc.getTokensLayer().getToken(i);
            String relFuncTag = tag(token.getString());
            if (relFuncTag != null) {
                // create and add relation tag to the layer
                rels.addRelation(relFuncTag, token);
            }
        }
        // IMPORTANT close the streams!!!
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        TestUtils.assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    private String tag(String tokenString) {
        return token2ann.get(tokenString);
    }
}
