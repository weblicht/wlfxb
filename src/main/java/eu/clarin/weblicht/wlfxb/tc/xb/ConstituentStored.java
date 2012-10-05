/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.xb;

import eu.clarin.weblicht.wlfxb.tc.api.Constituent;
import eu.clarin.weblicht.wlfxb.tc.api.ConstituentReference;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;
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
    protected String constituentId;
    @XmlAttribute(name = XML_ATTRIBUTE_CATEGORY, required = true)
    protected String category;
    @XmlAttribute(name = "edge")
    protected String edge;
    @XmlAttribute(name = CommonAttributes.TOKEN_SEQUENCE_REFERENCE)
    protected String[] tokRefs;
    @XmlElement(name = ConstituentReferenceStored.XML_NAME)
    protected List<ConstituentReferenceStored> crefs = new ArrayList<ConstituentReferenceStored>();
    @XmlElement(name = ConstituentStored.XML_NAME)
    protected List<ConstituentStored> children = new ArrayList<ConstituentStored>();

    @Override
    public boolean isTerminal() {
        return children.isEmpty();
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
        if (children.isEmpty()) {
            return null;
        } else {
            Constituent[] childrenAsArray = new Constituent[children.size()];
            return children.toArray(childrenAsArray);
        }
    }

    @Override
    public ConstituentReference[] getSecondaryEdgeChildren() {
        ConstituentReference[] crefstemp = new ConstituentReference[crefs.size()];
        return crefs.toArray(crefstemp);
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getEdge() {
        return edge;
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
        if (edge != null) {
            sb.append(edge);
            sb.append(" ");
        }
        if (!crefs.isEmpty()) {
            sb.append(crefs.toString());
            sb.append(" ");
        }
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
