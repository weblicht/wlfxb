/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.test;

import de.tuebingen.uni.sfs.wlf1.io.TextCorpusStreamed;
import de.tuebingen.uni.sfs.wlf1.tc.api.NamedEntitiesLayer;
import de.tuebingen.uni.sfs.wlf1.tc.api.TextCorpus;
import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
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
public class TextCorpusNamedEntitiesTest extends AbstractTextCorpusTest {

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/tc-nes/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/tc-nes/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/tc-nes/output-expected.xml";
    private static final String OUTPUT_FILE = "/tmp/output.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeNamedEntityRecognition =
            EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterNamedEntityRecognition =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.NAMED_ENTITIES);
    public static final Map<String, String> token2ne = new HashMap<String, String>();

    static {
        token2ne.put("Peter", "PERSON");
    }

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterNamedEntityRecognition);
        NamedEntitiesLayer layer = tc.getNamedEntitiesLayer();
        Assert.assertEquals(1, layer.size());
        Assert.assertEquals("PERSON", layer.getEntity(0).getType());
        Assert.assertEquals(tc.getTokensLayer().getToken(0), layer.getTokens(layer.getEntity(0))[0]);
    }

    @Test
    public void testReadWrite() throws Exception {
        TextCorpusStreamed tc = open(INPUT_FILE_WITHOUT_LAYER, OUTPUT_FILE, layersToReadBeforeNamedEntityRecognition);
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
        assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    private String recognize(String tokenString) {
        return token2ne.get(tokenString);
    }
}
