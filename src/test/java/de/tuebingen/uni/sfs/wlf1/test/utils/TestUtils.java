/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.test.utils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Assert;
import org.w3c.dom.Document;

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
    public static void assertEqualXml(String expectedFile, String realFile) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setCoalescing(true);
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setIgnoringComments(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc1 = db.parse(new File(expectedFile));
        doc1.normalizeDocument();
        Document doc2 = db.parse(new File(realFile));
        doc2.normalizeDocument();
        Assert.assertTrue(doc1.isEqualNode(doc2));
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
