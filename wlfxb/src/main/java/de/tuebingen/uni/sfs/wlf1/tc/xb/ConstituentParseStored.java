/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.Constituent;
import de.tuebingen.uni.sfs.wlf1.tc.api.ConstituentParse;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = ConstituentParseStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class ConstituentParseStored implements ConstituentParse {

    public static final String XML_NAME = "parse";
    public static final String ID_PREFIX = "pc_";
    @XmlAttribute(name = CommonAttributes.ID)
    String constituentParseId;
    @XmlElement(name = ConstituentStored.XML_NAME, required = true)
    ConstituentStored constituentParseRoot;

    @Override
    public Constituent getRoot() {
        return constituentParseRoot;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (constituentParseId != null) {
            sb.append(constituentParseId);
            sb.append(" -> ");
        }
        sb.append(constituentParseRoot.toString());
        return sb.toString();
    }
}
