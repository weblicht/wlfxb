package de.tuebingen.uni.sfs.wlf1.lx.test;

import de.tuebingen.uni.sfs.wlf1.io.LexiconStreamed;
import de.tuebingen.uni.sfs.wlf1.io.WLFormatException;
import de.tuebingen.uni.sfs.wlf1.lx.api.Lexicon;
import de.tuebingen.uni.sfs.wlf1.lx.xb.LexiconLayerTag;
import de.tuebingen.uni.sfs.wlf1.test.utils.TestUtils;
import java.io.*;
import java.util.EnumSet;
import javax.xml.parsers.ParserConfigurationException;
import junit.framework.Assert;
import org.xml.sax.SAXException;

/**
 *
 * @author akislev
 */
public abstract class AbstractLexiconTest {

    protected Lexicon read(String resource, EnumSet<LexiconLayerTag> layersToRead) throws Exception {
        InputStream is = this.getClass().getResourceAsStream(resource);
        LexiconStreamed lex = new LexiconStreamed(is, layersToRead);
        lex.close();
        System.out.println(lex);
        return lex;
    }

    protected LexiconStreamed open(String inputResource, String outputFile, EnumSet<LexiconLayerTag> layersToRead) throws FileNotFoundException, WLFormatException {
        InputStream is = this.getClass().getResourceAsStream(inputResource);
        Assert.assertNotNull("can't open input resource", is);
        OutputStream os = new FileOutputStream(outputFile);
        Assert.assertNotNull("can't open output file", os);
        return new LexiconStreamed(is, layersToRead, os, false);

    }

    protected void assertEqualXml(String expectedOutputResource, String outputFile) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
        InputStream expected = this.getClass().getResourceAsStream(expectedOutputResource);
        Assert.assertNotNull("can't open expected output resource", expected);
        InputStream actual = new FileInputStream(outputFile);
        Assert.assertNotNull("can't open actual output resource", actual);
        TestUtils.assertEqualXml(expected, actual);
    }
}
