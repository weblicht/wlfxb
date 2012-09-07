/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.*;
import java.lang.reflect.Constructor;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@SuppressWarnings("deprecation")
@XmlRootElement(name = TextCorpusStored.XML_NAME, namespace = TextCorpusStored.XML_NAMESPACE)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {
    "textLayer",
    "tokensLayer",
    "sentencesLayer",
    "lemmasLayer",
    "posTagsLayer",
    "constituentParsingLayer",
    "dependencyParsingLayer",
    "morphologyLayer",
    "namedEntitiesLayer",
    "coreferencesLayer",
    "relationsLayer",
    "matchesLayer",
    "wordSplittingLayer",
    "phoneticsLayer",
    "synonymyLayer",
    "antonymyLayer",
    "hyponymyLayer",
    "hyperonymyLayer",})
public class TextCorpusStored implements TextCorpus {

    public static final String XML_NAME = "TextCorpus";
    public static final String XML_NAMESPACE = "http://www.dspin.de/data/textcorpus";
    @XmlAttribute
    protected String lang;
    protected TextCorpusLayerStoredAbstract[] layersInOrder;
    private TextCorpusLayersConnector connector;

    TextCorpusStored() {
        connector = new TextCorpusLayersConnector();
        layersInOrder = new TextCorpusLayerStoredAbstract[TextCorpusLayerTag.orderedLayerTags().size()];
    }

    public TextCorpusStored(String language) {
        this();
        this.lang = language;
    }

    public String getLanguage() {
        return lang;
    }

    public TextLayer createTextLayer() {
        TextLayer layer = initializeLayer(TextLayerStored.class);
        return layer;
    }

    public TokensLayer createTokensLayer() {
        return initializeLayer(TokensLayerStored.class);
    }

    public TokensLayer createTokensLayer(boolean hasCharOffsets) {
        return initializeLayer(TokensLayerStored.class, Boolean.valueOf(hasCharOffsets));
    }

    public LemmasLayer createLemmasLayer() {
        return initializeLayer(LemmasLayerStored.class);
    }

    public PosTagsLayer createPosTagsLayer(String tagset) {
        return initializeLayer(PosTagsLayerStored.class, tagset);
    }

    public SentencesLayer createSentencesLayer() {
        return initializeLayer(SentencesLayerStored.class);
    }

    public SentencesLayer createSentencesLayer(boolean hasCharOffsets) {
        return initializeLayer(SentencesLayerStored.class, Boolean.valueOf(hasCharOffsets));
    }

    public ConstituentParsingLayer createConstituentParsingLayer(String tagset) {
        return initializeLayer(ConstituentParsingLayerStored.class, tagset);
    }

    public DependencyParsingLayer createDependencyParsingLayer(String tagset,
            boolean multipleGovernorsPossible, boolean emptyTokensPossible) {
        return initializeLayer(DependencyParsingLayerStored.class, tagset,
                Boolean.valueOf(multipleGovernorsPossible), Boolean.valueOf(emptyTokensPossible));
    }

    public DependencyParsingLayer createDependencyParsingLayer(
            boolean multipleGovernorsPossible, boolean emptyTokensPossible) {
        return initializeLayer(DependencyParsingLayerStored.class,
                Boolean.valueOf(multipleGovernorsPossible), Boolean.valueOf(emptyTokensPossible));
    }

    public MorphologyLayer createMorphologyLayer() {
        return initializeLayer(MorphologyLayerStored.class);
    }

    public MorphologyLayer createMorphologyLayer(boolean hasSegmentation) {
        return initializeLayer(MorphologyLayerStored.class, Boolean.valueOf(hasSegmentation));
    }

    public MorphologyLayer createMorphologyLayer(boolean hasSegmentation, boolean hasCharOffsets) {
        return initializeLayer(MorphologyLayerStored.class, Boolean.valueOf(hasSegmentation), Boolean.valueOf(hasCharOffsets));
    }

    public NamedEntitiesLayer createNamedEntitiesLayer(String entitiesType) {
        return initializeLayer(NamedEntitiesLayerStored.class, entitiesType);
    }

    public CoreferencesLayer createCoreferencesLayer(String tagset) {
        return initializeLayer(CoreferencesLayerStored.class, tagset);
    }

    public CoreferencesLayer createCoreferencesLayer(String tagset, String externalReferencesSource) {
        return initializeLayer(CoreferencesLayerStored.class, tagset, externalReferencesSource);
    }

    public RelationsLayer createRelationsLayer(String type) {
        return initializeLayer(RelationsLayerStored.class, type);
    }

    public MatchesLayer createMatchesLayer(String queryLanguage, String queryString) {
        return initializeLayer(MatchesLayerStored.class, queryLanguage, queryString);
    }

    public WordSplittingLayer createWordSplittingLayer(String type) {
        return initializeLayer(WordSplittingLayerStored.class, type);
    }

    public PhoneticsLayer createPhotenicsLayer(String alphabet) {
        return initializeLayer(PhoneticsLayerStored.class, alphabet);
    }

