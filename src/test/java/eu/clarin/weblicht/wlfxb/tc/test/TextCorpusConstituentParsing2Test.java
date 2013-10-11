/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.test;

import eu.clarin.weblicht.wlfxb.io.TextCorpusStreamed;
import eu.clarin.weblicht.wlfxb.tc.api.*;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import java.util.Arrays;
import java.util.EnumSet;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class TextCorpusConstituentParsing2Test extends AbstractTextCorpusTest {

    private static final String INPUT_FILE_WITH_PARSING = "/data/tc-parsing/tcf-empty-terminal.xml";
    //private static final String INPUT_FILE_WITH_PARSING = "/data/tc-parsing/tcf-parser-noid.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterParsing =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.SENTENCES, TextCorpusLayerTag.PARSING_CONSTITUENT);

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_PARSING, layersToReadAfterParsing);
        ConstituentParsingLayer parsingLayer = tc.getConstituentParsingLayer();
        Assert.assertEquals(27, parsingLayer.getTokens(parsingLayer.getParseRoot(0)).length);
    }

    
}
