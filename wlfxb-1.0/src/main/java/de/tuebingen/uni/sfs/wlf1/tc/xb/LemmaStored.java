/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.Lemma;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import java.util.Arrays;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = LemmaStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class LemmaStored implements Lemma {

    public static final String XML_NAME = "lemma";
    public static final String ID_PREFIX = "l_";
    @XmlValue
    String lemmaString;
    @XmlAttribute(name = CommonAttributes.ID)
    String lemmaId;
    @XmlAttribute(name = CommonAttributes.CONSECUTIVE_TOKENS_REFERENCE, required = true)
    String[] tokRefs;

    @Override
    public String getString() {
        return lemmaString;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (lemmaId != null) {
            sb.append(lemmaId);
            sb.append(" -> ");
        }
        sb.append(lemmaString + " " + Arrays.toString(tokRefs));
        return sb.toString();
    }
}
