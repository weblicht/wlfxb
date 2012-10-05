/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.xb;

import eu.clarin.weblicht.wlfxb.tc.api.TextSpan;
import eu.clarin.weblicht.wlfxb.tc.api.TextSpanType;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = TextSpanStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class TextSpanStored implements TextSpan {

    public static final String XML_NAME = "textspan";
    @XmlAttribute(name = CommonAttributes.START_TOKEN)
    protected String startToken;
    @XmlAttribute(name = CommonAttributes.END_TOKEN)
    protected String endToken;
    @XmlAttribute(name = CommonAttributes.TYPE)
    protected TextSpanType type;

    @Override
    public TextSpanType getType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(type.name());
        if (startToken != null && endToken != null) {
            sb.append(" ");
            sb.append(startToken);
            sb.append(" - ");
            sb.append(endToken);
        }
        return sb.toString();
    }
}
