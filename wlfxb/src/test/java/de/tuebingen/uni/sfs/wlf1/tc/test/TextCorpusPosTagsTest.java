/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.test;

import de.tuebingen.uni.sfs.wlf1.io.TextCorpusStreamed;
import de.tuebingen.uni.sfs.wlf1.tc.api.PosTagsLayer;
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
public class TextCorpusPosTagsTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "data/tc-pos/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "data/tc-pos/tcf-after.xml";
    private static final String OUTPUT_FILE = "data/tc-pos/output.xml";
    private static final String EXPECTED_OUTPUT_FILE = "data/tc-pos/output-expected.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforePosTagging =
            EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterPosTagging =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.POSTAGS);
    public static Map<String, String> token2Pos = new HashMap<String, String>();

    static {
        token2Pos.put("Peter", "NE");
        token2Pos.put("aß", "VVFIN");
        token2Pos.put("eine", "ART");
        token2Pos.put("Käsepizza", "NE");
        token2Pos.put(".", "$.");
        token2Pos.put("Sie", "PPER");
        token2Pos.put("schmeckte", "VVFIN");
        token2Pos.put("ihm", "PPER");
    }

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterPosTagging);
        PosTagsLayer layer = tc.getPosTagsLayer();
        assertEquals(9, layer.size());
        assertEquals("NE", layer.getTag(0).getString());
        assertEquals(tc.getTokensLayer().getToken(0), layer.getTokens(layer.getTag(0))[0]);
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
        TextCorpusStreamed tc = new TextCorpusStreamed(is, layersToReadBeforePosTagging, os, false);
        System.out.println(tc);
        // create part of speech layer, it's empty at first
        PosTagsLayer tags = tc.createPosTagsLayer("STTS");
        for (int i = 0; i < tc.getTokensLayer().size(); i++) {
            Token token = tc.getTokensLayer().getToken(i);
            String posTag = tag(token.getString());
            // create and add part-of-speech tag to the tags layer
            tags.addTag(posTag, token);
        }
        // IMPORTANT close the streams!!!
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        TestUtils.assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    private String tag(String tokenString) {
        return token2Pos.get(tokenString);
    }
}
