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
import eu.clarin.weblicht.wlfxb.tc.api.Token;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;
import eu.clarin.weblicht.wlfxb.utils.WlfUtilities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import eu.clarin.weblicht.wlfxb.tc.api.ChunksLayer;
/**
 *
 * @author felahi
 */
@XmlRootElement(name = ChunksLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class ChunksLayerStored extends TextCorpusLayerStoredAbstract implements ChunksLayer {

    public static final String XML_NAME = "chunks";
    @XmlAttribute(name = CommonAttributes.TAGSET, required = true)
    private String tagset;
    @XmlElement(name = ChunkStored.XML_NAME)
    private List<ChunkStored> chunks = new ArrayList<ChunkStored>();
    private Set<String> foundTypes = new HashSet<String>();
    private TextCorpusLayersConnector connector;

    protected ChunksLayerStored() {
    }

    protected ChunksLayerStored(String tagset) {
        this.tagset = tagset;
    }

    protected ChunksLayerStored(TextCorpusLayersConnector connector) {
        this.connector = connector;
    }

    @Override
    protected void setLayersConnector(TextCorpusLayersConnector connector) {
        this.connector = connector;
        for (ChunkStored ch : chunks) {
            for (String tokRef : ch.tokRefs) {
                Token tok = connector.tokenId2ItsToken.get(tokRef);
                if (!connector.token2ItsCH.containsKey(tok)) {
                    connector.token2ItsCH.put(tok, new ArrayList<Chunk>());
                }
                connector.token2ItsCH.get(tok).add(ch);
            }
            for (String type : ch.getTypes().keySet()) {
                foundTypes.add(type.toString());
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return chunks.isEmpty();
    }

    @Override
    public int size() {
        return chunks.size();
    }

    @Override
    public String getTagset() {
        return tagset;
    }

    @Override
    public Chunk getChunk(int index) {
        return chunks.get(index);
    }

    @Override
    public Chunk getChunk(Token token) {
        List<Chunk> nes = connector.token2ItsCH.get(token);
        if (nes != null && !nes.isEmpty()) {
            return nes.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Chunk> getChunks(Token token) {
        List<Chunk> chs = connector.token2ItsCH.get(token);
        return chs;
    }

    @Override
    public Token[] getTokens(Chunk chunk) {
        if (chunk instanceof ChunkStored) {
            ChunkStored ch = (ChunkStored) chunk;
            return WlfUtilities.tokenIdsToTokens(ch.tokRefs, connector.tokenId2ItsToken);
        } else {
            return null;
        }
    }

    @Override
    public Chunk addChunk(LinkedHashMap<String, String> chunkAttributes, Token chunkToken) {
        List<Token> tagTokens = Arrays.asList(new Token[]{chunkToken});
        return addChunk(chunkAttributes, tagTokens);
    }

    @Override
    public Chunk addChunk(LinkedHashMap<String, String> chunkAttributes, List<Token> chunkTokens) {
        ChunkStored ch = new ChunkStored();
        ch.attributes = ch.getAttributes(chunkAttributes);
        ch.tokRefs = new String[chunkTokens.size()];
        for (int i = 0; i < chunkTokens.size(); i++) {
            Token token = chunkTokens.get(i);
            ch.tokRefs[i] = token.getID();
            Token tok = connector.tokenId2ItsToken.get(token.getID());
            if (!connector.token2ItsCH.containsKey(tok)) {
                connector.token2ItsCH.put(tok, new ArrayList<Chunk>());
            }
            connector.token2ItsCH.get(tok).add(ch);
        }
        chunks.add(ch);
        for (String type : ch.getTypes().keySet()) {
            this.foundTypes.add(type.toString());
        }
        return ch;
    }

    @Override
    public Set<String> getFoundTypes() {
        return foundTypes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(XML_NAME);
        sb.append(" ");
        sb.append("{");
        sb.append(tagset);
        sb.append("} :");
        sb.append(chunks.toString());
        return sb.toString();
    }

}
