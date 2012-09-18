/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.test;

import de.tuebingen.uni.sfs.wlf1.io.TextCorpusStreamed;
import de.tuebingen.uni.sfs.wlf1.tc.api.*;
import de.tuebingen.uni.sfs.wlf1.tc.xb.TextCorpusLayerTag;
import java.util.Arrays;
import java.util.EnumSet;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class TextCorpusConstituentParsingTest extends AbstractTextCorpusTest {

    private static final String INPUT_FILE_WITHOUT_PARSING = "/data/tc-parsing/tcf-before.xml";
    private static final String INPUT_FILE_WITH_PARSING = "/data/tc-parsing/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/tc-parsing/output-expected.xml";
    private static final String OUTPUT_FILE = "/tmp/output.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeParsing =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.SENTENCES);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterParsing =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.SENTENCES, TextCorpusLayerTag.PARSING_CONSTITUENT);

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_PARSING, layersToReadAfterParsing);
        ConstituentParsingLayer parsingLayer = tc.getConstituentParsingLayer();
        Assert.assertEquals("Tiger", parsingLayer.getTagset());
        Assert.assertEquals("TOP", parsingLayer.getParseRoot(0).getCategory());
        Assert.assertEquals("TOP", parsingLayer.getParseRoot(1).getCategory());
        Assert.assertEquals(5, parsingLayer.getTokens(parsingLayer.getParse(0)).length);
        Assert.assertEquals(4, parsingLayer.getTokens(parsingLayer.getParse(1)).length);
        Assert.assertEquals(tc.getTokensLayer().getToken(0), parsingLayer.getTokens(parsingLayer.getParse(0))[0]);
        Assert.assertEquals(tc.getTokensLayer().getToken(5), parsingLayer.getTokens(parsingLayer.getParse(1))[0]);
    }

    @Test
    public void testReadWrite() throws Exception {
        TextCorpusStreamed tc = open(INPUT_FILE_WITHOUT_PARSING, OUTPUT_FILE, layersToReadBeforeParsing);
        System.out.println(tc);
        SentencesLayer sentences = tc.getSentencesLayer();
        ConstituentParsingLayer parses = tc.createConstituentParsingLayer("Tiger");
        for (int i = 0; i < sentences.size(); i++) {
            Token[] sentenceTokens = sentences.getTokens(sentences.getSentence(i));
            // creates test parse for the test tokens
            Constituent root = parse(sentenceTokens, parses);
            parses.addParse(root);
        }

        ConstituentReference cref = parses.getParseRoot(0).getChildren()[0].getChildren()[2].getSecondaryEdgeChildren()[0];
        Assert.assertEquals(parses.getParseRoot(0).getChildren()[0].getChildren()[0], parses.getConstituent(cref));

        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    private Constituent parse(Token[] sentenceTokens, ConstituentParsingLayer parses) {
        if (sentenceTokens[0].getString().equals("Peter")) {
            return parseFirstTestSentence(sentenceTokens, parses);
        } else {
            return parseSecondTestSentence(sentenceTokens, parses);
        }
    }

    private Constituent parseSecondTestSentence(Token[] sentenceTokens,
            ConstituentParsingLayer parses) {
//		   <parse>
//		    <constituent ID="c11" cat="TOP">
//		     <constituent ID="c12" cat="S-TOP">
//		      <constituent ID="c13" cat="NP-SB">
//		       <constituent cat="PPER-HD-Nom" ID="c14" tokenIDs="t6"/>
//		      </constituent>
//		      <constituent cat="VVFIN-HD" ID="c15" tokenIDs="t7"/>
//		      <constituent ID="c16" cat="NP-DA">
//		       <constituent cat="PPER-HD-Dat" ID="c17" tokenIDs="t8"/>
//		      </constituent>
//		     </constituent>
//		     <constituent cat="\$." ID="c18" tokenIDs="t9"/>
//		    </constituent>
//		   </parse>

        Constituent c14 = parses.createTerminalConstituent("PPER-HD-Nom", sentenceTokens[0]);
        Constituent c13 = parses.createConstituent("NP-SB", Arrays.asList(new Constituent[]{c14}));

        Constituent c15 = parses.createTerminalConstituent("VVFIN-HD", sentenceTokens[1]);

        Constituent c17 = parses.createTerminalConstituent("PPER-HD-Dat", sentenceTokens[2]);
        Constituent c16 = parses.createConstituent("NP-DA", Arrays.asList(new Constituent[]{c17}));

        Constituent c12 = parses.createConstituent("S-TOP", Arrays.asList(new Constituent[]{c13, c15, c16}));

        Constituent c18 = parses.createTerminalConstituent("\\$.", sentenceTokens[3]);

        Constituent c11 = parses.createConstituent("TOP", Arrays.asList(new Constituent[]{c12, c18}));

        return c11;

    }

    private Constituent parseFirstTestSentence(Token[] sentenceTokens,
            ConstituentParsingLayer parses) {
//		<parse>
//	    <constituent ID="c1" cat="TOP">
//	     <constituent ID="c2" cat="S-TOP">
//	      <constituent ID="c3" cat="NP-SB">
//	       <constituent ID="c4" cat="PN-HD-Nom.Sg">
//	        <constituent ID="c5" cat="NE-HD-Nom.Sg" tokenIDs="t1"/>
//	       </constituent>
//	      </constituent>
//	      <constituent cat="VVFIN-HD" ID="c6" tokenIDs="t2"/>
//	      <constituent ID="c7" cat="NP-OA">
//	       <constituent cat="ART-NK-Acc.Sg" ID="c8" tokenIDs="t3"/>
//	       <constituent cat="NN-NK-Acc.Sg" ID="c9" tokenIDs="t4"/>
//	      </constituent>
//	     </constituent>
//	     <constituent cat="\$." ID="c10" tokenIDs="t5"/>
//	    </constituent>
//	   </parse>

        Constituent c5 = parses.createTerminalConstituent("NE-HD-Nom.Sg", sentenceTokens[0]);
        Constituent c4 = parses.createConstituent("PN-HD-Nom.Sg", Arrays.asList(new Constituent[]{c5}));
        Constituent c3 = parses.createConstituent("NP-SB", Arrays.asList(new Constituent[]{c4}));

        Constituent c6 = parses.createTerminalConstituent("VVFIN-HD", sentenceTokens[1]);

        Constituent c8 = parses.createTerminalConstituent("ART-NK-Acc.Sg", sentenceTokens[2]);
        Constituent c9 = parses.createTerminalConstituent("NN-NK-Acc.Sg", sentenceTokens[3]);
        Constituent c7 = parses.createConstituent("NP-OA", Arrays.asList(new Constituent[]{c8, c9}));

        Constituent c2 = parses.createConstituent("S-TOP", Arrays.asList(new Constituent[]{c3, c6, c7}));

        Constituent c10 = parses.createTerminalConstituent("\\$.", sentenceTokens[4]);

        Constituent c1 = parses.createConstituent("TOP", Arrays.asList(new Constituent[]{c2, c10}));

        return c1;

    }
}
