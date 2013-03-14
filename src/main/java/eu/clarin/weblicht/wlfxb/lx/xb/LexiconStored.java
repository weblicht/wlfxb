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

import eu.clarin.weblicht.wlfxb.lx.api.*;
import java.lang.reflect.Constructor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = LexiconStored.XML_NAME, namespace = LexiconStored.XML_NAMESPACE)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {
    "lemmasLayer",
    "posTagsLayer",
    "frequenciesLayer",
    "relationsLayer",})
public class LexiconStored implements Lexicon {

    public static final String XML_NAME = "Lexicon";
    public static final String XML_NAMESPACE = "http://www.dspin.de/data/lexicon";
    @XmlAttribute
    protected String lang;
    protected LexiconLayerStoredAbstract[] layersInOrder;
    private LexiconLayersConnector connector;

    LexiconStored() {
        connector = new LexiconLayersConnector();
        layersInOrder = new LexiconLayerStoredAbstract[LexiconLayerTag.orderedLayerTags().size()];
    }

    public LexiconStored(String language) {
        this();
        this.lang = language;
    }

    public String getLanguage() {
        return lang;
    }

    public LemmasLayer createLemmasLayer() {
        return initializeLayer(LemmasLayerStored.class);
    }

    public PosTagsLayer createPosTagsLayer(String tagset) {
        return initializeLayer(PosTagsLayerStored.class, tagset);
    }

    public FrequenciesLayer createFrequenciesLayer() {
        return initializeLayer(FrequenciesLayerStored.class);
    }

    public RelationsLayer createRelationsLayer() {
        return initializeLayer(RelationsLayerStored.class);
    }

    @SuppressWarnings("unchecked")
    private <T extends LexiconLayerStoredAbstract> T initializeLayer(Class<T> layerClass, Object... params) {

        Class<?>[] paramsClass = null;
        if (params != null) {
            paramsClass = new Class<?>[params.length];
            for (int i = 0; i < params.length; i++) {
                paramsClass[i] = params[i].getClass();
            }
        }

        LexiconLayerTag layerTag = LexiconLayerTag.getFromClass(layerClass);
        try {
            Constructor<?> ct;
            T instance;
            if (params == null) {
                ct = layerClass.getDeclaredConstructor();
                instance = (T) ct.newInstance();
            } else {
                ct = layerClass.getDeclaredConstructor(paramsClass);
                instance = (T) ct.newInstance(params);
            }
            layersInOrder[layerTag.ordinal()] = instance;
            instance.setLayersConnector(connector);
        } catch (Exception e) {
            //e.printStackTrace();
            Logger.getLogger(LexiconStored.class.getName()).log(Level.SEVERE, null, e);
        }
        return (T) layersInOrder[layerTag.ordinal()];
    }

    @XmlElement(name = LemmasLayerStored.XML_NAME)
    protected void setLemmasLayer(LemmasLayerStored layer) {
        layersInOrder[LexiconLayerTag.LEMMAS.ordinal()] = layer;
    }

    public LemmasLayerStored getLemmasLayer() {
        return ((LemmasLayerStored) layersInOrder[LexiconLayerTag.LEMMAS.ordinal()]);
    }

    @XmlElement(name = PosTagsLayerStored.XML_NAME)
    protected void setPosTagsLayer(PosTagsLayerStored layer) {
        layersInOrder[LexiconLayerTag.POSTAGS.ordinal()] = layer;
    }

    public PosTagsLayerStored getPosTagsLayer() {
        return ((PosTagsLayerStored) layersInOrder[LexiconLayerTag.POSTAGS.ordinal()]);
    }

    @XmlElement(name = FrequenciesLayerStored.XML_NAME)
    protected void setFrequenciesLayer(FrequenciesLayerStored layer) {
        layersInOrder[LexiconLayerTag.FREQUENCIES.ordinal()] = layer;
    }

    public FrequenciesLayerStored getFrequenciesLayer() {
        return ((FrequenciesLayerStored) layersInOrder[LexiconLayerTag.FREQUENCIES.ordinal()]);
    }

    @XmlElement(name = RelationsLayerStored.XML_NAME)
    protected void setRelationsLayer(RelationsLayerStored layer) {
        layersInOrder[LexiconLayerTag.RELATIONS.ordinal()] = layer;
    }

    public RelationsLayerStored getRelationsLayer() {
        return ((RelationsLayerStored) layersInOrder[LexiconLayerTag.RELATIONS.ordinal()]);
    }

    protected void afterUnmarshal(Unmarshaller u, Object parent) {
        connectLayers();
    }

//		protected boolean beforeMarshal(Marshaller m) {
//			setEmptyLayersToNull();
//			return true;
//		}
//		
//
//		/**
//		 * 
//		 */
//		private void setEmptyLayersToNull() {
//			
//			for (int i = 0; i < this.layersInOrder.length; i++) {
//				if (layersInOrder[i] != null && layersInOrder[i].isEmpty()) {
//					layersInOrder[i] = null;
//				}
//			}
//		}
    protected void connectLayers() {
        for (int i = 0; i < this.layersInOrder.length; i++) {
            if (layersInOrder[i] != null) {
                layersInOrder[i].setLayersConnector(connector);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(XML_NAME);
        sb.append(":\n");

        for (LexiconLayer layer : this.layersInOrder) {
            if (layer != null) {
                sb.append(layer);
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
