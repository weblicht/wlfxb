/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.test;

import de.tuebingen.uni.sfs.wlf1.io.WLDObjector;
import de.tuebingen.uni.sfs.wlf1.md.xb.MetaData;
import de.tuebingen.uni.sfs.wlf1.tc.api.TextCorpus;
import de.tuebingen.uni.sfs.wlf1.tc.api.TextSpanType;
import de.tuebingen.uni.sfs.wlf1.tc.api.TextStructureLayer;
import de.tuebingen.uni.sfs.wlf1.tc.api.TokensLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.TextCorpusLayerTag;
import de.tuebingen.uni.sfs.wlf1.tc.xb.TextCorpusStored;
import java.io.File;
import java.util.EnumSet;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class TextCorpusTextStructureTest extends AbstractTextCorpusTest {

    private static final String INPUT_FILE_WITH_LAYER = "/data/tc-textstruct/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/tc-textstruct/output-expected.xml";
    private static final String OUTPUT_FILE = "/tmp/output.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterTextStructureAnnotation =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.TEXT_STRUCTURE);
    private String[] tokenstrings = new String[]{"Peter", "aß", "eine", "Käsepizza", ".", "Sie", "schmeckte", "ihm", "."};

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterTextStructureAnnotation);
        TextStructureLayer layer = tc.getTextStructureLayer();

        Assert.assertEquals(9, layer.size());
        Assert.assertEquals(TextSpanType.page, layer.getSpan(0).getType());
        Assert.assertEquals(TextSpanType.line, layer.getSpan(1).getType());
        Assert.assertEquals(TextSpanType.paragraph, layer.getSpan(2).getType());

        Assert.assertEquals(tc.getTokensLayer().getToken(0), layer.getTokens(layer.getSpan(3))[0]);
        Assert.assertEquals(tc.getTokensLayer().getToken(1), layer.getTokens(layer.getSpan(3))[1]);
        Assert.assertEquals(tc.getTokensLayer().getToken(2), layer.getTokens(layer.getSpan(3))[2]);
    }

    @Test
    public void testReadWrite() throws Exception {

        TextCorpusStored tc = new TextCorpusStored("de");
        MetaData md = new MetaData();
        //WLData data = new WLData(tc);

        File ofile = new File(OUTPUT_FILE);

        TokensLayer tokens = tc.createTokensLayer();
        for (String tokenString : tokenstrings) {
            tokens.addToken(tokenString);
        }
        TextStructureLayer textstructure = tc.createTextStructureLayer();
        textstructure.addSpan(tokens.getToken(0), tokens.getToken(4), TextSpanType.page);
        textstructure.addSpan(null, null, TextSpanType.line);
        textstructure.addSpan(tokens.getToken(0), tokens.getToken(8), TextSpanType.paragraph);
        textstructure.addSpan(tokens.getToken(0), tokens.getToken(2), TextSpanType.line);
        textstructure.addSpan(tokens.getToken(3), tokens.getToken(4), TextSpanType.line);
        textstructure.addSpan(tokens.getToken(5), tokens.getToken(8), TextSpanType.page);
        textstructure.addSpan(tokens.getToken(5), tokens.getToken(6), TextSpanType.line);
        textstructure.addSpan(tokens.getToken(7), tokens.getToken(8), TextSpanType.line);
        textstructure.addSpan(null, null, TextSpanType.line);

        WLDObjector.write(md, tc, ofile, false);

        System.out.println(tc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }
}
