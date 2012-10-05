/**
 *
 */
package eu.clarin.weblicht.wlfxb.md.xb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.annotation.*;

//import org.w3c.dom.Node;
/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = MetaData.XML_NAME, namespace = MetaData.XML_NAMESPACE)
@XmlAccessorType(XmlAccessType.FIELD)
public class MetaData {

    public static final String XML_NAME = "MetaData";
    public static final String XML_NAMESPACE = "http://www.dspin.de/data/metadata";
    @XmlElements({
        @XmlElement(name = "md", type = MetaDataItem.class)})
    private List<MetaDataItem> metaDataItems = new ArrayList<MetaDataItem>();

    public void addMetaDataItem(String name, String value) {
        metaDataItems.add(new MetaDataItem(name, value));
    }

    public List<MetaDataItem> getMetaDataItems() {
        return Collections.unmodifiableList(metaDataItems);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(XML_NAME);
        sb.append(":\n");
        sb.append(metaDataItems.toString());
        return sb.toString();
    }
}
