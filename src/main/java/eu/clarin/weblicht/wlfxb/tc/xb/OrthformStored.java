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

import eu.clarin.weblicht.wlfxb.tc.api.Orthform;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;
import java.util.Arrays;
import java.util.LinkedHashMap;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;

/**
 * @author Yana Panchenko
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class OrthformStored implements Orthform {

    public static final String XML_NAME = "orthform";
    @XmlValue
    protected String values;
    @XmlAttribute(name = CommonAttributes.ID)
    protected String id;
    @XmlAttribute(name = CommonAttributes.NONCONSECUTIVE_LEMMAS_REFERENCE, required = true)
    protected String[] lemmaRefs;
    @XmlAnyAttribute
    protected LinkedHashMap<QName, String> extraAttributes = new LinkedHashMap<QName, String>();

    @Override
    public String[] getValue() {
        String[] splittedValues = values.split(",[ ]*");
        return splittedValues;
    }

    @Override
    public LinkedHashMap<String, String> getExtraAttributes() {
       return Orthform.super.retrieveAttributes(extraAttributes);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (id != null) {
            sb.append(id);
            sb.append(" -> ");
        }
        sb.append("(").append(values).append(" ").append(Arrays.toString(lemmaRefs)).append(")");
        return sb.toString();
    }

}
