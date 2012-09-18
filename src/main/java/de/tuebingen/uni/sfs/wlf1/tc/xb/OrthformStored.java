/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import de.tuebingen.uni.sfs.wlf1.tc.api.Orthform;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;

/**
 * @author Yana Panchenko
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class OrthformStored implements Orthform {
	
	public static final String XML_NAME = "orthform";
	
	@XmlValue
	String values;
	@XmlAttribute(name=CommonAttributes.ID)
	String id;
	@XmlAttribute(name=CommonAttributes.NONCONSECUTIVE_LEMMAS_REFERENCE, required = true)
	String[] lemmaRefs;
	
	


	@Override
	public String[] getValue() {
		String[] splittedValues = values.split(",[ ]*");
		return splittedValues;
	}
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (id != null) {
			sb.append(id);
			sb.append(" -> ");
		}
		sb.append("(" + values + " " + Arrays.toString(lemmaRefs) + ")");
		return sb.toString();
	}

}
