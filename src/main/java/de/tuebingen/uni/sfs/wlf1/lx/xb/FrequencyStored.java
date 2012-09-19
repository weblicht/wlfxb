/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.lx.xb;

import de.tuebingen.uni.sfs.wlf1.lx.api.Frequency;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
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
