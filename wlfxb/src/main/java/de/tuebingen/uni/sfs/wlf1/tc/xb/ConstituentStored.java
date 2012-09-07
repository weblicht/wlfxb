/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.Constituent;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = ConstituentStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class ConstituentStored implements Constituent {

    public static final String XML_NAME = "constituent";
    public static final String XML_ATTRIBUTE_CATEGORY = "cat";
    public static final String ID_PREFIX = "c_";
    @XmlAttribute(name = CommonAttributes.ID)
    String constituentId;
    @XmlAttribute(name = XML_ATTRIBUTE_CATEGORY, required = true)
    String category;
    @XmlAttribute(name = CommonAttributes.CONSECUTIVE_TOKENS_REFERENCE)
    String[] tokRefs;
    @XmlElement(name = ConstituentStored.XML_NAME)
    List<ConstituentStored> children = new ArrayList<ConstituentStored>();

    @Override
    public boolean isTerminal() {
        if (children.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isEmptyTerminal() {
        if (isTerminal() && (tokRefs == null || tokRefs.length == 0)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Constituent[] getChildren() {
        // in order to not let user to add children to the list
        if (children.size() == 0) {
            return null;
        } else {
            Constituent[] childrenAsArray = new Constituent[children.size()];
            return children.toArray(childrenAsArray);
        }
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (constituentId != null) {
            sb.append(constituentId);
            sb.append(" -> ");
        }
        sb.append(category);
        sb.append(" ");
        if (tokRefs != null) {
            sb.append(Arrays.toString(tokRefs));
        } else {
            sb.append("( ");
            for (Constituent c : children) {
                sb.append(c.toString());
                sb.append(" ");
            }
            sb.append(")");
        }
        return sb.toString();
    }
}
