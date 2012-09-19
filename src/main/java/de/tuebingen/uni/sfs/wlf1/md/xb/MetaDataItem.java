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
    private String value;
    @XmlAttribute
    private String name;

    public MetaDataItem() {
    }

    protected MetaDataItem(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + "=" + value;
    }
}
