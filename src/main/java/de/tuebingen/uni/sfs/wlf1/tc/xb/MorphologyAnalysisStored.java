/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import de.tuebingen.uni.sfs.wlf1.tc.api.Feature;
import de.tuebingen.uni.sfs.wlf1.tc.api.MorphologyAnalysis;
import de.tuebingen.uni.sfs.wlf1.tc.api.MorphologySegment;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name=MorphologyAnalysisStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class MorphologyAnalysisStored implements MorphologyAnalysis {
	
	public static final String XML_NAME = "analysis";

	@XmlAttribute(name=CommonAttributes.TOKEN_SEQUENCE_REFERENCE)
	String[] tokRefs;
	@XmlElement(name=MorphologyTagStored.XML_NAME)
	MorphologyTagStored tag;

	@XmlElement(name=MorphologySegmentStored.XML_NAME)
	@XmlElementWrapper(name="segmentation")
	List<MorphologySegmentStored> segments;
	
	
	@Override
	public Feature[] getFeatures() {
		return tag.fs.getFeatures();
	}
	
	
	@Override
	public MorphologySegment[] getSegmentation() {
		if (segments == null) {
			return null;
		} else {
			return segments.toArray(new MorphologySegment[segments.size()]);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Arrays.toString(tokRefs));
		sb.append("( ");
		sb.append(tag.toString());
		sb.append(" )");
		if (segments != null) {
		sb.append("[ ");
		sb.append(segments.toString());
		sb.append(" ]");
		}
		return sb.toString();
	}
	
}
