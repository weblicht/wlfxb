/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import de.tuebingen.uni.sfs.wlf1.tc.api.Dependency;
import de.tuebingen.uni.sfs.wlf1.tc.api.DependencyParse;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;

/**
 * @author Yana Panchenko
 *
 */
public class DependencyParseStored implements DependencyParse {


	public static final String XML_NAME = "parse";
	public static final String ID_PREFIX = "pd_";

	@XmlAttribute(name=CommonAttributes.ID)
	String parseId;
	@XmlElement(name=DependencyStored.XML_NAME, required = true)
	List<DependencyStored> dependencies;
	@XmlElement(name=EmptyTokenStored.XML_NAME)
	@XmlElementWrapper(name="emptytoks")
	List<EmptyTokenStored> emptytoks;
	
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
