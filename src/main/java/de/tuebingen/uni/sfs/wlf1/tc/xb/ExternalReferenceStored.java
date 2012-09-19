/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Yana Panchenko
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class ExternalReferenceStored {

    public static final String XML_NAME = "extref";
    @XmlAttribute(name = "refid")
    protected String refid;

    @Override
    public String toString() {
        return refid;
    }

    protected ExternalReferenceStored() {
    }

    protected ExternalReferenceStored(String refid) {
        this.refid = refid;
    }
}
