package eu.clarin.weblicht.wlfxb.tc.api;

import java.util.List;

/**
 * @author Yana Panchenko
 *
 */
public interface TextCorpus {

    public String getLanguage();

    public List<TextCorpusLayer> getLayers();

    public TextLayer getTextLayer();

    public TextLayer createTextLayer();

    public TokensLayer getTokensLayer();

    public TokensLayer createTokensLayer();

    public TokensLayer createTokensLayer(boolean hasCharOffsets);

    public LemmasLayer getLemmasLayer();

    public LemmasLayer createLemmasLayer();

    public PosTagsLayer getPosTagsLayer();

    public PosTagsLayer createPosTagsLayer(String tagset);

    public SentencesLayer getSentencesLayer();

    public SentencesLayer createSentencesLayer();

    public SentencesLayer createSentencesLayer(boolean hasCharOffsets);

    public ConstituentParsingLayer getConstituentParsingLayer();

    public ConstituentParsingLayer createConstituentParsingLayer(String tagset);

    public DependencyParsingLayer getDependencyParsingLayer();

    public DependencyParsingLayer createDependencyParsingLayer(
            boolean multipleGovernorsPossible, boolean emptyTokensPossible);

    public DependencyParsingLayer createDependencyParsingLayer(String tagset,
            boolean multipleGovernorsPossible, boolean emptyTokensPossible);

    public MorphologyLayer getMorphologyLayer();

    public MorphologyLayer createMorphologyLayer(boolean hasSegmentation);

    public MorphologyLayer createMorphologyLayer(boolean hasSegmentation, boolean hasCharOffsets);

    public NamedEntitiesLayer getNamedEntitiesLayer();

    public NamedEntitiesLayer createNamedEntitiesLayer(String entitiesType);

    public ReferencesLayer getReferencesLayer();

    /**
     * Creates empty references layers, ready to be filled in with the
     * references data
     *
     * @param typetagset tagset for the mention type values of the references
     * (should be null if no types are defined)
     * @param reltagset tagset for relation values between the references
     * (should be null if no relations are defined)
     * @param externalReferencesSource name of external source (should be null
     * if entities from the external source are not referenced)
     * @return references layer
     */
    public ReferencesLayer createReferencesLayer(String typetagset, String reltagset, String externalReferencesSource);

    @SuppressWarnings("deprecation")
    public RelationsLayer getRelationsLayer();

    @SuppressWarnings("deprecation")
    public RelationsLayer createRelationsLayer(String type);

    public MatchesLayer getMatchesLayer();

    public MatchesLayer createMatchesLayer(String queryLanguage, String queryString);

    public WordSplittingLayer getWordSplittingLayer();

    public WordSplittingLayer createWordSplittingLayer(String type);

    public PhoneticsLayer getPhoneticsLayer();

    public PhoneticsLayer createPhotenicsLayer(String alphabet);

    public GeoLayer getGeoLayer();

    public GeoLayer createGeoLayer(String source, GeoLongLatFormat coordFormat);

    public GeoLayer createGeoLayer(String source, GeoLongLatFormat coordFormat,
            GeoContinentFormat conitentFormat, GeoCountryFormat countyFormat, GeoCapitalFormat capitalFormat);

    public OrthographyLayer getOrthographyLayer();

    public OrthographyLayer createOrthographyLayer();

    public TextStructureLayer getTextStructureLayer();

    public TextStructureLayer createTextStructureLayer();

    public LexicalSemanticsLayer getSynonymyLayer();

    public LexicalSemanticsLayer createSynonymyLayer(String source);

    public LexicalSemanticsLayer getAntonymyLayer();

    public LexicalSemanticsLayer createAntonymyLayer(String source);

    public LexicalSemanticsLayer getHyponymyLayer();

    public LexicalSemanticsLayer createHyponymyLayer(String source);

    public LexicalSemanticsLayer getHyperonymyLayer();

    public LexicalSemanticsLayer createHyperonymyLayer(String source);

    public DiscourseConnectivesLayer getDiscourseConnectivesLayer();

    public DiscourseConnectivesLayer createDiscourseConnectivesLayer();

    public DiscourseConnectivesLayer createDiscourseConnectivesLayer(String typeTagset);
}
