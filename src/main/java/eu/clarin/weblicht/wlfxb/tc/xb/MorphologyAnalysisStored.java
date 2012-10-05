/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.xb;

import eu.clarin.weblicht.wlfxb.tc.api.Feature;
import eu.clarin.weblicht.wlfxb.tc.api.MorphologyAnalysis;
import eu.clarin.weblicht.wlfxb.tc.api.MorphologySegment;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = MorphologyAnalysisStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class MorphologyAnalysisStored implements MorphologyAnalysis {

    public static final String XML_NAME = "analysis";
    @XmlAttribute(name = CommonAttributes.TOKEN_SEQUENCE_REFERENCE)
    protected String[] tokRefs;
    @XmlElement(name = MorphologyTagStored.XML_NAME)
    protected MorphologyTagStored tag;
    @XmlElement(name = MorphologySegmentStored.XML_NAME)
    @XmlElementWrapper(name = "segmentation")
    protected List<MorphologySegmentStored> segments;

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
