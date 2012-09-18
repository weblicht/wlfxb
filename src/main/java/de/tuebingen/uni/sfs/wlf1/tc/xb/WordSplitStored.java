/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import de.tuebingen.uni.sfs.wlf1.tc.api.WordSplit;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;

/**
 * @author Yana Panchenko
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class WordSplitStored implements WordSplit {
	
	public static final String XML_NAME = "split";
	
	@XmlValue
	int[] splitIndices;
	@XmlAttribute(name=CommonAttributes.TOKEN_REFERENCE, required = true)
	String tokRef;
	

	@Override
	public int[] getIndices() {
		return splitIndices;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(tokRef);
		sb.append(" ");
		sb.append(Arrays.toString(splitIndices));
		return sb.toString();
	}


}
