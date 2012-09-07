/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.test;

import de.tuebingen.uni.sfs.wlf1.io.TextCorpusStreamed;
import de.tuebingen.uni.sfs.wlf1.tc.api.NamedEntitiesLayer;
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
public class TextCorpusNamedEntitiesTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "data/tc-nes/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "data/tc-nes/tcf-after.xml";
    private static final String OUTPUT_FILE = "data/tc-nes/output.xml";
    private static final String EXPECTED_OUTPUT_FILE = "data/tc-nes/output-expected.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeNamedEntityRecognition =
            EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterNamedEntityRecognition =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.NAMED_ENTITIES);
    public static Map<String, String> token2ne = new HashMap<String, String>();

    static {
        token2ne.put("Peter", "PERSON");
    }

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterNamedEntityRecognition);
        NamedEntitiesLayer layer = tc.getNamedEntitiesLayer();
        assertEquals(1, layer.size());
        assertEquals("PERSON", layer.getEntity(0).getType());
        assertEquals(tc.getTokensLayer().getToken(0), layer.getTokens(layer.getEntity(0))[0]);
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
        TextCorpusStreamed tc = new TextCorpusStreamed(is, layersToReadBeforeNamedEntityRecognition, os, false);
        System.out.println(tc);
        // create named entities layer, it's empty at first
        NamedEntitiesLayer nes = tc.createNamedEntitiesLayer("MUC1990");
        for (int i = 0; i < tc.getTokensLayer().size(); i++) {
            Token token = tc.getTokensLayer().getToken(i);
            String neType = recognize(token.getString());
            if (neType != null) {
                // create and add part-of-speech tag to the tags layer
                nes.addEntity(neType, token);
            }
        }
        // IMPORTANT close the streams!!!
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        TestUtils.assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    private String recognize(String tokenString) {
        return token2ne.get(tokenString);
    }
}
