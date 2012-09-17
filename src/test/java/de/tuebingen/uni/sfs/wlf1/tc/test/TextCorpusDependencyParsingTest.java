/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.test;

import de.tuebingen.uni.sfs.wlf1.io.TextCorpusStreamed;
import de.tuebingen.uni.sfs.wlf1.tc.api.*;
import de.tuebingen.uni.sfs.wlf1.tc.xb.TextCorpusLayerTag;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class TextCorpusDependencyParsingTest extends AbstractTextCorpusTest {

    private static final String INPUT_FILE_WITHOUT_PARSING = "/data/tc-dparsing/tcf-before.xml";
    private static final String INPUT_FILE_WITH_PARSING = "/data/tc-dparsing/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/tc-dparsing/output-expected.xml";
    private static final String OUTPUT_FILE = "/tmp/output.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeParsing =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.SENTENCES);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterParsing =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.SENTENCES, TextCorpusLayerTag.PARSING_DEPENDENCY);

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_PARSING, layersToReadAfterParsing);
        DependencyParsingLayer layer = tc.getDependencyParsingLayer();
        Assert.assertEquals("Tiger", layer.getTagset());
        Assert.assertEquals(true, layer.hasEmptyTokens());
        Assert.assertEquals(false, layer.hasMultipleGovernors());
        Assert.assertEquals(2, layer.size());
        Assert.assertEquals(4, layer.getParse(0).getDependencies().length);
        Assert.assertEquals("SUBJ", layer.getParse(0).getDependencies()[1].getFunction());
        Assert.assertEquals(tc.getTokensLayer().getToken(1), layer.getDependentTokens(layer.getParse(0).getDependencies()[0])[0]);
        Assert.assertEquals("", layer.getGovernorTokens(layer.getParse(1).getDependencies()[0])[0].getString());
    }

    @Test
    public void testReadWrite() throws Exception {
        TextCorpusStreamed tc = open(INPUT_FILE_WITHOUT_PARSING, OUTPUT_FILE, layersToReadBeforeParsing);
        System.out.println(tc);
        SentencesLayer sentences = tc.getSentencesLayer();
        DependencyParsingLayer parses = tc.createDependencyParsingLayer("Tiger", false, true);
        for (int i = 0; i < sentences.size(); i++) {
            Token[] sentenceTokens = sentences.getTokens(sentences.getSentence(i));
            // creates test parse for the test tokens
            List<Dependency> deps = parse(sentenceTokens, parses);
            parses.addParse(deps);
        }
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
    }

    private List<Dependency> parse(Token[] sentenceTokens, DependencyParsingLayer parses) {
        if (sentenceTokens[0].getString().equals("Peter")) {
            return parseFirstTestSentence(sentenceTokens, parses);
        } else {
            return parseSecondTestSentence(sentenceTokens, parses);
        }
    }

    private List<Dependency> parseSecondTestSentence(Token[] sentenceTokens,
            DependencyParsingLayer parses) {

//	   <parse>
//	   <dependency func="ROOT" depIDs="t7" govIDs="et_0"/>
//	    <dependency func="SUBJ" depIDs="t6" govIDs="t7"/>
//	    <dependency func="OBJ" depIDs="t8" govIDs="t7"/>
//	    <emptytoks>
//	          <emptytok ID="et_0"></emptytok>
//	        </emptytoks>
//	   </parse>

        List<Dependency> deps = new ArrayList<Dependency>();
        Token emptyToken = parses.createEmptyToken("");
        Dependency d1 = parses.createDependency("ROOT", sentenceTokens[1], emptyToken);
        Dependency d2 = parses.createDependency("SUBJ", sentenceTokens[0], sentenceTokens[1]);
        Dependency d3 = parses.createDependency("OBJ", sentenceTokens[3], sentenceTokens[1]);
        deps.add(d1);
        deps.add(d2);
        deps.add(d3);
        return deps;

    }

    private List<Dependency> parseFirstTestSentence(Token[] sentenceTokens,
            DependencyParsingLayer parses) {
//		<parse>
//	    <dependency depIDs="t2" func="ROOT"/>
//	    <dependency func="SUBJ" depIDs="t1" govIDs="t2"/>
//	    <dependency func="SPEC" depIDs="t3" govIDs="t4"/>
//	    <dependency func="OBJ" depIDs="t4" govIDs="t2"/>
//	   </parse>

        List<Dependency> deps = new ArrayList<Dependency>();
        Dependency d1 = parses.createDependency("ROOT", sentenceTokens[1]);
        Dependency d2 = parses.createDependency("SUBJ", sentenceTokens[0], sentenceTokens[1]);
        Dependency d3 = parses.createDependency("SPEC", sentenceTokens[2], sentenceTokens[3]);
        Dependency d4 = parses.createDependency("OBJ", sentenceTokens[3], sentenceTokens[1]);
        deps.add(d1);
        deps.add(d2);
        deps.add(d3);
        deps.add(d4);
        return deps;

    }
}
