package eu.clarin.weblicht.wlfxb.tc.test;

import eu.clarin.weblicht.wlfxb.io.TextCorpusStreamed;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumSet;

public class TextCorpusKeepUnrequestedLayersTest extends AbstractTextCorpusTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT_FILE = "/data/streamer/tcf04-kohl.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/streamer/tcf04-kohl-output.xml";
    private static final String OUTPUT_FILE = "output.xml";

    @Test
    public void testReadKeepsUnrequestedLayersStreamed() throws Exception {
        InputStream is = this.getClass().getResourceAsStream(INPUT_FILE);
        Assert.assertNotNull("can't open input resource", is);

        String outfile = testFolder.getRoot() + File.separator + OUTPUT_FILE;
        OutputStream os = new FileOutputStream(outfile);
        Assert.assertNotNull("can't open output file", os);

        TextCorpusStreamed tc = new TextCorpusStreamed(is, EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.NAMED_ENTITIES), os, false);

        Assert.assertEquals(2, tc.getLayers().size());

        tc.close();
        assertEqualXml(INPUT_FILE, outfile);
    }
}
