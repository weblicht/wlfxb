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
