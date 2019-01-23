package eu.clarin.weblicht.wlfxb.io;

import eu.clarin.weblicht.wlfxb.lx.api.*;
import eu.clarin.weblicht.wlfxb.lx.xb.LexiconStored;
import eu.clarin.weblicht.wlfxb.md.xb.MetaData;
import eu.clarin.weblicht.wlfxb.test.utils.TestUtils;
import eu.clarin.weblicht.wlfxb.xb.WLData;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yana Panchenko
 *
 */
public class WLDObjectorTestLexicon {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT_FILE_Lexicon = "/data/objector/input_lexicon.xml";

    public WLDObjectorTestLexicon() {
    }

    @Test
    public void testWrite_Lexicon() throws Exception {
        File file = testFolder.newFile("wld-lexicon-write.xml");
        WLData data = createWLLexiconTestData();
        WLDObjector.write(data, file);
        TestUtils.assertEqualXml(this.getClass().getResourceAsStream(INPUT_FILE_Lexicon), new FileInputStream(file));
    }

    @Test
    public void testWriteUsingMinimumNsPrefixes_File() throws Exception {
        File file = testFolder.newFile("wld-lexicon-write-minns.xml");
        WLData data = createWLLexiconTestData();
        WLDObjector.write(data.getMetaData(), data.getLexicon(), file, false);
        TestUtils.assertEqualXml(this.getClass().getResourceAsStream(INPUT_FILE_Lexicon), new FileInputStream(file));
    }

    @Test
    public void testReadWrite_Lexicon() throws Exception {
        InputStream is = this.getClass().getResourceAsStream(INPUT_FILE_Lexicon);
        WLData wld = WLDObjector.read(is);
        File file = testFolder.newFile("wld-lexicon-readwrite.xml");
        WLDObjector.write(wld, file);
        TestUtils.assertEqualXml(this.getClass().getResourceAsStream(INPUT_FILE_Lexicon), new FileInputStream(file));
    }

    private WLData createWLLexiconTestData() {
        MetaData md = new MetaData();
        {
            md.addMetaDataItem("title", "lexicon write test");
            md.addMetaDataItem("author", "HAL 9000");
        }

        LexiconStored lexicon = new LexiconStored("en");
        {
            EntriesLayer entries = lexicon.createEntriesLayer(EntryType.lemmas);
            PosTagsLayer postags = lexicon.createPosTagsLayer("stts");
            CooccurrencesLayer coocurences = lexicon.createCooccurrencesLayer();
            SyllabificationsLayer syllabs = lexicon.createSyllabificationsLayer();
            SynonymsLayer synonyms = lexicon.createSynonymsLayer();
            FrequenciesLayer frequencies = lexicon.createFrequenciesLayer(FrequencyType.absolute);

            String[] tokenList = "This is a test . This is the second sentence .".split(" ");
            for (String token: tokenList) {
                final Entry entry = entries.addEntry(token);

                postags.addTag(token, entry);

                frequencies.addFrequency(entry, 1);

                List<Term> coocurenceTerms = new ArrayList<Term>();
                coocurenceTerms.add(coocurences.createTerm(entry));
                coocurenceTerms.add(coocurences.createTerm("coocurence1"));
                coocurenceTerms.add(coocurences.createTerm("coocurence2"));
                coocurences.addCooccurrence(CooccurrenceFunction.left_neighbour, coocurenceTerms);
                coocurences.addCooccurrence(CooccurrenceFunction.right_neighbour, coocurenceTerms);

                List<Term> synonymTerms = new ArrayList<Term>();
                synonymTerms.add(coocurences.createTerm(entry));
                synonymTerms.add(coocurences.createTerm("synonym1"));
                synonymTerms.add(coocurences.createTerm("synonym2"));
                synonyms.addSynonym(synonymTerms);

                syllabs.addSyllabification(token, entry);
            }
        }

        return new WLData(md, lexicon);
    }
}
