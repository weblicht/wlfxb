/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.ConstituentReference;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = ConstituentReferenceStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class ConstituentReferenceStored implements ConstituentReference {

    public static final String XML_NAME = "cref";
    public static final String XML_ATTRIBUTE_EDGE_LABEL = "edge";
    public static final String XML_ATTRIBUTE_CREF = "constID";
    @XmlAttribute(name = XML_ATTRIBUTE_EDGE_LABEL, required = true)
    private String edge;
    @XmlAttribute(name = XML_ATTRIBUTE_CREF, required = true)
    protected String constId;

    ConstituentReferenceStored() {
    }

    ConstituentReferenceStored(ConstituentStored cref, String edgeLabel) {
        this.edge = edgeLabel;
        this.constId = cref.constituentId;
    }

    @Override
    public String getEdgeLabel() {
        return edge;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(edge);
        sb.append(" -> ");
        sb.append(constId);
        return sb.toString();
    }
}
