/**
 *
 */
package eu.clarin.weblicht.wlfxb.test.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceEngine;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author Yana Panchenko
 *
 */
public class TestUtils {

    /**
     * @param expectedOutputFile
     * @param outputFile
     * @throws Exception
     */
//    public static void assertEqualXml(InputStream expectedFile, InputStream realFile) throws ParserConfigurationException, SAXException, IOException {
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        dbf.setNamespaceAware(true);
//        dbf.setCoalescing(true);
//        dbf.setIgnoringElementContentWhitespace(true);
//        dbf.setIgnoringComments(true);
//        DocumentBuilder db = dbf.newDocumentBuilder();
//        Document doc1 = db.parse(expectedFile);
//        doc1.normalizeDocument();
//        Document doc2 = db.parse(realFile);
//        doc2.normalizeDocument();
//        Assert.assertTrue(doc1.isEqualNode(doc2));
//    }
//    
    public static void assertEqualXml(InputStream expectedFile, InputStream realFile) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        //dbf.setCoalescing(true);
        //dbf.setIgnoringElementContentWhitespace(true);
        //dbf.setIgnoringComments(true);
        
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc1 = db.parse(expectedFile);
        doc1.normalizeDocument();
        Document doc2 = db.parse(realFile);
        doc2.normalizeDocument();
        
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreAttributeOrder(true);
        XMLUnit.setNormalize(true);

        
        Diff diff = new Diff(doc1, doc2);
        DetailedDiff ddiff = new DetailedDiff(diff);
        List list = ddiff.getAllDifferences();
        boolean equal = true;
        for (Object le : list) {
            Difference d = (Difference) le;
            // ignore differences in namespace prefix names
            if (!d.equals(DifferenceEngine.NAMESPACE_PREFIX)) {
                equal = false;
                break;
            }
        }
        Assert.assertTrue("xml not identical " + diff, equal);
        //Assert.assertTrue("xml not similar " + myDiff, myDiff.similar());
    }

    public static void write(Object jaxbAnnotatedObj, OutputStream os) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(jaxbAnnotatedObj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(jaxbAnnotatedObj, os);
    }

    public static void write(Object jaxbAnnotatedObj, OutputStream os, boolean outputAsFragment) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(jaxbAnnotatedObj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, outputAsFragment);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(jaxbAnnotatedObj, os);
    }

    public static <T> T read(Class<T> cl, InputStream is) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(cl);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        T o = cl.cast(unmarshaller.unmarshal(is));
        return o;
    }
}
