/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.Coreference;
import de.tuebingen.uni.sfs.wlf1.tc.api.Referent;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = ReferentStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class ReferentStored implements Referent {

    public static final String XML_NAME = "referent";
    @XmlAttribute(name = CommonAttributes.ID)
    String id;
    @XmlAttribute(name = CommonAttributes.TYPE, required = true)
    String type;
    @XmlElement(name = "extref")
    ExternalReferenceStored externalRef;
    @XmlElement(name = CoreferenceStored.XML_NAME)
    List<CoreferenceStored> coreferences = new ArrayList<CoreferenceStored>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (id != null) {
            sb.append(id);
            sb.append(" -> ");
        }
        sb.append(type);
        if (externalRef != null) {
            sb.append(" ");
            sb.append(externalRef.toString());
        }
        sb.append(coreferences.toString());
        return sb.toString();
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getExternalId() {
        if (this.externalRef != null) {
            return this.externalRef.refid;
        }
        return null;
    }

    @Override
    public Coreference[] getCoreferences() {
        if (!coreferences.isEmpty()) {
            return coreferences.toArray(new Coreference[coreferences.size()]);
        }
        return null;
    }
}
