package de.tuebingen.uni.sfs.wlf1.ed.xb;

import de.tuebingen.uni.sfs.wlf1.ed.api.CanonicalSegmentationLayer;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = CanonicalSegmentationLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class CanonicalSegmentationLayerStored extends ExternalDataLayerStored implements CanonicalSegmentationLayer {

    public static final String XML_NAME = "canonicalsegmentation";

    protected CanonicalSegmentationLayerStored() {
        super();
    }

    protected CanonicalSegmentationLayerStored(String mimeType) {
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
