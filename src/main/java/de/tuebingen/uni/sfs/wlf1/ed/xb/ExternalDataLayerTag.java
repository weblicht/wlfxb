package de.tuebingen.uni.sfs.wlf1.ed.xb;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tuebingen.uni.sfs.wlf1.ed.api.ExternalDataLayer;


public enum ExternalDataLayerTag {

	/*
	 * New layers should be added in such an order that they are
	 * enumerated after the layers they reference and before
	 * the layers that are referencing them.
	 * The order here will also be the order in TCF output.
	 */
	SPEECH_SIGNAL(SpeechSignalLayerStored.XML_NAME, SpeechSignalLayerStored.class),
	TOKEN_SEGMENTATION(TokenSegmentationLayerStored.XML_NAME, TokenSegmentationLayerStored.class),
	PHONETIC_SEGMENTATION(PhoneticSegmentationLayerStored.XML_NAME, PhoneticSegmentationLayerStored.class),
	CANONICAL_SEGMENTATION(CanonicalSegmentationLayerStored.XML_NAME, CanonicalSegmentationLayerStored.class)
	
	;
	
	
	
	
	private static Map<String, ExternalDataLayerTag> xmlNameToLayerTagMap =
        new HashMap<String, ExternalDataLayerTag>();
	
	static {
	    for (ExternalDataLayerTag layerTag : ExternalDataLayerTag.values()) {
	    	xmlNameToLayerTagMap.put(layerTag.xmlName, layerTag);
	    }
	}
	
	private static Map<Class<? extends ExternalDataLayer>, ExternalDataLayerTag> classToLayerTagMap =
        new HashMap<Class<? extends ExternalDataLayer>, ExternalDataLayerTag>();
	static {
	    for (ExternalDataLayerTag layerTag :ExternalDataLayerTag.values()) {
	    	classToLayerTagMap.put(layerTag.getLayerClass(), layerTag);
	    }
	}
	
	private static final ExternalDataLayerTag[] layersOrder;
	
	static {
		layersOrder = new ExternalDataLayerTag[ExternalDataLayerTag.values().length];
	    for (ExternalDataLayerTag layerTag : ExternalDataLayerTag.values()) {
	    	layersOrder[layerTag.ordinal()] = layerTag;
	    }
	}
	
	private final String xmlName;
	private final Class<? extends ExternalDataLayer> layerClass;
	
	ExternalDataLayerTag(String name, Class<? extends ExternalDataLayer> layerClass) {
        this.xmlName = name;
        this.layerClass = layerClass;
    }


	public final String getXmlName() {
        return xmlName;
    }
	
	public final Class<? extends ExternalDataLayer> getLayerClass() {
        return layerClass;
    }
	
	public static ExternalDataLayerTag getFromXmlName(String xmlName) {
        return xmlNameToLayerTagMap.get(xmlName);
    }
	
	public static List<ExternalDataLayerTag> orderedLayerTags() {
		return Arrays.asList(layersOrder);
	}
	
	public static ExternalDataLayerTag getFromClass(Class<? extends ExternalDataLayer> cl) {
		return classToLayerTagMap.get(cl);
	}
	
}
