/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.test;

import eu.clarin.weblicht.wlfxb.io.WLDObjector;
import eu.clarin.weblicht.wlfxb.md.xb.MetaData;
import eu.clarin.weblicht.wlfxb.tc.api.TextCorpus;
import eu.clarin.weblicht.wlfxb.tc.api.TextSpan;
import eu.clarin.weblicht.wlfxb.tc.api.TextSpanType;
import eu.clarin.weblicht.wlfxb.tc.api.TextStructureLayer;
import eu.clarin.weblicht.wlfxb.tc.api.Token;
import eu.clarin.weblicht.wlfxb.tc.api.TokensLayer;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusStored;
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

//        Assert.assertEquals(9, layer.size());
//        Assert.assertEquals(TextSpanType.page, layer.getSpan(0).getType());
//        Assert.assertEquals(TextSpanType.line, layer.getSpan(1).getType());
//        Assert.assertEquals(TextSpanType.paragraph, layer.getSpan(2).getType());
//
//        Assert.assertEquals(tc.getTokensLayer().getToken(0), layer.getTokens(layer.getSpan(3))[0]);
//        Assert.assertEquals(tc.getTokensLayer().getToken(1), layer.getTokens(layer.getSpan(3))[1]);
//        Assert.assertEquals(tc.getTokensLayer().getToken(2), layer.getTokens(layer.getSpan(3))[2]);
        
        Assert.assertEquals(9, layer.size());
        Assert.assertEquals("page", layer.getSpan(0).getType());
        Assert.assertEquals("number", layer.getSpan(0).getSubspans()[0].getType());
        Assert.assertEquals("1", layer.getSpan(0).getSubspans()[0].getValue());
        Assert.assertEquals("line", layer.getSpan(1).getType());
        Assert.assertEquals("paragraph", layer.getSpan(2).getType());

        Assert.assertEquals(tc.getTokensLayer().getToken(0), layer.getTokens(layer.getSpan(3))[0]);
        Assert.assertEquals((new Token[0]).length, layer.getTokens(layer.getSpan(0).getSubspans()[0]).length);
        Assert.assertEquals(tc.getTokensLayer().getToken(1), layer.getTokens(layer.getSpan(3))[1]);
        Assert.assertEquals(tc.getTokensLayer().getToken(2), layer.getTokens(layer.getSpan(3))[2]);
    }

//    @Test
//    public void testReadWrite() throws Exception {
//
//        TextCorpusStored tc = new TextCorpusStored("de");
//        MetaData md = new MetaData();
//        //WLData data = new WLData(tc);
//
//        File ofile = new File(OUTPUT_FILE);
//
//        TokensLayer tokens = tc.createTokensLayer();
//        for (String tokenString : tokenstrings) {
//            tokens.addToken(tokenString);
//        }
//        TextStructureLayer textstructure = tc.createTextStructureLayer();
//        textstructure.addSpan(tokens.getToken(0), tokens.getToken(4), TextSpanType.page);
//        textstructure.addSpan(null, null, TextSpanType.line);
//        textstructure.addSpan(tokens.getToken(0), tokens.getToken(8), TextSpanType.paragraph);
//        textstructure.addSpan(tokens.getToken(0), tokens.getToken(2), TextSpanType.line);
//        textstructure.addSpan(tokens.getToken(3), tokens.getToken(4), TextSpanType.line);
//        textstructure.addSpan(tokens.getToken(5), tokens.getToken(8), TextSpanType.page);
//        textstructure.addSpan(tokens.getToken(5), tokens.getToken(6), TextSpanType.line);
//        textstructure.addSpan(tokens.getToken(7), tokens.getToken(8), TextSpanType.line);
//        textstructure.addSpan(null, null, TextSpanType.line);
//
//        WLDObjector.write(md, tc, ofile, false);
//
//        System.out.println(tc);
//        // compare output xml with expected xml
//        assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
//    }
    
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
        TextSpan parentSpan = textstructure.addSpan(tokens.getToken(0), tokens.getToken(4), "page");
        
        textstructure.addSpan(parentSpan, null, null, "number", "1");
        
        textstructure.addSpan(null, null, "line");
        textstructure.addSpan(tokens.getToken(0), tokens.getToken(8), "paragraph");
        textstructure.addSpan(tokens.getToken(0), tokens.getToken(2), "line");
        textstructure.addSpan(tokens.getToken(3), tokens.getToken(4), "line");
        textstructure.addSpan(tokens.getToken(5), tokens.getToken(8), "page");
        textstructure.addSpan(tokens.getToken(5), tokens.getToken(6), "line");
        textstructure.addSpan(tokens.getToken(7), tokens.getToken(8), "line");
        textstructure.addSpan(null, null, "line");

        WLDObjector.write(md, tc, ofile, false);

        System.out.println(tc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }
}
