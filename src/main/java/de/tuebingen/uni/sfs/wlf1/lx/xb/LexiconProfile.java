/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.lx.xb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name=LexiconStored.XML_NAME, namespace=LexiconStored.XML_NAMESPACE)
@XmlAccessorType(XmlAccessType.NONE)
public class LexiconProfile {
	
	@XmlAttribute
	public String lang;

	
	@Override
	public String toString() {
		return LexiconStored.XML_NAME + " " + lang;
	}

}
