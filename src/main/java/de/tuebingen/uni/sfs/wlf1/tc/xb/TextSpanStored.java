/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import de.tuebingen.uni.sfs.wlf1.tc.api.TextSpan;
import de.tuebingen.uni.sfs.wlf1.tc.api.TextSpanType;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = TextSpanStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class TextSpanStored implements TextSpan {
	
	public static final String XML_NAME = "textspan";
	
	@XmlAttribute(name=CommonAttributes.START_TOKEN)
	String startToken;
	@XmlAttribute(name=CommonAttributes.END_TOKEN)
	String endToken;
	@XmlAttribute(name=CommonAttributes.TYPE)
	TextSpanType type;


	@Override
	public TextSpanType getType() {
		return type;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(type.name());
		if (startToken != null && endToken != null) {
			sb.append(" ");
			sb.append(startToken);
			sb.append(" - ");
			sb.append(endToken);
		}
		return sb.toString();
	}

}
