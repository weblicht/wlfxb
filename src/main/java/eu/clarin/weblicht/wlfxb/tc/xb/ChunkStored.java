/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.clarin.weblicht.wlfxb.tc.xb;

import eu.clarin.weblicht.wlfxb.tc.api.Chunk;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;
import java.util.Arrays;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author felahi
 */
@XmlRootElement(name = ChunkStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class ChunkStored implements Chunk {

    public static final String XML_NAME = "chunk";
    @XmlAttribute(name = CommonAttributes.ID)
    protected String id;
    @XmlAttribute(name = "type")
    protected String type;
    @XmlAttribute(name = CommonAttributes.TOKEN_SEQUENCE_REFERENCE, required = true)
    protected String[] tokRefs;

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (id != null) {
            sb.append(id);
            sb.append(" -> ");
        }
        sb.append(type);
        sb.append(" ");
        sb.append(Arrays.toString(tokRefs));
        return sb.toString();
    }
}
