package de.tuebingen.uni.sfs.wlf1.io;

import de.tuebingen.uni.sfs.wlf1.ed.api.SpeechSignalLayer;
import de.tuebingen.uni.sfs.wlf1.ed.xb.ExternalDataStored;
import de.tuebingen.uni.sfs.wlf1.md.xb.MetaData;
import de.tuebingen.uni.sfs.wlf1.md.xb.MetaDataItem;
import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.tc.api.TokensLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.TextCorpusStored;
import de.tuebingen.uni.sfs.wlf1.xb.WLData;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class WLDObjectorTest2 {

    private static final String INPUT_FILE_TextcorpusAndExternalData = "/data/objector/input_textcorpus_extdata.xml";
    private static final String OUTPUT_FILE_1 = "/tmp/wld-output2.xml";

    public WLDObjectorTest2() {
    }

    @Test
    public void testRead_TextcorpusAndExternalData() throws Exception {
        File file = new File(INPUT_FILE_TextcorpusAndExternalData);
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

    private void testRead(InputStream is) throws Exception {
        System.out.println("read");
        WLData wld = WLDObjector.read(is);
        System.out.println(" --- " + wld.getMetaData());
        System.out.println(" --- " + wld.getExternalData());
        System.out.println(" --- " + wld.getTextCorpus());
        System.out.println(" --- " + wld.getLexicon());
    }

    private WLData createWLTestData() {
        MetaData md = createTestMetadata();
        TextCorpusStored tc = createTestTextCorpus();
        ExternalDataStored ed = createTestExternalData();
        WLData data = new WLData(md, ed, tc);
        return data;
    }

    private TextCorpusStored createTestTextCorpus() {
        TextCorpusStored textCorpus = new TextCorpusStored("de");
        String text = "<ähm> ich würde diesmal sagen Theater das ist dann immer so aufge in letzter "
                + "Minute so was spielt man heute abend ich wäre eher dafür daß wir vielleicht ins Kino "
                + "gehen und nachher irgendwo in eine nette Kneipe";
        textCorpus.createTextLayer().addText(text);
        TokensLayer tokensLayer = textCorpus.createTokensLayer();
        String[] tokenStrings = ("<ähm> ich würde diesmal sagen Theater das ist dann immer so aufge in letzter "
                + "Minute so was spielt man heute abend ich wäre eher dafür daß wir vielleicht ins Kino "
                + "gehen und nachher irgendwo in eine nette Kneipe").split(" ");
        List<Token> tokens = new ArrayList<Token>();
        for (String tokenString : tokenStrings) {
            Token token = tokensLayer.addToken(tokenString);
            tokens.add(token);
        }
        return textCorpus;
    }

    private ExternalDataStored createTestExternalData() {
        ExternalDataStored extData = new ExternalDataStored();
        SpeechSignalLayer speechLayer = extData.createSpeechSignalLayer("audio/wav", 1);
        speechLayer.addLink("http://arc:8080/drop-off/storage/g046acn1_037_AFI.wav");
        return extData;
    }

    private MetaData createTestMetadata() {
        MetaData md = new MetaData();
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
