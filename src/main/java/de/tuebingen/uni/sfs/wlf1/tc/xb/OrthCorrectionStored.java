/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import de.tuebingen.uni.sfs.wlf1.tc.api.CorrectionOperation;
import de.tuebingen.uni.sfs.wlf1.tc.api.OrthCorrection;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name=OrthCorrectionStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class OrthCorrectionStored implements OrthCorrection {
	
	public static final String XML_NAME = "correction";
	
	@XmlValue
	String corrString;
	@XmlAttribute(name=CommonAttributes.TOKEN_SEQUENCE_REFERENCE, required = true)
	String[] tokRefs;
	@XmlAttribute(name="operation", required = true)
	CorrectionOperation operation;
	
	@Override
	public String getString() {
		return corrString;
	}
	
	@Override
	public CorrectionOperation getOperation() {
		return operation;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(corrString + " - " + operation.name() + Arrays.toString(tokRefs));
		return sb.toString();
	}
}

