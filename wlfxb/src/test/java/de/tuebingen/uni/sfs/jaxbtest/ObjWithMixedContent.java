/**
 *
 */
package de.tuebingen.uni.sfs.jaxbtest;

import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = "o")
@XmlAccessorType(XmlAccessType.NONE)
public class ObjWithMixedContent {

    @XmlAttribute(name = CommonAttributes.NAME, required = true)
    String name;
    @XmlMixed
    @XmlElementRefs({
        @XmlElementRef(name = "elo", type = ElObect.class),})
    List<Object> els;

    public String toString() {
        return name + " " + els;
    }
}
