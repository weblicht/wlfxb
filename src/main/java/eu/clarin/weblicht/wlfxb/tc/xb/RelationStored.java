/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.xb;

import eu.clarin.weblicht.wlfxb.tc.api.Relation;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;
import java.util.Arrays;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Yana Panchenko
 *
 */
@SuppressWarnings("deprecation")
@XmlAccessorType(XmlAccessType.NONE)
@Deprecated
public class RelationStored implements Relation {

    public static final String XML_NAME = "relation";
    @XmlAttribute(name = CommonAttributes.ID)
    protected String id;
    @XmlAttribute(name = CommonAttributes.FUNCTION)
    protected String function;
    @XmlAttribute(name = "refIDs", required = true)
    protected String[] tokRefs;

    @Override
    public String getFunction() {
        return function;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (id != null) {
            sb.append(id);
            sb.append(" -> ");
        }
        if (function != null) {
            sb.append(function);
            sb.append(" ");
        }
        sb.append(Arrays.toString(tokRefs));
        return sb.toString();
    }
}
