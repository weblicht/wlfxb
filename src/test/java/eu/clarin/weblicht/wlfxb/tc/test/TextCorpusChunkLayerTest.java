/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.clarin.weblicht.wlfxb.tc.test;

import eu.clarin.weblicht.wlfxb.io.TextCorpusStreamed;
import eu.clarin.weblicht.wlfxb.tc.api.ChunkLayer;
import eu.clarin.weblicht.wlfxb.tc.api.TextCorpus;
import eu.clarin.weblicht.wlfxb.tc.api.Token;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import java.io.File;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author felahi
 */
public class TextCorpusChunkLayerTest extends AbstractTextCorpusTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/tc-chunk/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/tc-chunk/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/tc-chunk/output-expected.xml";
    private static final String OUTPUT_FILE = "output.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeChunkLayer
            = EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterChunkLayer
            = EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.CHUNKS);
    public static final Map<String, Map<String, String>> token2CH = new HashMap<String, Map<String, String>>();

    static {
        Map<String, String> types = new HashMap<String, String>();
        types.put("type", "NP");
        token2CH.put("He", types);
        types = new HashMap<String, String>();
        types.put("type", "VP");
        types.put("voice", "none");
        types.put("tense", "present");
        token2CH.put("reckons", types);
    }

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterChunkLayer);
        ChunkLayer layer = tc.getChunkLayer();
        Assert.assertEquals(1, layer.size());
        Assert.assertEquals("tagset", layer.getTagset());
        Assert.assertEquals(tc.getTokensLayer().getToken(0), layer.getTokens(layer.getChunk(0))[0]);
    }

    @Test
    public void testReadWrite() throws Exception {
        String outfile = testFolder.getRoot() + File.separator + OUTPUT_FILE;
        //String outfile = "/Users/felahi/repository/tcf_wlfxb/newwlfxb/wlfxb/src/test/resources/data/tc-chunk" + File.separator + OUTPUT_FILE;
        TextCorpusStreamed tc = open(INPUT_FILE_WITHOUT_LAYER, outfile, layersToReadBeforeChunkLayer);
        System.out.println(tc);
        // create chunk layer, it's empty at first
        ChunkLayer layer = tc.createChunkLayer("tagset");
        for (int i = 0; i < tc.getTokensLayer().size(); i++) {
            Token token = tc.getTokensLayer().getToken(i);
            Map<String, String> chType = recognize(token.getString());
            if (chType != null) {
                // create and add chunk to the chunk layer
                layer.addChunk(chType, token);
            }
        }
        // IMPORTANT close the streams!!!
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        //assertEqualXml(EXPECTED_OUTPUT_FILE, outfile);
    }

    private Map<String, String> recognize(String tokenString) {
        return token2CH.get(tokenString);
    }
}
