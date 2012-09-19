package de.tuebingen.uni.sfs.wlf1.lx.xb;

import de.tuebingen.uni.sfs.wlf1.lx.api.Sig;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = SigStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class SigStored implements Sig {

    public static final String XML_NAME = "sig";
    @XmlAttribute(name = "measure")
    private String measure;
    @XmlValue
    private double value;

    SigStored() {
    }

    SigStored(String measure, double value) {
        this.measure = measure;
        this.value = value;
    }

    @Override
    public String getMeasure() {
        return measure;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (measure != null) {
            sb.append(measure);
            sb.append(" ");
        }
        sb.append("").append(value);
        return sb.toString();
    }
}
