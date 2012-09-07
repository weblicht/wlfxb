/**
 *
 */
package de.tuebingen.uni.sfs.jaxbtest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 * @author Yana Panchenko
 *
 */
public class SimpleTest {

    private static final String INPUT_FILE2 = "data/input-tc-fragment.xml";

    public SimpleTest() {
    }

    //@Test
    public void testRead_InputStream() throws Exception {
        System.out.println("read");
        InputStream inputStream = new FileInputStream(INPUT_FILE2);
        testRead(inputStream);
    }

    //@Test
    public void testRead_File() throws Exception {
        System.out.println("read");
        File file = new File(INPUT_FILE2);
        InputStream is = new FileInputStream(file);
        testRead(is);
    }

    private void testRead(InputStream is) throws Exception {
        JAXBContext context = JAXBContext.newInstance(TextCorpusImpl.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        TextCorpusImpl tc = ((TextCorpusImpl) unmarshaller.unmarshal(is));

        System.out.println(tc.toksLayer.getTokens());
    }
}
