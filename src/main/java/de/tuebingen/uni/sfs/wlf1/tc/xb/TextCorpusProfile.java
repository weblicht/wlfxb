/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name=TextCorpusStored.XML_NAME, namespace=TextCorpusStored.XML_NAMESPACE)
@XmlAccessorType(XmlAccessType.NONE)
public class TextCorpusProfile {
	
	@XmlAttribute
	public String lang;

	
	@Override
	public String toString() {
		return TextCorpusStored.XML_NAME + " " + lang;
	}

}
