/**
 *
 */
package de.tuebingen.uni.sfs.jaxbtest;

import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = "f")
@XmlAccessorType(XmlAccessType.NONE)
public class Ft {

    @XmlAttribute
    String name;
//	@XmlMixed @XmlElementRefs({
//		@XmlElementRef(name="fs", type=Fs.class),
//	})
    List<Object> fsOrString;

    public String toString() {
        return name + " (" + fsOrString.toString() + ")";
    }

    @XmlMixed
    @XmlElementRefs({
        @XmlElementRef(name = "fs", type = Fs.class),})
    protected List<Object> getContent() {
        return fsOrString;
    }

    protected void setContent(List<Object> content) {
        this.fsOrString = content;
    }
}
