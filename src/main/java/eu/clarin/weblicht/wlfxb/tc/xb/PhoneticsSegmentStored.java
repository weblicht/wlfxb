/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.xb;

import eu.clarin.weblicht.wlfxb.tc.api.PhoneticsSegment;
import eu.clarin.weblicht.wlfxb.tc.api.Pronunciation;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = PhoneticsSegmentStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class PhoneticsSegmentStored implements PhoneticsSegment {

    public static final String XML_NAME = "phonseg";
    @XmlAttribute(name = CommonAttributes.TOKEN_REFERENCE)
    protected String tokRef;
    @XmlElement(name = PronunciationStored.XML_NAME, required = true)
    protected List<PronunciationStored> prons = new ArrayList<PronunciationStored>();

    @Override
    public Pronunciation[] getPronunciations() {
        return prons.toArray(new Pronunciation[prons.size()]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(tokRef);
        sb.append(" ");
        sb.append(prons.toString());
        return sb.toString();
    }
}
