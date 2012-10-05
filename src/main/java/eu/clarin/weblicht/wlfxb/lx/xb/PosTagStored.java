/**
 *
 */
package eu.clarin.weblicht.wlfxb.lx.xb;

import eu.clarin.weblicht.wlfxb.lx.api.PosTag;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author Yana Panchenko
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class PosTagStored implements PosTag {

    public static final String XML_NAME = "tag";
    //public static final String ID_PREFIX = "p_";
    //TODO rename attributes!!!
    @XmlValue
    protected String tagString;
    @XmlAttribute(name = CommonAttributes.ID)
    private String tagId;
    @XmlAttribute(name = CommonAttributes.LEMMA_REFERENCE, required = true)
    protected String lemRef;

    @Override
    public String getString() {
        return tagString;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (tagId != null) {
            sb.append(tagId);
            sb.append(" -> ");
        }
        sb.append(tagString).append("[").append(lemRef).append("]");
        return sb.toString();
    }
}
