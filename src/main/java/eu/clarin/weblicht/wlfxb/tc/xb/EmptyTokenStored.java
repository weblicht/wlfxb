/**
 * 
 */
package eu.clarin.weblicht.wlfxb.tc.xb;

import eu.clarin.weblicht.wlfxb.tc.api.Token;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author Yana Panchenko
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class EmptyTokenStored implements Token {
	
	
	public static final String XML_NAME = "emptytok";
	public static final String ID_PREFIX = "et_";
	
	@XmlValue
	protected String tokenString;
	@XmlAttribute(name=CommonAttributes.ID, required = true)
	protected String id;
	protected int order;
	
	
	@Override
	public String getString() {
            return tokenString;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(order).append(": ").append(id).append(" ").append(tokenString);
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
