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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.clarin.weblicht.wlfxb.tc.xb;

import eu.clarin.weblicht.wlfxb.tc.api.Chunk;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;
import java.util.Arrays;
import java.util.LinkedHashMap;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

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
    @XmlAnyAttribute
    protected LinkedHashMap<QName, String> attributes = new LinkedHashMap<QName, String>();
    @XmlAttribute(name = CommonAttributes.TOKEN_SEQUENCE_REFERENCE, required = true)
    protected String[] tokRefs;
    protected LinkedHashMap<String, String> types = new LinkedHashMap<String, String>();

    public LinkedHashMap<QName, String> getAttributes(LinkedHashMap<String, String> types) {
        LinkedHashMap<QName, String> attributes = new LinkedHashMap<QName, String>();
        for (String type : types.keySet()) {
            QName qname = new QName(type);
            attributes.put(qname, types.get(type));
        }
        return attributes;
    }

    @Override
    public LinkedHashMap<String, String> getTypes() {
        for (QName qName : attributes.keySet()) {
            types.put(qName.toString(), attributes.get(qName).toString());
        }
        return types;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (id != null) {
            sb.append(id);
            sb.append(" -> ");
        }
        sb.append(types.toString());
        sb.append(" ");
        sb.append(Arrays.toString(tokRefs));
        return sb.toString();
    }

}
