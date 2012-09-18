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

import de.tuebingen.uni.sfs.wlf1.tc.api.Reference;
import de.tuebingen.uni.sfs.wlf1.tc.api.ReferencedEntity;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name=ReferencedEntityStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class ReferencedEntityStored implements ReferencedEntity {
	
	public static final String XML_NAME = "entity";

	@XmlAttribute(name=CommonAttributes.ID)
	String id;
	@XmlElement(name="extref")
	ExternalReferenceStored externalRef;
	@XmlElement(name=ReferenceStored.XML_NAME)
	List<ReferenceStored> references = new ArrayList<ReferenceStored>();
	
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (id != null) {
			sb.append(id);
			sb.append(" -> ");
		}
		if (externalRef != null) {
			sb.append(" ");
			sb.append(externalRef.toString());
		}
		sb.append(references.toString());
		return sb.toString();
	}


	@Override
	public String getExternalId() {
		if (this.externalRef != null) {
			return this.externalRef.refid;
		}
		return null;
	}

	
	@Override
	public Reference[] getReferences() {
		if (!references.isEmpty()) {
			return references.toArray(new Reference[references.size()]);
		}
		return null;
	}
	
	

}
