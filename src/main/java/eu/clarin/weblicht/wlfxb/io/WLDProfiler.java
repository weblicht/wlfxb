package eu.clarin.weblicht.wlfxb.io;

import eu.clarin.weblicht.wlfxb.xb.WLDProfile;
import java.io.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class WLDProfiler {

    public static WLDProfile read(InputStream inputStream) throws WLFormatException {
        WLDProfile profile = null;
        try {
            JAXBContext context = JAXBContext.newInstance(WLDProfile.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            profile = (WLDProfile) unmarshaller.unmarshal(inputStream);
        } catch (JAXBException e) {
            throw new WLFormatException(e);
        }
        return profile;
    }

    public static WLDProfile read(Reader reader) throws WLFormatException {
        WLDProfile profile = null;
        try {
            JAXBContext context = JAXBContext.newInstance(WLDProfile.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            profile = (WLDProfile) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            throw new WLFormatException(e);
        }
        return profile;
    }

    public static WLDProfile read(File file) throws WLFormatException {
        WLDProfile profile = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            profile = read(fis);

        } catch (IOException e) {
            throw new WLFormatException(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new WLFormatException(e);
                }
            }
        }
        return profile;
    }
}
