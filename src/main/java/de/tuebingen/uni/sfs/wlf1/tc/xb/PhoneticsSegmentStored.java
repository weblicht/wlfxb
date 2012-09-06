/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.PhoneticsSegment;
import de.tuebingen.uni.sfs.wlf1.tc.api.Pronunciation;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
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
    String tokRef;
    @XmlElement(name = PronunciationStored.XML_NAME, required = true)
    List<PronunciationStored> prons = new ArrayList<PronunciationStored>();

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
