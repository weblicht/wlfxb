package eu.clarin.weblicht.wlfxb.ed.xb;

import eu.clarin.weblicht.wlfxb.ed.api.TokenSegmentationLayer;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = TokenSegmentationLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class TokenSegmentationLayerStored extends ExternalDataLayerStored implements TokenSegmentationLayer {

    public static final String XML_NAME = "tokensegmentation";

    protected TokenSegmentationLayerStored() {
        super();
    }

    protected TokenSegmentationLayerStored(String mimeType) {
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
