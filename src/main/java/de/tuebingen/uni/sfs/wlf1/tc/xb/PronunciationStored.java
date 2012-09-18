/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.tuebingen.uni.sfs.wlf1.tc.api.Pronunciation;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name=PronunciationStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class PronunciationStored implements Pronunciation {
	
	public static final String XML_NAME = "pron";

	@XmlAttribute(name=CommonAttributes.TYPE, required = true)
	PronunciationType type;
	@XmlAttribute(name="onset")
	Float onset;
	@XmlAttribute(name="offset")
	Float offset;
	@XmlAttribute(name="cp")
	String cp;
	@XmlAttribute(name="rp")
	String rp;
	@XmlElement(name=PronunciationStored.XML_NAME)
	List<PronunciationStored> children = new ArrayList<PronunciationStored>();
	

	@Override
	public PronunciationType getType() {
		return type;
	}


	@Override
	public String getCanonical() {
		return cp;
	}

	@Override
	public String getRealized() {
		return rp;
	}

	@Override
	public Float getOnsetInSeconds() {
		return onset;
	}

	@Override
	public Float getOffsetInSeconds() {
		return offset;
	}


	@Override
	public boolean hasChildren() {
		return !children.isEmpty();
	}
	
	@Override
	public boolean hasOnOffsets() {
		return (onset != null && offset != null);
	}

	@Override
	public Pronunciation[] getChildren() {
		return children.toArray(new Pronunciation[children.size()]);
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(type);
		if (cp != null) {
			sb.append(" ");
			sb.append(cp);
		}
		if (rp != null) {
			sb.append(" ");
			sb.append(rp);
		}
		if (hasOnOffsets()) {
			sb.append(" " + onset);
			sb.append(" " + offset);
		}
		if (hasChildren()) {
			sb.append(" ( ");
			sb.append(children.toString());
			sb.append(" )");
		}
		return sb.toString();
	}
	
}
