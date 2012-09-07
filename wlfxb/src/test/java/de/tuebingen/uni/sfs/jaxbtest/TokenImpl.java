package de.tuebingen.uni.sfs.jaxbtest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.NONE)
public class TokenImpl implements TokenI {

    @XmlAttribute(name = "ID")
    String id;
    String tString;

    @Override
    @XmlValue
    public String getString() {
        return tString;
    }

    @Override
    public void setString(String tString) {
        this.tString = tString;
    }

    @Override
    public String toString() {
        return id + " -> " + tString;
    }
}