    public LexicalSemanticsLayer createSynonymyLayer(String source) {
        return initializeLayer(SynonymyLayerStored.class, source);
    }

    public LexicalSemanticsLayer createAntonymyLayer(String source) {
        return initializeLayer(AntonymyLayerStored.class, source);
    }

    public LexicalSemanticsLayer createHyponymyLayer(String source) {
        return initializeLayer(HyponymyLayerStored.class, source);
    }

    public LexicalSemanticsLayer createHyperonymyLayer(String source) {
        return initializeLayer(HyperonymyLayerStored.class, source);
    }

    @SuppressWarnings("unchecked")
    private <T extends TextCorpusLayerStoredAbstract> T initializeLayer(Class<T> layerClass, Object... params) {

        Class<?>[] paramsClass = null;
        if (params != null) {
            paramsClass = new Class<?>[params.length];
            for (int i = 0; i < params.length; i++) {
                paramsClass[i] = params[i].getClass();
            }
        }
        TextCorpusLayerTag layerTag = TextCorpusLayerTag.getFromClass(layerClass);
        try {
            Constructor<?> ct = null;
            T instance = null;
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
            e.printStackTrace();
        }
        return (T) layersInOrder[layerTag.ordinal()];
    }

    @XmlElement(name = TextLayerStored.XML_NAME)
    protected void setTextLayer(TextLayerStored layer) {
        layersInOrder[TextCorpusLayerTag.TEXT.ordinal()] = layer;
    }

    public TextLayerStored getTextLayer() {
        return ((TextLayerStored) layersInOrder[TextCorpusLayerTag.TEXT.ordinal()]);
    }

    @XmlElement(name = TokensLayerStored.XML_NAME)
    protected void setTokensLayer(TokensLayerStored layer) {
        layersInOrder[TextCorpusLayerTag.TOKENS.ordinal()] = layer;
    }

    public TokensLayerStored getTokensLayer() {
        return ((TokensLayerStored) layersInOrder[TextCorpusLayerTag.TOKENS.ordinal()]);
    }

    @XmlElement(name = SentencesLayerStored.XML_NAME)
    protected void setSentencesLayer(SentencesLayerStored layer) {
        layersInOrder[TextCorpusLayerTag.SENTENCES.ordinal()] = layer;
    }

    public SentencesLayerStored getSentencesLayer() {
        return ((SentencesLayerStored) layersInOrder[TextCorpusLayerTag.SENTENCES.ordinal()]);
    }

    @XmlElement(name = LemmasLayerStored.XML_NAME)
    protected void setLemmasLayer(LemmasLayerStored layer) {
        layersInOrder[TextCorpusLayerTag.LEMMAS.ordinal()] = layer;
    }

    public LemmasLayerStored getLemmasLayer() {
        return ((LemmasLayerStored) layersInOrder[TextCorpusLayerTag.LEMMAS.ordinal()]);
    }

    @XmlElement(name = PosTagsLayerStored.XML_NAME)
    protected void setPosTagsLayer(PosTagsLayerStored layer) {
        layersInOrder[TextCorpusLayerTag.POSTAGS.ordinal()] = layer;
    }

    public PosTagsLayerStored getPosTagsLayer() {
        return ((PosTagsLayerStored) layersInOrder[TextCorpusLayerTag.POSTAGS.ordinal()]);
    }

    @XmlElement(name = ConstituentParsingLayerStored.XML_NAME)
    protected void setConstituentParsingLayer(ConstituentParsingLayerStored layer) {
        layersInOrder[TextCorpusLayerTag.PARSING_CONSTITUENT.ordinal()] = layer;
    }

    public ConstituentParsingLayerStored getConstituentParsingLayer() {
        return ((ConstituentParsingLayerStored) layersInOrder[TextCorpusLayerTag.PARSING_CONSTITUENT.ordinal()]);
    }

    @XmlElement(name = DependencyParsingLayerStored.XML_NAME)
    protected void setDependencyParsingLayer(DependencyParsingLayerStored layer) {
        layersInOrder[TextCorpusLayerTag.PARSING_DEPENDENCY.ordinal()] = layer;
    }

    public DependencyParsingLayerStored getDependencyParsingLayer() {
        return ((DependencyParsingLayerStored) layersInOrder[TextCorpusLayerTag.PARSING_DEPENDENCY.ordinal()]);
    }

    @XmlElement(name = MorphologyLayerStored.XML_NAME)
    protected void setMorphologyLayer(MorphologyLayerStored layer) {
        layersInOrder[TextCorpusLayerTag.MORPHOLOGY.ordinal()] = layer;
    }

    public MorphologyLayerStored getMorphologyLayer() {
        return ((MorphologyLayerStored) layersInOrder[TextCorpusLayerTag.MORPHOLOGY.ordinal()]);
    }

    @XmlElement(name = NamedEntitiesLayerStored.XML_NAME)
    protected void setNamedEntitiesLayer(NamedEntitiesLayerStored layer) {
        layersInOrder[TextCorpusLayerTag.NAMED_ENTITIES.ordinal()] = layer;
    }

