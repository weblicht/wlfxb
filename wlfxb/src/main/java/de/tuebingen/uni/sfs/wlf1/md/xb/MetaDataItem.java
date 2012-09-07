/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.md.xb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlType;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = "md")
@XmlAccessorType(XmlAccessType.FIELD)
public class MetaDataItem {

    @XmlAttribute
    public String value;
    @XmlAttribute
    public String name;

    @Override
    public String toString() {
        return name + "=" + value;
    }
}
