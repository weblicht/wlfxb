/**
 *
 */
package de.tuebingen.uni.sfs.jaxbtest;

import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class ObjWithMixedContentTest {

    private static final String INPUT_FILE2 = "/data/jaxb/input-tc-fragment.xml";

    public ObjWithMixedContentTest() {
    }

    @Test
    public void testRead_File() throws Exception {
        System.out.println("read");
        InputStream is = this.getClass().getResourceAsStream(INPUT_FILE2);
        testRead(is);
    }

    private void testRead(InputStream is) throws Exception {
        JAXBContext context = JAXBContext.newInstance(ObjWithMixedContent.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ObjWithMixedContent o = ((ObjWithMixedContent) unmarshaller.unmarshal(is));
        System.out.println(o);
    }
}
