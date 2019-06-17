/**
 * wlfxb - a library for creating and processing of TCF data streams.
 *
 * Copyright (C) University of Tübingen.
 *
 * This file is part of wlfxb.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.xb;


import eu.clarin.weblicht.wlfxb.tc.api.TopologicalField;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;

import javax.xml.bind.annotation.*;
import javax.xml.namespace.QName;
import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 * @author Neele Witte
 *
 */
@XmlRootElement(name = TopologicalFieldStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class TopologicalFieldStored implements TopologicalField {

    public static final String XML_NAME = "field";
    @XmlValue
    protected String tagString;
    @XmlAttribute(name = CommonAttributes.ID)
    protected String tagId;
    @XmlAttribute(name = CommonAttributes.TOKEN_SEQUENCE_REFERENCE, required = true)
    protected String[] tokRefs;
    @XmlAnyAttribute
    protected LinkedHashMap<QName, String> extraAttributes = new LinkedHashMap<QName, String>();
  
    @Override
    public LinkedHashMap<String, String> getExtraAttributes() {
        return TopologicalField.super.retrieveAttributes(extraAttributes);
    }

    @Override
    public String getString() {
        return tagString;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (tagId != null) {
            sb.append(tagId);
            sb.append(" -> ");
        }
        sb.append(this.tagString).append(" ").append(Arrays.toString(tokRefs));
        return sb.toString();
    }
}
