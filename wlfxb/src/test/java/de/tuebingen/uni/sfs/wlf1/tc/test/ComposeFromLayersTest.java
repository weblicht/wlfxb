/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.test;

import de.tuebingen.uni.sfs.wlf1.io.WLDObjector;
import de.tuebingen.uni.sfs.wlf1.md.xb.MetaData;
import de.tuebingen.uni.sfs.wlf1.tc.xb.*;
import de.tuebingen.uni.sfs.wlf1.test.utils.TestUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class ComposeFromLayersTest {

    private static final String INPUT_TEXT = "data/tc-text/layer-input.xml";
    private static final String INPUT_TOKENS = "data/tc-tokens/layer-input.xml";
    private static final String INPUT_PARSING = "data/tc-parsing/layer-input.xml";
    private static final String INPUT_SENTENCES = "data/tc-sents/layer-input.xml";
    private static final String INPUT_LEMMAS = "data/tc-lemmas/layer-input.xml";
    private static final String OUTPUT_FILE = "data/composer/output.xml";

    public ComposeFromLayersTest() {
    }

    @Test
    public void test() throws Exception {

        InputStream is;

        is = new FileInputStream(INPUT_TEXT);
        TextLayerStored textLayer = TestUtils.read(TextLayerStored.class, is);
        is.close();

        is = new FileInputStream(INPUT_TOKENS);
        TokensLayerStored tokensLayer = TestUtils.read(TokensLayerStored.class, is);
        is.close();

        is = new FileInputStream(INPUT_PARSING);
        ConstituentParsingLayerStored parsingLayer = TestUtils.read(ConstituentParsingLayerStored.class, is);
        is.close();

        is = new FileInputStream(INPUT_SENTENCES);
        SentencesLayerStored sentsLayer = TestUtils.read(SentencesLayerStored.class, is);
        is.close();

        is = new FileInputStream(INPUT_LEMMAS);
        LemmasLayerStored lemmasLayer = TestUtils.read(LemmasLayerStored.class, is);
        is.close();

        TextCorpusStored textCorpus = TextCorpusStored.compose("de", textLayer, tokensLayer, parsingLayer, sentsLayer, lemmasLayer);
//        List<TextCorpusLayerStoredAbstract> layers = new ArrayList<TextCorpusLayerStoredAbstract>();
//        layers.add(textLayer); layers.add(tokensLayer); layers.add(parsingLayer); layers.add(sentsLayer); layers.add(lemmasLayer);
//        TextCorpusLayerStoredAbstract[] layersAsArray = new TextCorpusLayerStoredAbstract[layers.size()];
//        TextCorpusStored textCorpus = TextCorpusStored.compose("de",  layers.toArray(layersAsArray));


        WLDObjector.write(new MetaData(), textCorpus, new File(OUTPUT_FILE), false);

    }
}
