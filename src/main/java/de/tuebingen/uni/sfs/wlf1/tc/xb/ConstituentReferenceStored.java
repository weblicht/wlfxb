/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import de.tuebingen.uni.sfs.wlf1.tc.api.ConstituentReference;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name=ConstituentReferenceStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class ConstituentReferenceStored implements ConstituentReference {
	
	public static final String XML_NAME = "cref";
	public static final String XML_ATTRIBUTE_EDGE_LABEL = "edge";
	public static final String XML_ATTRIBUTE_CREF = "constID";

	@XmlAttribute(name=XML_ATTRIBUTE_EDGE_LABEL, required = true)
	String edge;
	@XmlAttribute(name=XML_ATTRIBUTE_CREF, required = true)
	String constId;
	
	ConstituentReferenceStored() {
	}
	
	ConstituentReferenceStored(ConstituentStored cref, String edgeLabel) {
		this.edge = edgeLabel;
		this.constId = cref.constituentId;
	}

	@Override
	public String getEdgeLabel() {
		return edge;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(edge);
		sb.append(" -> ");
		sb.append(constId);
		return sb.toString();
	}

	
}
