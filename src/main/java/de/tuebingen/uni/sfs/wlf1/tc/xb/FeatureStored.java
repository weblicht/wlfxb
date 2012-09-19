/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.Feature;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = FeatureStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class FeatureStored implements Feature {

    public static final String XML_NAME = "f";
    @XmlAttribute(name = CommonAttributes.NAME, required = true)
    protected String name;
    // temporary to hold unmarshalled objects before I can transfer them to fs or value
    private List<Object> content = new ArrayList<Object>();
    protected FeatureStructureStored fs;
    protected String value;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isTerminal() {
        if (fs == null) {
            return true;
        }
        return false;
    }

    @Override
    public Feature[] getSubfeatures() {
        return fs.getFeatures();
    }

    @Override
    public String getValue() {
        return value;
    }

    @XmlMixed
    @XmlElementRefs({
        @XmlElementRef(name = FeatureStructureStored.XML_NAME, type = FeatureStructureStored.class),})
    protected List<Object> getContent() {
        List<Object> content = new ArrayList<Object>();
        if (fs != null) {
            content.add(fs);
        } else if (value != null) {
            content.add(value);
        } else {
            return null;
        }
        return content;
    }

    void setContent(List<Object> content) {
        this.content = content;
    }

    protected void afterUnmarshal(Unmarshaller u, Object parent) {
        for (Object obj : content) {
            if (obj instanceof String) {
                String v = ((String) obj).trim();
                if (fs == null && v.length() > 0) {
                    value = v;
                    return;
                }
            } else if (obj instanceof FeatureStructureStored) {
                fs = (FeatureStructureStored) obj;
                return;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" ");
        if (isTerminal()) {
            sb.append(value);
        } else {
            sb.append(fs.toString());
        }
        return sb.toString();

    }
}
