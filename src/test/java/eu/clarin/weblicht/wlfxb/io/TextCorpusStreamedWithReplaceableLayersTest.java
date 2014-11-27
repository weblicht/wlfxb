package eu.clarin.weblicht.wlfxb.io;

import eu.clarin.weblicht.wlfxb.io.TextCorpusStreamed;
import eu.clarin.weblicht.wlfxb.io.WLFormatException;
import eu.clarin.weblicht.wlfxb.io.XmlReaderWriter;
import eu.clarin.weblicht.wlfxb.tc.api.LemmasLayer;
import eu.clarin.weblicht.wlfxb.tc.api.TextCorpus;
import eu.clarin.weblicht.wlfxb.tc.api.TextCorpusLayer;
import eu.clarin.weblicht.wlfxb.tc.api.Token;
import eu.clarin.weblicht.wlfxb.tc.test.AbstractTextCorpusTest;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerStoredAbstract;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusStored;
import eu.clarin.weblicht.wlfxb.test.utils.TestUtils;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author Yana Panchenko <yana.panchenko@uni-tuebingen.de>
 */
public class TextCorpusStreamedWithReplaceableLayersTest extends AbstractTextCorpusTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT_FILE_WITH_LAYER = "/data/streamer/tcf-text_toks_sents_pos_lem.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/streamer/tcf-text_toks_sents_pos_changed-lem.xml";
    private static final String OUTPUT_FILE = "output.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToRead =
            EnumSet.of(TextCorpusLayerTag.TOKENS);
    private static final EnumSet<TextCorpusLayerTag> layersToReplace =
            EnumSet.of(TextCorpusLayerTag.LEMMAS);
    
    public static final Map<String, String> token2Lemma = new HashMap<String, String>();
    
    static {
        token2Lemma.put("Peter", "Peter");
        token2Lemma.put("aß", "essen");
        token2Lemma.put("eine", "ein");
        token2Lemma.put("Käsepizza", "Käsepizza");
        token2Lemma.put("Sie", "sie");
        token2Lemma.put("schmeckte", "schmecken");
        token2Lemma.put("ihm", "er");
    }


    @Test
    public void testReadWrite() throws Exception {
        InputStream is = this.getClass().getResourceAsStream(INPUT_FILE_WITH_LAYER);
        junit.framework.Assert.assertNotNull("can't open input resource", is);
        OutputStream os = new FileOutputStream(testFolder.newFile(OUTPUT_FILE));
        junit.framework.Assert.assertNotNull("can't open output file", os);
        TextCorpusStreamedWithReplaceableLayers tc = new TextCorpusStreamedWithReplaceableLayers(is, layersToRead, layersToReplace, os);
        
        // create lemmas layer, empty at first
        LemmasLayer lemmas = tc.createLemmasLayer();
        for (int i = 0; i < tc.getTokensLayer().size(); i++) {
            Token token = tc.getTokensLayer().getToken(i);
            String lemmaString = lemmatize(token.getString());
            // create and add lemma to the lemmas layer
            if (lemmaString != null) {
                lemmas.addLemma(lemmaString, token);
            }
        }
        // IMPORTANT close the streams!!!
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        //assertEqualXml(EXPECTED_OUTPUT_FILE, OUTPUT_FILE);
        InputStream expected = this.getClass().getResourceAsStream(EXPECTED_OUTPUT_FILE);
        junit.framework.Assert.assertNotNull("can't open expected output resource", expected);
        InputStream actual = new FileInputStream(testFolder.newFile(OUTPUT_FILE));
        junit.framework.Assert.assertNotNull("can't open actual output resource", actual);
        TestUtils.assertEqualXml(expected, actual);
    }

    private String lemmatize(String tokenString) {
        return token2Lemma.get(tokenString);
    }
}
