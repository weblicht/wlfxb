/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = MatchesQuery.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class MatchesQuery {

    public static final String XML_NAME = "query";
    @XmlAttribute(name = CommonAttributes.TYPE, required = true)
    protected String type;
    @XmlValue
    protected String value;

    MatchesQuery() {
    }

    MatchesQuery(String type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(XML_NAME);
        sb.append(" ");
        sb.append(type);
        sb.append(" ");
        sb.append(value);
        return sb.toString();
    }
}
