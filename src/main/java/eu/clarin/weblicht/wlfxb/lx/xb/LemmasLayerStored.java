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
package eu.clarin.weblicht.wlfxb.lx.xb;

import eu.clarin.weblicht.wlfxb.lx.api.Lemma;
import eu.clarin.weblicht.wlfxb.lx.api.LemmasLayer;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = LemmasLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class LemmasLayerStored extends LexiconLayerStoredAbstract implements LemmasLayer {

    public static final String XML_NAME = "lemmas";
    private LexiconLayersConnector connector;
    @XmlElement(name = LemmaStored.XML_NAME)
    private List<LemmaStored> lemmas = new ArrayList<LemmaStored>();

    protected LemmasLayerStored() {
    }

    protected LemmasLayerStored(LexiconLayersConnector connector) {
        this.connector = connector;
    }

    public void setLayersConnector(LexiconLayersConnector connector) {
        this.connector = connector;
        for (LemmaStored lemma : lemmas) {
            this.connector.lemmaId2ItsLemma.put(lemma.lemmaId, lemma);
        }
    }

    @Override
    public Lemma getLemma(int index) {
        return lemmas.get(index);
    }

    @Override
    public Lemma getLemma(String lemmaId) {
        Lemma lemma = connector.lemmaId2ItsLemma.get(lemmaId);
        return lemma;
    }

    @Override
    public Lemma addLemma(String lemmaString) {
        LemmaStored lemma = new LemmaStored();
        int lemmaCount = lemmas.size();
        lemma.lemmaId = LemmaStored.ID_PREFIX + lemmaCount;
        lemma.lemmaString = lemmaString;
        connector.lemmaId2ItsLemma.put(lemma.lemmaId, lemma);
        lemmas.add(lemma);
        return lemma;
    }

    @Override
    public boolean isEmpty() {
        return lemmas.isEmpty();
    }

    @Override
    public int size() {
        return lemmas.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(XML_NAME);
        sb.append(" : ");
        sb.append(lemmas.toString());
        return sb.toString();
    }
}
