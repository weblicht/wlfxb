package de.tuebingen.uni.sfs.wlf1.io;

import de.tuebingen.uni.sfs.wlf1.md.xb.MetaData;
import de.tuebingen.uni.sfs.wlf1.tc.xb.TextCorpusStored;
import de.tuebingen.uni.sfs.wlf1.xb.WLData;
import java.io.File;
import java.io.InputStream;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
//TODO so that it tests all layers, and so that all layers be in separate tests
public class WLDObjectorEmptyLayersTest {

    private static final String INPUT_FILE_Textcorpus = "/data/objector/input_textcorpus.xml";
    private static final String INPUT_FILE_Lexicon = "/data/objector/input_lexicon.xml";
    private static final String OUTPUT_FILE_1 = "/tmp/wld-output-empty.xml";
    private static final String OUTPUT_FILE_2 = "/tmp/wld-min-prefix-output-empty.xml";

    public WLDObjectorEmptyLayersTest() {
    }

    @Test
    public void testRead_Textcorpus() throws Exception {
        InputStream is = this.getClass().getResourceAsStream(INPUT_FILE_Textcorpus);
        testRead(is);
    }

    @Test
    public void testRead_Lexicon() throws Exception {
        InputStream is = this.getClass().getResourceAsStream(INPUT_FILE_Lexicon);
        testRead(is);
    }

    @Test
    public void testWrite_File() throws Exception {
        System.out.println("write");
        File file = new File(OUTPUT_FILE_1);
        WLData data = createWLTestData();
        WLDObjector.write(data, file, true);
    }

    @Test
    public void testWriteUsingMinimumNsPrefixes_File() throws Exception {
        System.out.println("write");
        File file = new File(OUTPUT_FILE_2);
        WLData data = createWLTestData();
        WLDObjector.write(data.getMetaData(), data.getTextCorpus(), file, false);
    }

    private void testRead(InputStream is) throws Exception {
        System.out.println("read");
        WLData wld = WLDObjector.read(is);
        System.out.println(" --- " + wld.getMetaData());
        System.out.println(" --- " + wld.getTextCorpus());
        System.out.println(" --- " + wld.getLexicon());
    }

    private WLData createWLTestData() {
        MetaData md = createTestMetadata();
        TextCorpusStored tc = createTestTextCorpus();
        WLData data = new WLData(md, tc);
        return data;
    }

    private TextCorpusStored createTestTextCorpus() {
        TextCorpusStored textCorpus = new TextCorpusStored("en");
        textCorpus.createTextLayer();
        textCorpus.createTokensLayer();
        textCorpus.createSentencesLayer();
        textCorpus.createPosTagsLayer("Tiger");
        return textCorpus;
    }

    private MetaData createTestMetadata() {
        MetaData md = new MetaData();
        //data.metaData.source = "Tuebingen Uni";
        md.addMetaDataItem("title", "binding test");
        md.addMetaDataItem("author", "Yana");
        return md;
    }
}
