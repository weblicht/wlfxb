/**
 *
 */
package de.tuebingen.uni.sfs.jaxbtest;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = "fs")
@XmlAccessorType(XmlAccessType.NONE)
public class Fs {

    @XmlElement(name = "f", type = Ft.class)
    List<Ft> fts;

    public String toString() {
        return fts.toString();
    }
}
