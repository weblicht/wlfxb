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

import eu.clarin.weblicht.wlfxb.tc.api.Sentence;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;
import java.util.Arrays;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = SentenceStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"tokIds", "end", "start"})
public class SentenceStored implements Sentence {

    public static final String XML_NAME = "sentence";
    @XmlAttribute(name = CommonAttributes.ID)
    protected String sentenceId;
    @XmlAttribute(name = CommonAttributes.TOKEN_SEQUENCE_REFERENCE, required = true)
    protected String[] tokIds;
    @XmlAttribute(name = CommonAttributes.START_CHAR_OFFSET)
    protected Integer start;
    @XmlAttribute(name = CommonAttributes.END_CHAR_OFFSET)
    protected Integer end;

    @Override
    public String getId() {
        return sentenceId;
    }

    @Override
    public Integer getStartCharOffset() {
        return start;
    }

    @Override
    public Integer getEndCharOffset() {
        return end;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (sentenceId != null) {
            sb.append(sentenceId);
            sb.append(" -> ");
        }
        sb.append(Arrays.toString(tokIds));
        if (start != null && end != null) {
            sb.append(" (");
            sb.append(start);
            sb.append("-");
            sb.append(end);
            sb.append(")");
        }
        return sb.toString();
    }
}
