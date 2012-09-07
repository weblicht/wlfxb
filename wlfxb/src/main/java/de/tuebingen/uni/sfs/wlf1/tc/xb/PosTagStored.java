/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.PosTag;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import java.util.Arrays;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = PosTagStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class PosTagStored implements PosTag {

    public static final String XML_NAME = "tag";
    //public static final String ID_PREFIX = "p_";
    @XmlValue
    String tagString;
    @XmlAttribute(name = CommonAttributes.ID)
    String tagId;
    @XmlAttribute(name = CommonAttributes.CONSECUTIVE_TOKENS_REFERENCE, required = true)
    String[] tokRefs;

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
        sb.append(this.tagString + " " + Arrays.toString(tokRefs));
        return sb.toString();
    }
}
