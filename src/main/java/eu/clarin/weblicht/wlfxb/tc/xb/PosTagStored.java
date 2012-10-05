/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.xb;

import eu.clarin.weblicht.wlfxb.tc.api.PosTag;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;
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
    protected String tagString;
    @XmlAttribute(name = CommonAttributes.ID)
    protected String tagId;
    @XmlAttribute(name = CommonAttributes.TOKEN_SEQUENCE_REFERENCE, required = true)
    protected String[] tokRefs;

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
        sb.append(this.tagString).append(" ").append(Arrays.toString(tokRefs));
        return sb.toString();
    }
}
