/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.Dependency;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import java.util.Arrays;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Yana Panchenko
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class DependencyStored implements Dependency {
	
	public static final String XML_NAME = "dependency";
	public static final String XML_ATTR_DEPENDENT_REFERENCE = "depIDs";
	public static final String XML_ATTR_GOVERNOR_REFERENCE = "govIDs";
	
	
	@XmlAttribute(name=CommonAttributes.FUNCTION)
	protected String function;
	@XmlAttribute(name=XML_ATTR_DEPENDENT_REFERENCE, required = true)
	protected String[] depIds;
	@XmlAttribute(name=XML_ATTR_GOVERNOR_REFERENCE)
	protected String[] govIds;
	
	@Override
	public String getFunction() {
		return function;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (function != null) {
			sb.append(function);
			sb.append(" ");
		}
		sb.append(Arrays.toString(depIds)).append(" <- ");
		if (govIds == null) {
			sb.append("[ ]");
		} else {
			sb.append(Arrays.toString(govIds));
		}
		return sb.toString();
	}

}
