/**
 *
 */
package eu.clarin.weblicht.wlfxb.ed.xb;

import eu.clarin.weblicht.wlfxb.ed.api.ExternalDataLayer;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author Yana Panchenko
 *
 */
@XmlTransient
public abstract class ExternalDataLayerStored implements ExternalDataLayer {

    @XmlAttribute(name = CommonAttributes.TYPE, required = true)
    private String type;
    @XmlValue
    private String dataURI;

    protected ExternalDataLayerStored() {
        super();
    }

    protected ExternalDataLayerStored(String mimeType) {
        this.type = mimeType;
    }

    public String getDataMimeType() {
        return type;
    }

    public String getLink() {
        return dataURI;
    }

    public void addLink(String dataURI) {
        this.dataURI = dataURI;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDataMimeType());
        sb.append(" ");
        sb.append(getLink());
        return sb.toString();
    }
}
