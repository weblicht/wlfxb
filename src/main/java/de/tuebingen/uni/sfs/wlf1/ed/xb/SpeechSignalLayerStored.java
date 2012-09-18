package de.tuebingen.uni.sfs.wlf1.ed.xb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import de.tuebingen.uni.sfs.wlf1.ed.api.SpeechSignalLayer;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name=SpeechSignalLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class SpeechSignalLayerStored extends ExternalDataLayerStored implements SpeechSignalLayer {
	
	@XmlAttribute(name="numberchannels")
	Integer numberOfChannels;
	
	public static final String XML_NAME = "speechsignal";
	
	protected SpeechSignalLayerStored() {
		super();
	}
	
	protected SpeechSignalLayerStored(String mimeType, Integer numberOfChannels) {
		super(mimeType);
		this.numberOfChannels = numberOfChannels;
	}
	
	protected SpeechSignalLayerStored(int numberOfChannels) {
		super();
		this.numberOfChannels = numberOfChannels;
	}
	

	@Override
	public Integer getNumberOfChannels() {
		return numberOfChannels;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(XML_NAME);
		sb.append(" : ");
		sb.append(super.getDataMimeType());
		if (numberOfChannels != null) {
			sb.append(" " + numberOfChannels);
		}
		sb.append(" ");
		sb.append(super.getLink());
		return sb.toString();
	}

}
