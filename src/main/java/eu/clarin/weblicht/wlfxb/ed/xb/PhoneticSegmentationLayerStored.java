package eu.clarin.weblicht.wlfxb.ed.xb;

import eu.clarin.weblicht.wlfxb.ed.api.PhoneticSegmentationLayer;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = PhoneticSegmentationLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class PhoneticSegmentationLayerStored extends ExternalDataLayerStored implements PhoneticSegmentationLayer {

    public static final String XML_NAME = "phoneticsegmentation";

    protected PhoneticSegmentationLayerStored() {
        super();
    }

    protected PhoneticSegmentationLayerStored(String mimeType) {
        super(mimeType);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(XML_NAME);
        sb.append(" : ");
        sb.append(super.toString());
        return sb.toString();
    }
}
