/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.lx.xb;

import de.tuebingen.uni.sfs.wlf1.lx.api.Lemma;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name=LemmaStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class LemmaStored implements Lemma {
	
	public static final String XML_NAME = "lemma";
	public static final String ID_PREFIX = "l_";
	
	@XmlValue
	protected String lemmaString;
	@XmlAttribute(name=CommonAttributes.ID, required = true)
	protected String lemmaId;
	
	
	@Override
	public String getString() {
		return lemmaString;
	}
	
	@Override
	public String getID() {
		return lemmaId;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(lemmaId);
		sb.append(" -> ");
		sb.append(lemmaString);
		return sb.toString();
	}

}


