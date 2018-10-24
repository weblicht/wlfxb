/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.clarin.weblicht.wlfxb.tc.test_v5;

import eu.clarin.weblicht.wlfxb.io.TextCorpusStreamed;
import eu.clarin.weblicht.wlfxb.tc.api.TextCorpus;
import eu.clarin.weblicht.wlfxb.tc.api.Token;
import eu.clarin.weblicht.wlfxb.tc.test.AbstractTextCorpusTest;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import java.io.File;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import eu.clarin.weblicht.wlfxb.tc.api.ChunksLayer;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public class TextCorpusChunkLayerTest extends AbstractTextCorpusTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data_v5/tc-chunk/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER_TAGSET = "/data_v5/tc-chunk/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE_TAGSET = "/data_v5/tc-chunk/output-expected.xml";
    private static final String OUTPUT_FILE = "output.xml";

    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeChunkLayer
            = EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterChunkLayer
            = EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.CHUNKS);
    public static final Map<String, LinkedHashMap<String, String>> token2CH = new HashMap<String, LinkedHashMap<String, String>>();
    public static final Map<String, String> multiword = new HashMap<String, String>();

    static {
        LinkedHashMap<String, String> types = new LinkedHashMap<String, String>();
        types.put("type", "NP");
        token2CH.put("He", types);
        types = new LinkedHashMap<String, String>();
        types.put("type", "VP");
        types.put("voice", "none");
        types.put("tense", "present");
        token2CH.put("reckons", types);
        types = new LinkedHashMap<String, String>();
        types.put("type", "VP");
        types.put("voice", "none");
        types.put("tense", "present");
        token2CH.put("reckons", types);
    }

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER_TAGSET, layersToReadAfterChunkLayer);
        ChunksLayer layer = tc.getChunksLayer();
        Assert.assertEquals(1, layer.size());
        Assert.assertEquals("tagset", layer.getTagset());
        Assert.assertEquals(tc.getTokensLayer().getToken(0), layer.getTokens(layer.getChunk(0))[0]);
    }

    @Test
    public void testReadWrite() throws Exception {
        String outfile = testFolder.getRoot() + File.separator + OUTPUT_FILE;
        TextCorpusStreamed tc = open(INPUT_FILE_WITHOUT_LAYER, outfile, layersToReadBeforeChunkLayer);
        // create chunk layer, it's empty at first
        ChunksLayer layer = tc.createChunksLayer("tagset");
        for (int i = 0; i < tc.getTokensLayer().size(); i++) {
            Token token = tc.getTokensLayer().getToken(i);
            LinkedHashMap<String, String> chunkAttributes = recognize(token.getString());

            if (chunkAttributes != null) {
                layer.addChunk(chunkAttributes, token);
            }
        }
        // IMPORTANT close the streams!!!
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE_TAGSET, outfile);
    }

    private LinkedHashMap<String, String> recognize(String tokenString) {
        return token2CH.get(tokenString);
    }
}
