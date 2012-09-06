/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = TokenStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"end", "start", "tokenId"})
public class TokenStored implements Token {

    public static final String XML_NAME = "token";
    public static final String ID_PREFIX = "t_";
    @XmlValue
    String tokenString;
    @XmlAttribute(name = CommonAttributes.ID, required = true)
    String tokenId;
    @XmlAttribute(name = CommonAttributes.START_CHAR_OFFSET)
    Integer start;
    @XmlAttribute(name = CommonAttributes.END_CHAR_OFFSET)
    Integer end;

    @Override
    public String getString() {
        return tokenString;
    }

    @Override
    public String getID() {
        return tokenId;
    }

    @Override
    public Integer getStart() {
        return start;
    }

    @Override
    public Integer getEnd() {
        return end;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(tokenId);
        sb.append(" -> ");
        sb.append(tokenString);
        if (start != null && end != null) {
            sb.append(" (");
            sb.append(start);
            sb.append("-");
            sb.append(end);
            sb.append(")");
        }
        return sb.toString();
    }
}
