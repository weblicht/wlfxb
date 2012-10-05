/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.xb;

import eu.clarin.weblicht.wlfxb.tc.api.Dependency;
import eu.clarin.weblicht.wlfxb.tc.api.DependencyParse;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * @author Yana Panchenko
 *
 */
public class DependencyParseStored implements DependencyParse {

    public static final String XML_NAME = "parse";
    public static final String ID_PREFIX = "pd_";
    @XmlAttribute(name = CommonAttributes.ID)
    private String parseId;
    @XmlElement(name = DependencyStored.XML_NAME, required = true)
    protected List<DependencyStored> dependencies;
    @XmlElement(name = EmptyTokenStored.XML_NAME)
    @XmlElementWrapper(name = "emptytoks")
    protected List<EmptyTokenStored> emptytoks;

    @Override
    public Dependency[] getDependencies() {
        return dependencies.toArray(new Dependency[dependencies.size()]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (parseId != null) {
            sb.append(parseId);
            sb.append(" -> ");
        }
        sb.append(dependencies.toString());
//		if (emptytoks != null) {
//			sb.append(emptytoks.toString());
//		}
        return sb.toString();
    }
}
