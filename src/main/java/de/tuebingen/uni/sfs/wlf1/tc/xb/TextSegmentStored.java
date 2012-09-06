/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.TextSegment;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = TextSegmentStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class TextSegmentStored implements TextSegment {

    public static final String XML_NAME = "tseg";
    @XmlValue
    String text;

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
