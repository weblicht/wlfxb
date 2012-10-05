/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.xb;

import eu.clarin.weblicht.wlfxb.tc.api.CorrectionOperation;
import eu.clarin.weblicht.wlfxb.tc.api.OrthCorrection;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;
import java.util.Arrays;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = OrthCorrectionStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class OrthCorrectionStored implements OrthCorrection {

    public static final String XML_NAME = "correction";
    @XmlValue
    protected String corrString;
    @XmlAttribute(name = CommonAttributes.TOKEN_SEQUENCE_REFERENCE, required = true)
    protected String[] tokRefs;
    @XmlAttribute(name = "operation", required = true)
    protected CorrectionOperation operation;

    @Override
    public String getString() {
        return corrString;
    }

    @Override
    public CorrectionOperation getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(corrString).append(" - ").append(operation.name()).append(Arrays.toString(tokRefs));
        return sb.toString();
    }
}
