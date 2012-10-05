/**
 * 
 */
package eu.clarin.weblicht.wlfxb.jaxbtest;

import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name="elo")
@XmlAccessorType(XmlAccessType.NONE)
public class ElObect {

	@XmlAttribute
	String eloname;
	@XmlElement
	ObjWithMixedContent o;
	
	public String toString() {
		return eloname + " (" + o + ")";
	}
}
