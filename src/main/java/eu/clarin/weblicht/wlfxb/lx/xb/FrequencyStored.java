/**
 *
 */
package eu.clarin.weblicht.wlfxb.lx.xb;

import eu.clarin.weblicht.wlfxb.lx.api.Frequency;
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
public class FrequencyStored implements Frequency {

    public static final String XML_NAME = "frequency";
    @XmlValue
    protected int value;
    @XmlAttribute(name = CommonAttributes.LEMMA_REFERENCE, required = true)
    protected String lemRef;

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(lemRef);
        sb.append(" ").append(value);
        return sb.toString();
    }
}
