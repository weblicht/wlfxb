/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name=TokenStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder={"end", "start", "tokenId"})
public class TokenStored implements Token {
	
	public static final String XML_NAME = "token";
	public static final String ID_PREFIX = "t_";
	
	@XmlValue
	String tokenString;
	@XmlAttribute(name=CommonAttributes.ID, required = true)
	String tokenId; //TODO: see if it makes sense to store order instead of tokenID, 
	//and in connector to store tokenIDs and lists where index correspond to order instead of maps... to ... instead of Token to ... 
	@XmlAttribute(name=CommonAttributes.START_CHAR_OFFSET)
	Long start;
	//@XmlAttribute(name=CommonAttributes.END_CHAR_OFFSET)
	//Long end;
	
	int order;
	
	  
	@Override
	public String getString() {
		return tokenString;
	}
	
	@Override
	public String getID() {
		return tokenId;
	}
	
	@Override
	public int getOrder() {
		return order;
	}

	@Override
	public Long getStart() {
		return start;
	}

	@Override
	@XmlAttribute(name=CommonAttributes.END_CHAR_OFFSET)
	public Long getEnd() {
		if (start != null) {
			return start + tokenString.length();
		}
		return null;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(order + ": " + tokenId);
		sb.append(" -> ");
		sb.append(tokenString);
		if (start != null) {
			sb.append(" (");
			sb.append(start);
			sb.append("-");
			sb.append(getEnd());
			sb.append(")");
		}
		return sb.toString();
	}

}


