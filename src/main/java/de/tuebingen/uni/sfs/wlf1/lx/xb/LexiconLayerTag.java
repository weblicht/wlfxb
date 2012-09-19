package de.tuebingen.uni.sfs.wlf1.lx.xb;

import de.tuebingen.uni.sfs.wlf1.lx.api.LexiconLayer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum LexiconLayerTag {

    /*
     * New layers should be added in such an order that they are enumerated
     * after the layers they reference and before the layers that are
     * referencing them. The order here will also be the order in TCF output.
     */
    LEMMAS("lemmas", LemmasLayerStored.class),
    //MORPHOLOGY("morphology"),
    POSTAGS("POStags", PosTagsLayerStored.class),
    FREQUENCIES("frequencies", FrequenciesLayerStored.class),
    RELATIONS("word-relations", RelationsLayerStored.class);
    private static final Map<String, LexiconLayerTag> xmlNameToLayerTagMap =
            new HashMap<String, LexiconLayerTag>() {

                {
                    for (LexiconLayerTag layerTag : LexiconLayerTag.values()) {
                        put(layerTag.xmlName, layerTag);
                    }
                }
            };
    private static final Map<Class<? extends LexiconLayer>, LexiconLayerTag> classToLayerTagMap =
            new HashMap<Class<? extends LexiconLayer>, LexiconLayerTag>() {

                {
                    for (LexiconLayerTag layerTag : LexiconLayerTag.values()) {
                        put(layerTag.getLayerClass(), layerTag);
                    }
                }
            };
    private static final LexiconLayerTag[] layersOrder;

    static {
        layersOrder = new LexiconLayerTag[LexiconLayerTag.values().length];
        for (LexiconLayerTag layerTag : LexiconLayerTag.values()) {
            layersOrder[layerTag.ordinal()] = layerTag;
        }
    }
    private final String xmlName;
    private final Class<? extends LexiconLayer> layerClass;

    LexiconLayerTag(String name, Class<? extends LexiconLayer> layerClass) {
        this.xmlName = name;
        this.layerClass = layerClass;
    }

    public final String getXmlName() {
        return xmlName;
    }

    public final Class<? extends LexiconLayer> getLayerClass() {
        return layerClass;
    }

    public static LexiconLayerTag getFromXmlName(String xmlName) {
        return xmlNameToLayerTagMap.get(xmlName);
    }

    public static List<LexiconLayerTag> orderedLayerTags() {
        return Arrays.asList(layersOrder);
    }

    public static LexiconLayerTag getFromClass(Class<? extends LexiconLayer> cl) {
        return classToLayerTagMap.get(cl);
    }
}
