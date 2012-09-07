package de.tuebingen.uni.sfs.wlf1.io;

import de.tuebingen.uni.sfs.wlf1.md.xb.MetaData;
import de.tuebingen.uni.sfs.wlf1.md.xb.MetaDataItem;
import de.tuebingen.uni.sfs.wlf1.tc.xb.TextCorpusStored;
import de.tuebingen.uni.sfs.wlf1.xb.WLData;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
//TODO so that it tests all layers, and so that all layers be in separate tests
public class WLDObjectorEmptyLayersTest {

    private static final String INPUT_FILE_Textcorpus = "data/input2.xml";
    private static final String INPUT_FILE_Lexicon = "data/input_lexicon.xml";
    private static final String OUTPUT_FILE_1 = "data/wld-objector-output-empty.xml";
    private static final String OUTPUT_FILE_2 = "data/wld-objector-min-prefix-output-empty.xml";

    public WLDObjectorEmptyLayersTest() {
    }

    @Test
    public void testRead_Textcorpus() throws Exception {
        File file = new File(INPUT_FILE_Textcorpus);
        InputStream is = new FileInputStream(file);
        testRead(is);
    }

    @Test
    public void testRead_Lexicon() throws Exception {
        File file = new File(INPUT_FILE_Lexicon);
        InputStream is = new FileInputStream(file);
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
        MetaDataItem mdi1 = new MetaDataItem();
        mdi1.name = "title";
        mdi1.value = "binding test";
        md.metaDataItems.add(mdi1);
        MetaDataItem mdi2 = new MetaDataItem();
        mdi2.name = "author";
        mdi2.value = "Yana";
        md.metaDataItems.add(mdi2);
        return md;
    }
}
