/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.Reference;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import java.util.Arrays;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = ReferenceStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class ReferenceStored implements Reference {

    public static final String XML_NAME = "reference";
    public static final String ID_PREFIX = "rc_";
    @XmlAttribute(name = CommonAttributes.ID)
    protected String id;
    @XmlAttribute(name = CommonAttributes.TYPE)
    protected String type;
    @XmlAttribute(name = "rel")
    protected String relation;
    @XmlAttribute(name = "target")
    protected String[] targetIds;
    @XmlAttribute(name = CommonAttributes.TOKEN_SEQUENCE_REFERENCE, required = true)
    protected String[] tokRefs;
    @XmlAttribute(name = "mintokIDs", required = true)
    protected String[] minTokRefs;

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getRelation() {
        return relation;
    }

    protected boolean beforeMarshal(Marshaller m) {
        setEmptyTargetToNull();
        if (targetIds != null) {
            System.out.println(Arrays.toString(targetIds));
        }
        return true;
    }

    private void setEmptyTargetToNull() {
        if (this.targetIds != null && this.targetIds.length == 0) {
            this.targetIds = null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (id != null) {
            sb.append(id);
            sb.append(" ");
        }
        if (type != null) {
            sb.append(type);
            sb.append(" ");
        }
        if (relation != null) {
            sb.append(relation);
            sb.append(" ");
        }
        if (targetIds != null) {
            sb.append("->");
            sb.append(Arrays.toString(targetIds));
        }
        sb.append(" ");
        sb.append(Arrays.toString(tokRefs));
        if (minTokRefs != null) {
            sb.append(" ");
            sb.append(Arrays.toString(minTokRefs));
        }
        return sb.toString();
    }
}
