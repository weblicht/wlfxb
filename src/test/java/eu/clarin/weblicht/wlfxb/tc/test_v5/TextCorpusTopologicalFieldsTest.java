/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.test_v5;

import eu.clarin.weblicht.wlfxb.io.TextCorpusStreamed;
import eu.clarin.weblicht.wlfxb.tc.api.TextCorpus;
import eu.clarin.weblicht.wlfxb.tc.api.Token;
import eu.clarin.weblicht.wlfxb.tc.api.TopologicalFieldsLayer;
import eu.clarin.weblicht.wlfxb.tc.test.AbstractTextCorpusTest;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import java.io.File;

/**
 * @author Neele Witte
 *
 */
public class TextCorpusTopologicalFieldsTest extends AbstractTextCorpusTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data_v5/tc-topo/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data_v5/tc-topo/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data_v5/tc-topo/output-expected.xml";
    private static final String OUTPUT_FILE = "output.xml";

    
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeTopoTagging =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.POSTAGS);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterTopoTagging =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.POSTAGS, TextCorpusLayerTag.TOPOLOGICAL_FIELDS);
    public static final Map<String, String> token2Topo = new HashMap<String, String>();

    static {
    	token2Topo.put("Peter", "VF");
    	token2Topo.put("aß", "LK");
    	token2Topo.put("eine", "MF");
    	token2Topo.put("Käsepizza", "MF");
    	token2Topo.put(".", "UNK");
    	token2Topo.put("Sie", "VF");
    	token2Topo.put("schmeckte", "LK");
    	token2Topo.put("ihm", "MF");
    	token2Topo.put(".", "UNK");
    }

    /**
     * Checks if the new topological field layer can be read from TCF, if it has the expected size and if the first
     * and the last topological field match the expected tag
     * @throws Exception
     */
    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterTopoTagging);
        TopologicalFieldsLayer layer = tc.getTopologicalFieldsLayer();
        Assert.assertEquals(9, layer.size());
        Assert.assertEquals("VF", layer.getTag(0).getString());
        Assert.assertEquals(tc.getTokensLayer().getToken(0), layer.getTokens(layer.getTag(0))[0]);
    }

    /**
     * Checks if a TCF can be read and a the new topological field layer can be added. The layer specifies a tagset
     * and adds a topological field for each token. Checks if the new XML file created with the library matches the
     * expected XML file.
     * @throws Exception
     */
    @Test
    public void testReadWrite() throws Exception {
        String outfile = testFolder.getRoot() + File.separator + OUTPUT_FILE;
        TextCorpusStreamed tc = open(INPUT_FILE_WITHOUT_LAYER, outfile, layersToReadBeforeTopoTagging);
        TopologicalFieldsLayer tags = tc.createTopologicalFieldsLayer("DANIELDK");
        for (int i = 0; i < tc.getTokensLayer().size(); i++) {
            Token token = tc.getTokensLayer().getToken(i);
            String topoTag = tag(token.getString());
            tags.addTag(topoTag, token);
        }
        // IMPORTANT close the streams!!!
        tc.close();
        assertEqualXml(EXPECTED_OUTPUT_FILE, outfile);
    }

    private String tag(String tokenString) {
        return token2Topo.get(tokenString);
    }
}
