/**
 *
 */
package de.tuebingen.uni.sfs.jaxbtest;

import de.tuebingen.uni.sfs.wlf1.tc.xb.FeatureStructureStored;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class SimpleTest4 {

    private static final String INPUT_FILE2 = "data/input-tc-fragment4.xml";

    public SimpleTest4() {
    }

    @Test
    public void testRead_File() throws Exception {
        System.out.println("read");
        File file = new File(INPUT_FILE2);
        InputStream is = new FileInputStream(file);
        testRead(is);
    }

    private void testRead(InputStream is) throws Exception {
        JAXBContext context = JAXBContext.newInstance(FeatureStructureStored.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        FeatureStructureStored o = ((FeatureStructureStored) unmarshaller.unmarshal(is));
        System.out.println(o);
    }
}
