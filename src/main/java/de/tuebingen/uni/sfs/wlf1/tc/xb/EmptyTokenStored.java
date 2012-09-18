/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;

/**
 * @author Yana Panchenko
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class EmptyTokenStored implements Token {
	
	
	public static final String XML_NAME = "emptytok";
	public static final String ID_PREFIX = "et_";
	
	@XmlValue
	String tokenString;
	@XmlAttribute(name=CommonAttributes.ID, required = true)
	String id;
	int order;
	
	
	@Override
	public String getString() {
		return tokenString;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(order + ": " + id + " " + tokenString );
		return sb.toString();
	}



	@Override
	public String getID() {
		return id;
	}


	@Override
	public Long getStart() {
		return null;
	}


	@Override
	public Long getEnd() {
		return null;
	}
	
	public int getOrder() {
		return order;
	}

}
