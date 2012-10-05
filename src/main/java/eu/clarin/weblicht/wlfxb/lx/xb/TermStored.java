package eu.clarin.weblicht.wlfxb.lx.xb;

import eu.clarin.weblicht.wlfxb.lx.api.Term;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = TermStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class TermStored implements Term {

    public static final String XML_NAME = "term";
    @XmlAttribute(name = CommonAttributes.LEMMA_REFERENCE)
    protected String lemId;
    @XmlValue
    protected String word;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (lemId != null) {
            sb.append(lemId);
        } else if (word != null) {
            sb.append(word);
        }
        return sb.toString();
    }
}
