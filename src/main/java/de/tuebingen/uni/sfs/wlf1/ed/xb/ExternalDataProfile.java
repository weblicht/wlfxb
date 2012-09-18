/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.ed.xb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name=ExternalDataStored.XML_NAME, namespace=ExternalDataStored.XML_NAMESPACE)
@XmlAccessorType(XmlAccessType.NONE)
public class ExternalDataProfile {
	
	
	@Override
	public String toString() {
		return ExternalDataStored.XML_NAME;
	}

}
