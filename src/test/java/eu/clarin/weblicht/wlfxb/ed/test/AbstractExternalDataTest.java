package eu.clarin.weblicht.wlfxb.ed.test;

import eu.clarin.weblicht.wlfxb.ed.xb.ExternalDataLayerTag;
import eu.clarin.weblicht.wlfxb.io.ExternalDataWithTextCorpusStreamed;
import eu.clarin.weblicht.wlfxb.io.WLFormatException;
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
public abstract class AbstractExternalDataTest {

    protected ExternalDataWithTextCorpusStreamed read(String resource,
            EnumSet<ExternalDataLayerTag> edLayersToRead,
            EnumSet<TextCorpusLayerTag> tcLayersToRead) throws Exception {
        InputStream is = this.getClass().getResourceAsStream(resource);
        ExternalDataWithTextCorpusStreamed edtc = new ExternalDataWithTextCorpusStreamed(is, edLayersToRead, tcLayersToRead);
        edtc.close();
        System.out.println(edtc);
        return edtc;
    }

    protected ExternalDataWithTextCorpusStreamed open(String inputResource, String outputFile, EnumSet<ExternalDataLayerTag> edLayersToRead, EnumSet<TextCorpusLayerTag> tcLayersToRead) throws FileNotFoundException, WLFormatException {
        InputStream is = this.getClass().getResourceAsStream(inputResource);
        Assert.assertNotNull("can't open input resource", is);
        OutputStream os = new FileOutputStream(outputFile);
        Assert.assertNotNull("can't open output file", os);
        return new ExternalDataWithTextCorpusStreamed(is, edLayersToRead, tcLayersToRead, os);
    }

    protected void assertEqualXml(String expectedOutputResource, String outputFile) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
        InputStream expected = this.getClass().getResourceAsStream(expectedOutputResource);
        Assert.assertNotNull("can't open expected output resource", expected);
        InputStream actual = new FileInputStream(outputFile);
        Assert.assertNotNull("can't open actual output resource", actual);
        TestUtils.assertEqualXml(expected, actual);
    }
}
