package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.TextLayer;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = TextLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class TextLayerStored extends TextCorpusLayerStoredAbstract implements TextLayer {

    @XmlValue
    String text;
    public static final String XML_NAME = "text";

    protected TextLayerStored() {
        this.text = "";
    }

    protected void setLayersConnector(TextCorpusLayersConnector connector) {
        // doesn't need connector
    }

    @Override
    public boolean isEmpty() {
        if (text == null || text.length() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void addText(String text) {
        if (this.text == null) {
            this.text = text;
        } else {
            this.text = this.text + text;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(XML_NAME);
        sb.append(" : ");
        sb.append(text);
        return sb.toString();
    }

    @Override
    public int size() {
        return text.length();
    }
//	@XmlElement(name=TextSegmentStored.XML_NAME, type=TextSegmentStored.class)
//	List<TextSegment> textSegments = new ArrayList<TextSegment>(); 
//
//	
//	public List<TextSegment> getTextSegments() {
//		return textSegments;
//	}
//
//
//	public LayerProfile getProfile() {
//		LayerProfile profile = new LayerProfile();
//		return profile;
//	}
//	
//	@Override
//	public String toString() {
//		StringBuilder sb = new StringBuilder(XML_NAME);
//		sb.append(" ");
//		sb.append(getProfile().toString());
//		sb.append(": ");
//		sb.append(textSegments.toString());
//		return sb.toString();
//	}
}
