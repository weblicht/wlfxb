/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.Coreference;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import java.util.Arrays;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = CoreferenceStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class CoreferenceStored implements Coreference {

    public static final String XML_NAME = "coreference";
    @XmlAttribute(name = CommonAttributes.TYPE, required = true)
    String type;
    @XmlAttribute(name = "srole")
    String semanticRole;
    @XmlAttribute(name = CommonAttributes.CONSECUTIVE_TOKENS_REFERENCE, required = true)
    String[] tokRefs;
    @XmlAttribute(name = "mintokIDs", required = true)
    String[] minTokRefs;

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getSemanticRole() {
        return semanticRole;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(type);
        if (semanticRole != null) {
            sb.append(" ");
            sb.append(semanticRole);
        }
        sb.append(" ");
        sb.append(Arrays.toString(tokRefs));
        sb.append(" ");
        sb.append(Arrays.toString(minTokRefs));
        return sb.toString();
    }
}
