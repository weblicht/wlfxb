package de.tuebingen.uni.sfs.wlf1.lx.xb;

import de.tuebingen.uni.sfs.wlf1.lx.api.Relation;
import de.tuebingen.uni.sfs.wlf1.lx.api.Sig;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = RelationStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class RelationStored implements Relation {

    public static final String XML_NAME = "word-relation";
    @XmlAttribute(name = CommonAttributes.TYPE)
    String type;
    @XmlAttribute(name = CommonAttributes.FUNCTION)
    String function;
    @XmlAttribute(name = "freq")
    Integer freq;
    @XmlElement
    SigStored sig;
    @XmlElement(name = TermStored.XML_NAME)
    List<TermStored> terms = new ArrayList<TermStored>();

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getFunction() {
        return function;
    }

    @Override
    public Integer getFrequency() {
        return freq;
    }

    @Override
    public Sig getSig() {
        return sig;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (type != null) {
            sb.append(type);
            sb.append(" ");
        }
        if (function != null) {
            sb.append(function);
            sb.append(" ");
        }
        if (freq != null) {
            sb.append(freq + " ");
        }
        if (sig != null) {
            sb.append(sig);
            sb.append(" ");
        }
        sb.append(terms.toString());
        return sb.toString();
    }
}
