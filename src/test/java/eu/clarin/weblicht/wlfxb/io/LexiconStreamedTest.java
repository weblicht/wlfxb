package eu.clarin.weblicht.wlfxb.io;

import eu.clarin.weblicht.wlfxb.lx.api.CooccurrenceFunction;
import eu.clarin.weblicht.wlfxb.lx.api.CooccurrencesLayer;
import eu.clarin.weblicht.wlfxb.lx.api.Entry;
import eu.clarin.weblicht.wlfxb.lx.api.FrequenciesLayer;
import eu.clarin.weblicht.wlfxb.lx.api.Frequency;
import eu.clarin.weblicht.wlfxb.lx.api.Lexicon;
import eu.clarin.weblicht.wlfxb.lx.api.Term;
import eu.clarin.weblicht.wlfxb.lx.xb.LexiconLayerTag;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import org.junit.Test;

public class LexiconStreamedTest {

    private static final String INPUT_FILE_FOR_READ = "/data/streamer/lx-karin.xml";
    private static final String INPUT_FILE_FOR_REWRITE = "/data/streamer/lx-karin-ent_freq.xml";
    private static final String OUTPUT_FILE_ADD_COOCCURRENCES = "/tmp/output-add_cooc.xml";

    @Test
    public void testRead() throws Exception {
        System.out.println();
        System.out.println("--- READ TEST START ---");
        InputStream is =this.getClass().getResourceAsStream(INPUT_FILE_FOR_READ);
        testRead(is, EnumSet.of(LexiconLayerTag.ENTRIES, LexiconLayerTag.FREQUENCIES));
        System.out.println("--- READ TEST END ---");
        System.out.println();
    }

    private void testRead(InputStream is, EnumSet<LexiconLayerTag> layersToRead) throws Exception {
        Lexicon lx = new LexiconStreamed(is, layersToRead);
        System.out.println(lx.getEntriesLayer());
        FrequenciesLayer freqLayer = lx.getFrequenciesLayer();
        Frequency f1 = freqLayer.getFrequency(0);
        Entry entryOfFreqAnnotation = freqLayer.getEntry(f1);
        System.out.println(entryOfFreqAnnotation);
        System.out.println(lx);
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
        File ofile = new File(OUTPUT_FILE_ADD_COOCCURRENCES);
        OutputStream os = new FileOutputStream(ofile);
        testReadWrite(is, EnumSet.of(LexiconLayerTag.ENTRIES),
                os, false);
        System.out.println("--- WRITE TEST END ---");
        System.out.println();
    }


    private void testReadWrite(InputStream is,
            EnumSet<LexiconLayerTag> layersToRead, OutputStream os,
            boolean outputAsXmlFragment) throws WLFormatException {

        LexiconStreamed lx = new LexiconStreamed(is, layersToRead, os, outputAsXmlFragment);
        System.out.println();
        System.out.println("Before write:\n" + lx);
        System.out.println();

        addCooccurrencesAndCloseTheStreams(lx);

        System.out.println();
        System.out.println("INSPECT XML RESULT IN:\n" + OUTPUT_FILE_ADD_COOCCURRENCES);

    }

    

    private void addCooccurrencesAndCloseTheStreams(LexiconStreamed lx) throws WLFormatException {

        // add cooccurrences:
        CooccurrencesLayer layer = lx.createCooccurrencesLayer();
        for (int i = 0; i < lx.getEntriesLayer().size(); i++) {
            Entry entry = lx.getEntriesLayer().getEntry(i);
            List<Term> terms = new ArrayList<Term>();
            terms.add(layer.createTerm(entry));
            terms.add(layer.createTerm(i + "-faked-cooccurence-term-to-be-replaced"));
            layer.addCooccurrence(CooccurrenceFunction.sentence, terms);
        }

        System.out.println("After cooccurences write:\n" + lx);

        // IMPORTANT: Close the LexiconStreamer streams!!!
        lx.close();
    }

}
