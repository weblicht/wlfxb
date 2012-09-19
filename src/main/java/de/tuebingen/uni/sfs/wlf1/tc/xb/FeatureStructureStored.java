/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.Feature;
import de.tuebingen.uni.sfs.wlf1.tc.api.FeatureStructure;
import java.util.ArrayList;
import java.util.List;
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
public class FeatureStructureStored implements FeatureStructure {

    public static final String XML_NAME = "fs";
    @XmlElement(name = FeatureStored.XML_NAME, type = FeatureStored.class)
    protected List<FeatureStored> features = new ArrayList<FeatureStored>();

    @Override
    public Feature[] getFeatures() {
        return features.toArray(new Feature[features.size()]);
    }

    @Override
    public String toString() {
        return features.toString();
    }
}
