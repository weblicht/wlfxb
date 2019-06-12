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

import eu.clarin.weblicht.wlfxb.tc.api.Token;
import eu.clarin.weblicht.wlfxb.tc.api.TopologicalField;
import eu.clarin.weblicht.wlfxb.tc.api.TopologicalFieldsLayer;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;
import eu.clarin.weblicht.wlfxb.utils.WlfUtilities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * @author Neele Witte
 *
 */
@XmlRootElement(name = TopologicalFieldsLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class TopologicalFieldsLayerStored extends TextCorpusLayerStoredAbstract implements TopologicalFieldsLayer {

    public static final String XML_NAME = "topologicalFields";
    @XmlAttribute(name = CommonAttributes.TAGSET, required = true)
    private String tagset;
    @XmlElement(name = TopologicalFieldStored.XML_NAME)
    private List<TopologicalFieldStored> tags = new ArrayList<TopologicalFieldStored>();
    private TextCorpusLayersConnector connector;

    protected TopologicalFieldsLayerStored() {
    }

    protected TopologicalFieldsLayerStored(String tagset) {
        this.tagset = tagset;
    }

    protected TopologicalFieldsLayerStored(TextCorpusLayersConnector connector) {
        this.connector = connector;
    }

    protected void setLayersConnector(TextCorpusLayersConnector connector) {
        this.connector = connector;
        for (TopologicalFieldStored tag : tags) {
            for (String tokRef : tag.tokRefs) {
                connector.token2ItsTopoField.put(connector.tokenId2ItsToken.get(tokRef), tag);
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return tags.isEmpty();
    }

    @Override
    public int size() {
        return tags.size();
    }

    @Override
    public String getTagset() {
        return tagset;
    }

    @Override
    public TopologicalField getTag(int index) {
        TopologicalField tag = tags.get(index);
        return tag;
    }

    @Override
    public TopologicalField getTag(Token token) {
        TopologicalField tag = connector.token2ItsTopoField.get(token);
        return tag;
    }

    @Override
    public Token[] getTokens(TopologicalField tag) {
        if (tag instanceof TopologicalFieldStored) {
            TopologicalFieldStored tagStored = (TopologicalFieldStored) tag;
            return WlfUtilities.tokenIdsToTokens(tagStored.tokRefs, connector.tokenId2ItsToken);
        } else {
            return null;
        }
    }

    @Override
    public TopologicalField addTag(String tagString, Token tagToken) {
        List<Token> tagTokens = Arrays.asList(new Token[]{tagToken});
        return addTag(tagString, tagTokens);
    }

    @Override
    public TopologicalField addTag(String tagString, List<Token> tagTokens) {
        TopologicalFieldStored tag = new TopologicalFieldStored();
        tag.tagString = tagString;
        tag.tokRefs = new String[tagTokens.size()];
        for (int i = 0; i < tagTokens.size(); i++) {
            Token token = tagTokens.get(i);
            tag.tokRefs[i] = token.getID();
            connector.token2ItsTopoField.put(token, tag);
        }
        tags.add(tag);
        return tag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(XML_NAME);
        sb.append(" ");
        sb.append("{");
        sb.append(tagset);
        sb.append("} :");
        sb.append(tags.toString());
        return sb.toString();
    }
}
