/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.xb;

import eu.clarin.weblicht.wlfxb.tc.api.Constituent;
import eu.clarin.weblicht.wlfxb.tc.api.ConstituentParse;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;
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
    private String constituentParseId;
    @XmlElement(name = ConstituentStored.XML_NAME, required = true)
    protected ConstituentStored constituentParseRoot;

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
