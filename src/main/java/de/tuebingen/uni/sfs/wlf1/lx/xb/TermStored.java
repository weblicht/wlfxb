package de.tuebingen.uni.sfs.wlf1.lx.xb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import de.tuebingen.uni.sfs.wlf1.lx.api.Term;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;

@XmlRootElement(name=TermStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class TermStored implements Term {
	
	public static final String XML_NAME = "term";
	
	@XmlAttribute(name=CommonAttributes.LEMMA_REFERENCE)
	String lemId;
	@XmlValue
	String word;
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (lemId != null) {
			sb.append(lemId);
		} else if (word != null) {
			sb.append(word);
		}
		return sb.toString();
	}

}
