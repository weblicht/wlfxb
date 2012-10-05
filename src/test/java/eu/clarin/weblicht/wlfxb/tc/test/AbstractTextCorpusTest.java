package eu.clarin.weblicht.wlfxb.tc.test;

import eu.clarin.weblicht.wlfxb.io.TextCorpusStreamed;
import eu.clarin.weblicht.wlfxb.io.WLFormatException;
import eu.clarin.weblicht.wlfxb.tc.api.TextCorpus;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import eu.clarin.weblicht.wlfxb.test.utils.TestUtils;
import java.io.*;
import java.util.EnumSet;
import javax.xml.parsers.ParserConfigurationException;
import junit.framework.Assert;
import org.xml.sax.SAXException;

/**
 *
 * @author akislev
 */
public abstract class AbstractTextCorpusTest {

    protected TextCorpus read(String file, EnumSet<TextCorpusLayerTag> layersToRead) throws Exception {
        InputStream is = this.getClass().getResourceAsStream(file);
        TextCorpusStreamed tc = new TextCorpusStreamed(is, layersToRead);
        tc.close();
        System.out.println(tc);
        return tc;
    }

    protected TextCorpusStreamed open(String inputResource, String outputFile, EnumSet<TextCorpusLayerTag> layersToRead) throws FileNotFoundException, WLFormatException {
        InputStream is = this.getClass().getResourceAsStream(inputResource);
        Assert.assertNotNull("can't open input resource", is);
        OutputStream os = new FileOutputStream(outputFile);
        Assert.assertNotNull("can't open output file", os);
        return new TextCorpusStreamed(is, layersToRead, os, false);
    }

    protected void assertEqualXml(String expectedOutputResource, String outputFile) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
        InputStream expected = this.getClass().getResourceAsStream(expectedOutputResource);
        Assert.assertNotNull("can't open expected output resource", expected);
        InputStream actual = new FileInputStream(outputFile);
        Assert.assertNotNull("can't open actual output resource", actual);
        TestUtils.assertEqualXml(expected, actual);
    }
}
