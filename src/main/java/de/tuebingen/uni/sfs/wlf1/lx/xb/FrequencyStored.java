/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.lx.xb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import de.tuebingen.uni.sfs.wlf1.lx.api.Frequency;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;

/**
 * @author Yana Panchenko
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class FrequencyStored implements Frequency {
	
	public static final String XML_NAME = "frequency";
	
	@XmlValue
	int value;
	@XmlAttribute(name=CommonAttributes.LEMMA_REFERENCE, required = true)
	String lemRef;
	
	@Override
	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(lemRef);
		sb.append(" " + value);
		return sb.toString();
	}
}

