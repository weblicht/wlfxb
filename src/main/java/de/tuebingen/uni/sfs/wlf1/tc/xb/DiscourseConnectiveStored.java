/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import de.tuebingen.uni.sfs.wlf1.tc.api.DiscourseConnective;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name=DiscourseConnectiveStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class DiscourseConnectiveStored implements DiscourseConnective {
	
	public static final String XML_NAME = "connective";

	@XmlAttribute(name="type")
	String type;
	@XmlAttribute(name=CommonAttributes.TOKEN_SEQUENCE_REFERENCE, required = true)
	String[] tokRefs;
	
	

	@Override
	public String getType() {
		return type;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (type != null) {
			sb.append(type);
			sb.append(" ");
		}
		sb.append(Arrays.toString(tokRefs));
		return sb.toString();
	}

}

