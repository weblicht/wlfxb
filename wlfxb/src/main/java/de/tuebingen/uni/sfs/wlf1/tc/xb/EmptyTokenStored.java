/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
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
public class EmptyTokenStored implements Token {

    public static final String XML_NAME = "emptytok";
    public static final String ID_PREFIX = "et_";
    @XmlValue
    String tokenString;
    @XmlAttribute(name = CommonAttributes.ID, required = true)
    String id;

    @Override
    public String getString() {
        return tokenString;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id + " " + tokenString);
        return sb.toString();
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public Integer getStart() {
        return null;
    }

    @Override
    public Integer getEnd() {
        return null;
    }
}
