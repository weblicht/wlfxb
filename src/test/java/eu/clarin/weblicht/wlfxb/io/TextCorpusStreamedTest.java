package eu.clarin.weblicht.wlfxb.io;

import eu.clarin.weblicht.wlfxb.md.xb.MetaData;
import eu.clarin.weblicht.wlfxb.md.xb.MetaDataItem;
import eu.clarin.weblicht.wlfxb.tc.api.LemmasLayer;
import eu.clarin.weblicht.wlfxb.tc.api.Sentence;
import eu.clarin.weblicht.wlfxb.tc.api.SentencesLayer;
import eu.clarin.weblicht.wlfxb.tc.api.TextCorpus;
import eu.clarin.weblicht.wlfxb.tc.api.Token;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class TextCorpusStreamedTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT_FILE_FOR_READ = "/data/streamer/tcf-text_toks_sents_pos_lem.xml";
    private static final String INPUT_FILE_FOR_REWRITE = "/data/streamer/tcf-text_tok_pos.xml";
    private static final String OUTPUT_FILE_ADD_LEMMS_SENTS = "output-add_lemms_sents.xml";
    private static final String OUTPUT_FILE_ADD_LEMMS_SENTS_METADATA = "output-add_lemms_sents_metadata.xml";

    @Test
    public void testRead() throws Exception {
        System.out.println();
        System.out.println("--- READ TEST START ---");
        InputStream is =this.getClass().getResourceAsStream(INPUT_FILE_FOR_READ);
        testRead(is, EnumSet.of(TextCorpusLayerTag.SENTENCES, TextCorpusLayerTag.LEMMAS));
        System.out.println("--- READ TEST END ---");
        System.out.println();
    }

    private void testRead(InputStream is, EnumSet<TextCorpusLayerTag> layersToRead) throws Exception {
        TextCorpus tc = new TextCorpusStreamed(is, layersToRead);
        System.out.println(tc.getTokensLayer());
        SentencesLayer sentencesLayer = tc.getSentencesLayer();
        Sentence sentence1 = sentencesLayer.getSentence(0);
        Token[] sentence1Tokens = sentencesLayer.getTokens(sentence1);
        System.out.println(Arrays.toString(sentence1Tokens));
        System.out.println(tc);
    }

    @Test
    /**
     * This test rewrites tcf from input file to output file adding lemmas and
     * sentences layers. All the layers in input file, including those not used
     * (not requested for read) are rewritten into the output file.
     */
    public void testReadWriteAddingSentsLemms() throws Exception {
        System.out.println();
        System.out.println("--- WRITE TEST START ---");
        InputStream is = this.getClass().getResourceAsStream(INPUT_FILE_FOR_REWRITE);
        File ofile = testFolder.newFile(OUTPUT_FILE_ADD_LEMMS_SENTS);
        OutputStream os = new FileOutputStream(ofile);
        testReadWrite(is, EnumSet.of(TextCorpusLayerTag.TOKENS),
                os, false);
        System.out.println("--- WRITE TEST END ---");
        System.out.println();
    }

    @Test
    /**
     * This test rewrites tcf from input file to output file adding lemmas and
     * sentences layers. Additionally it adds MetaData items. All the layers in
     * input file, including those not used (not requested for read) are
     * rewritten into the output file. All the metadata in the input file is
     * also rewritten into the output file.
     */
    public void testReadWriteAddingSentsLemmsMetadata() throws Exception {
        System.out.println();
        System.out.println("--- WRITE TEST START ---");
        InputStream is = this.getClass().getResourceAsStream(INPUT_FILE_FOR_REWRITE);
        File ofile = testFolder.newFile(OUTPUT_FILE_ADD_LEMMS_SENTS_METADATA);
        OutputStream os = new FileOutputStream(ofile);
        testReadWriteAddingMetadata(is, EnumSet.of(TextCorpusLayerTag.TOKENS),
                os,
                createTestMetadata().getMetaDataItems());
        System.out.println("--- WRITE TEST END ---");
        System.out.println();
    }

    private void testReadWrite(InputStream is,
            EnumSet<TextCorpusLayerTag> layersToRead, OutputStream os,
            boolean outputAsXmlFragment) throws WLFormatException {

        TextCorpusStreamed tc = new TextCorpusStreamed(is, layersToRead, os, outputAsXmlFragment);
        System.out.println();
        System.out.println("Before write:\n" + tc);
        System.out.println();

        addLemmasSentsAndCloseTheStreams(tc);

        System.out.println();
        System.out.println("INSPECT XML RESULT IN:\n" + OUTPUT_FILE_ADD_LEMMS_SENTS);

    }

    private void testReadWriteAddingMetadata(
            InputStream is, EnumSet<TextCorpusLayerTag> layersToRead, OutputStream os,
            List<MetaDataItem> metadataItemsToAdd) throws WLFormatException {

        TextCorpusStreamed tc = new TextCorpusStreamed(
                is, layersToRead, os, metadataItemsToAdd);

        System.out.println();
        System.out.println("Before write:\n" + tc);
        System.out.println();

        addLemmasSentsAndCloseTheStreams(tc);

        System.out.println();
        System.out.println("INSPECT XML RESULT IN:\n" + OUTPUT_FILE_ADD_LEMMS_SENTS_METADATA);
    }

    private void addLemmasSentsAndCloseTheStreams(TextCorpusStreamed tc) throws WLFormatException {

        // add lemmas:
        LemmasLayer lemmasLayer = tc.createLemmasLayer();
        for (int i = 0; i < tc.getTokensLayer().size(); i++) {
            Token token = tc.getTokensLayer().getToken(i);
            lemmasLayer.addLemma("_" + token.getString() + "_", token);
        }

        System.out.println("After lemmas write:\n" + tc);

        // add sentences:
        SentencesLayer sentsLayer = tc.createSentencesLayer();
        List<Token> sent1Tokens = new ArrayList<Token>();
        for (int i = 0; i < 5; i++) {
            sent1Tokens.add(tc.getTokensLayer().getToken(i));
        }
        sentsLayer.addSentence(sent1Tokens, 0, 15);
        List<Token> sent2Tokens = new ArrayList<Token>();
        for (int i = 5; i < tc.getTokensLayer().size(); i++) {
            sent2Tokens.add(tc.getTokensLayer().getToken(i));
        }
        sentsLayer.addSentence(sent2Tokens, 16, 44);

        System.out.println();
        System.out.println("After sentences write:\n" + tc);

        // IMPORTANT: Close the TextCorpusStreamer streams!!!
        tc.close();
    }

    private MetaData createTestMetadata() throws ParserConfigurationException {
        MetaData metaData = new MetaData();

        metaData.addMetaDataItem("sentences-boundary-detector", "Tuebingen Uni");
        metaData.addMetaDataItem("lemmatizer", "Tuebingen Uni");

        return metaData;
    }
}
