/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.lx.xb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import de.tuebingen.uni.sfs.wlf1.lx.api.PosTag;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;

/**
 * @author Yana Panchenko
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class PosTagStored implements PosTag {
	
	public static final String XML_NAME = "tag";
	//public static final String ID_PREFIX = "p_";
	
	//TODO rename attributes!!!
	@XmlValue
	String tagString;
	@XmlAttribute(name=CommonAttributes.ID)
	String tagId;
	@XmlAttribute(name=CommonAttributes.LEMMA_REFERENCE, required = true)
	String lemRef;
	
	@Override
	public String getString() {
		return tagString;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (tagId != null) {
			sb.append(tagId);
			sb.append(" -> ");
		}
		sb.append(tagString + "[" + lemRef + "]");
		return sb.toString();
	}
}

