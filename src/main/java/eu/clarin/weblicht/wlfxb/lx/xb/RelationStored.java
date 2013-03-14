/**
 * wlfxb - a library for creating and processing of TCF data streams.
 *
 * Copyright (C) Yana Panchenko.
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
package eu.clarin.weblicht.wlfxb.lx.xb;

import eu.clarin.weblicht.wlfxb.lx.api.Relation;
import eu.clarin.weblicht.wlfxb.lx.api.Sig;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = RelationStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class RelationStored implements Relation {

    public static final String XML_NAME = "word-relation";
    @XmlAttribute(name = CommonAttributes.TYPE)
    protected String type;
    @XmlAttribute(name = CommonAttributes.FUNCTION)
    protected String function;
    @XmlAttribute(name = "freq")
    protected Integer freq;
    @XmlElement
    protected SigStored sig;
    @XmlElement(name = TermStored.XML_NAME)
    protected List<TermStored> terms = new ArrayList<TermStored>();

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getFunction() {
        return function;
    }

    @Override
    public Integer getFrequency() {
        return freq;
    }

    @Override
    public Sig getSig() {
        return sig;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (type != null) {
            sb.append(type);
            sb.append(" ");
        }
        if (function != null) {
            sb.append(function);
            sb.append(" ");
        }
        if (freq != null) {
            sb.append(freq).append(" ");
        }
        if (sig != null) {
            sb.append(sig);
            sb.append(" ");
        }
        sb.append(terms.toString());
        return sb.toString();
    }
}
