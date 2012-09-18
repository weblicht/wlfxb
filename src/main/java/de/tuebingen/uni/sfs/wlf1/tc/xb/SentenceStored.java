/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import de.tuebingen.uni.sfs.wlf1.tc.api.Sentence;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name=SentenceStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder={"tokIds", "end", "start"})
public class SentenceStored implements Sentence {
	
	public static final String XML_NAME = "sentence";
	
	
	@XmlAttribute(name=CommonAttributes.ID)
	String sentenceId;
	@XmlAttribute(name=CommonAttributes.TOKEN_SEQUENCE_REFERENCE, required = true)
	String[] tokIds;
	@XmlAttribute(name=CommonAttributes.START_CHAR_OFFSET)
	Integer start;
	@XmlAttribute(name=CommonAttributes.END_CHAR_OFFSET)
	Integer end;

	@Override
	public Integer getStartCharOffset() {
		return start;
	}

	@Override
	public Integer getEndCharOffset() {
		return end;
	}
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (sentenceId != null) {
			sb.append(sentenceId);
			sb.append(" -> ");
		}
		sb.append(Arrays.toString(tokIds));
		if (start != null && end != null) {
			sb.append(" (");
			sb.append(start);
			sb.append("-");
			sb.append(end);
			sb.append(")");
		}
		return sb.toString();
	}

}
