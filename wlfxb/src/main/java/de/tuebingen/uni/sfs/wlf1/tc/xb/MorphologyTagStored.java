/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = FeatureStructureStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class MorphologyTagStored {

    public static final String XML_NAME = "tag";
    @XmlElement(name = FeatureStructureStored.XML_NAME)
    FeatureStructureStored fs;

    @Override
    public String toString() {
        return fs.toString();
    }
}