    public NamedEntitiesLayerStored getNamedEntitiesLayer() {
        return ((NamedEntitiesLayerStored) layersInOrder[TextCorpusLayerTag.NAMED_ENTITIES.ordinal()]);
    }

    @XmlElement(name = CoreferencesLayerStored.XML_NAME)
    protected void setCoreferencesLayer(CoreferencesLayerStored layer) {
        layersInOrder[TextCorpusLayerTag.COREFERENCES.ordinal()] = layer;
    }

    public CoreferencesLayerStored getCoreferencesLayer() {
        return ((CoreferencesLayerStored) layersInOrder[TextCorpusLayerTag.COREFERENCES.ordinal()]);
    }

    @XmlElement(name = RelationsLayerStored.XML_NAME)
    protected void setRelationsLayer(RelationsLayerStored layer) {
        layersInOrder[TextCorpusLayerTag.RELATIONS.ordinal()] = layer;
    }

    public RelationsLayerStored getRelationsLayer() {
        return ((RelationsLayerStored) layersInOrder[TextCorpusLayerTag.RELATIONS.ordinal()]);
    }

    @XmlElement(name = MatchesLayerStored.XML_NAME)
    protected void setMatchesLayer(MatchesLayerStored layer) {
        layersInOrder[TextCorpusLayerTag.CORPUS_MATCHES.ordinal()] = layer;
    }

    public MatchesLayerStored getMatchesLayer() {
        return ((MatchesLayerStored) layersInOrder[TextCorpusLayerTag.CORPUS_MATCHES.ordinal()]);
    }

    @XmlElement(name = WordSplittingLayerStored.XML_NAME)
    protected void setWordSplittingLayer(WordSplittingLayerStored layer) {
        layersInOrder[TextCorpusLayerTag.WORD_SPLITTINGS.ordinal()] = layer;
    }

    public WordSplittingLayerStored getWordSplittingLayer() {
        return ((WordSplittingLayerStored) layersInOrder[TextCorpusLayerTag.WORD_SPLITTINGS.ordinal()]);
    }

    @XmlElement(name = PhoneticsLayerStored.XML_NAME)
    protected void setPhoneticsLayer(PhoneticsLayerStored layer) {
        layersInOrder[TextCorpusLayerTag.PHONETICS.ordinal()] = layer;
    }

    public PhoneticsLayerStored getPhoneticsLayer() {
        return ((PhoneticsLayerStored) layersInOrder[TextCorpusLayerTag.PHONETICS.ordinal()]);
    }

    @XmlElement(name = SynonymyLayerStored.XML_NAME)
    protected void setSynonymyLayer(SynonymyLayerStored layer) {
        layersInOrder[TextCorpusLayerTag.SYNONYMY.ordinal()] = layer;
    }

    public SynonymyLayerStored getSynonymyLayer() {
        return ((SynonymyLayerStored) layersInOrder[TextCorpusLayerTag.SYNONYMY.ordinal()]);
    }

    @XmlElement(name = AntonymyLayerStored.XML_NAME)
    protected void setAntonymyLayer(AntonymyLayerStored layer) {
        layersInOrder[TextCorpusLayerTag.ANTONYMY.ordinal()] = layer;
    }

    public AntonymyLayerStored getAntonymyLayer() {
        return ((AntonymyLayerStored) layersInOrder[TextCorpusLayerTag.ANTONYMY.ordinal()]);
    }

    @XmlElement(name = HyponymyLayerStored.XML_NAME)
    protected void setHyponymyLayer(HyponymyLayerStored layer) {
        layersInOrder[TextCorpusLayerTag.HYPONYMY.ordinal()] = layer;
    }

    public HyponymyLayerStored getHyponymyLayer() {
        return ((HyponymyLayerStored) layersInOrder[TextCorpusLayerTag.HYPONYMY.ordinal()]);
    }

    @XmlElement(name = HyperonymyLayerStored.XML_NAME)
    protected void setHyperonymyLayer(HyperonymyLayerStored layer) {
        layersInOrder[TextCorpusLayerTag.HYPERONYMY.ordinal()] = layer;
    }

    public HyperonymyLayerStored getHyperonymyLayer() {
        return ((HyperonymyLayerStored) layersInOrder[TextCorpusLayerTag.HYPERONYMY.ordinal()]);
    }

    protected void afterUnmarshal(Unmarshaller u, Object parent) {
        connectLayers();
    }

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

        for (TextCorpusLayer layer : this.layersInOrder) {
            if (layer != null) {
                sb.append(layer);
                sb.append("\n");
            }
        }

        return sb.toString().trim();
    }

    /**
     * Would be able to compose correctly only in case referencing between
     * layers is correct
     *
     * @param lang
     * @param layers
     * @return text corpus stored
     */
    public static TextCorpusStored compose(String lang, TextCorpusLayerStoredAbstract... layers) {
        TextCorpusStored tc = new TextCorpusStored(lang);
        for (TextCorpusLayerStoredAbstract layer : layers) {
            tc.layersInOrder[TextCorpusLayerTag.getFromClass(layer.getClass()).ordinal()] = layer;
        }
        tc.connectLayers();
        return tc;
    }
}
