package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.TextCorpusLayer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum TextCorpusLayerTag {

    /*
     * New layers should be added in such an order that they are enumerated
     * after the layers they reference and before the layers that are
     * referencing them. The order here will also be the order in TCF output.
     */
    TEXT(TextLayerStored.XML_NAME, TextLayerStored.class),
    TOKENS(TokensLayerStored.XML_NAME, TokensLayerStored.class),
    SENTENCES(SentencesLayerStored.XML_NAME, SentencesLayerStored.class),
    LEMMAS(LemmasLayerStored.XML_NAME, LemmasLayerStored.class),
    POSTAGS(PosTagsLayerStored.XML_NAME, PosTagsLayerStored.class),
    MORPHOLOGY(MorphologyLayerStored.XML_NAME, MorphologyLayerStored.class),
    PARSING_CONSTITUENT(ConstituentParsingLayerStored.XML_NAME, ConstituentParsingLayerStored.class),
    PARSING_DEPENDENCY(DependencyParsingLayerStored.XML_NAME, DependencyParsingLayerStored.class),
    @Deprecated
    RELATIONS(RelationsLayerStored.XML_NAME, RelationsLayerStored.class),
    NAMED_ENTITIES(NamedEntitiesLayerStored.XML_NAME, NamedEntitiesLayerStored.class),
    REFERENCES(ReferencesLayerStored.XML_NAME, ReferencesLayerStored.class),
    SYNONYMY(SynonymyLayerStored.XML_NAME, SynonymyLayerStored.class),
    ANTONYMY(AntonymyLayerStored.XML_NAME, AntonymyLayerStored.class),
    HYPONYMY(HyponymyLayerStored.XML_NAME, HyponymyLayerStored.class),
    HYPERONYMY(HyperonymyLayerStored.XML_NAME, HyperonymyLayerStored.class),
    WORD_SPLITTINGS(WordSplittingLayerStored.XML_NAME, WordSplittingLayerStored.class),
    PHONETICS(PhoneticsLayerStored.XML_NAME, PhoneticsLayerStored.class),
    GEO(GeoLayerStored.XML_NAME, GeoLayerStored.class),
    ORTHOGRAPHY(OrthographyLayerStored.XML_NAME, OrthographyLayerStored.class),
    TEXT_STRUCTURE(TextStructureLayerStored.XML_NAME, TextStructureLayerStored.class),
    DISCOURSE_CONNECTIVES(DiscourseConnectivesLayerStored.XML_NAME, DiscourseConnectivesLayerStored.class),
    CORPUS_MATCHES(MatchesLayerStored.XML_NAME, MatchesLayerStored.class);
    private static final Map<String, TextCorpusLayerTag> xmlNameToLayerTagMap =
            new HashMap<String, TextCorpusLayerTag>() {

                {
                    for (TextCorpusLayerTag layerTag : TextCorpusLayerTag.values()) {
                        put(layerTag.xmlName, layerTag);
                    }
                }
            };
    private static final Map<Class<? extends TextCorpusLayer>, TextCorpusLayerTag> classToLayerTagMap =
            new HashMap<Class<? extends TextCorpusLayer>, TextCorpusLayerTag>() {

                {
                    for (TextCorpusLayerTag layerTag : TextCorpusLayerTag.values()) {
                        put(layerTag.getLayerClass(), layerTag);
                    }
                }
            };
    private static final TextCorpusLayerTag[] layersOrder;

    static {
        layersOrder = new TextCorpusLayerTag[TextCorpusLayerTag.values().length];
        for (TextCorpusLayerTag layerTag : TextCorpusLayerTag.values()) {
            layersOrder[layerTag.ordinal()] = layerTag;
        }
    }
    private final String xmlName;
    private final Class<? extends TextCorpusLayer> layerClass;

    TextCorpusLayerTag(String name, Class<? extends TextCorpusLayer> layerClass) {
        this.xmlName = name;
        this.layerClass = layerClass;
    }

    public final String getXmlName() {
        return xmlName;
    }

    public final Class<? extends TextCorpusLayer> getLayerClass() {
        return layerClass;
    }

    public static TextCorpusLayerTag getFromXmlName(String xmlName) {
        return xmlNameToLayerTagMap.get(xmlName);
    }

    public static List<TextCorpusLayerTag> orderedLayerTags() {
        return Arrays.asList(layersOrder);
    }

    public static TextCorpusLayerTag getFromClass(Class<? extends TextCorpusLayer> cl) {
        return classToLayerTagMap.get(cl);
    }
}
