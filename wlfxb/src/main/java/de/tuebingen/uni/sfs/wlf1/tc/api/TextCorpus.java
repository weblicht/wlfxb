package de.tuebingen.uni.sfs.wlf1.tc.api;

/**
 * @author Yana Panchenko
 *
 */
public interface TextCorpus {

    public String getLanguage();

    public TextLayer getTextLayer();

    public TokensLayer getTokensLayer();

    public LemmasLayer getLemmasLayer();

    public PosTagsLayer getPosTagsLayer();

    public SentencesLayer getSentencesLayer();

    public ConstituentParsingLayer getConstituentParsingLayer();

    public DependencyParsingLayer getDependencyParsingLayer();

    public MorphologyLayer getMorphologyLayer();

    public NamedEntitiesLayer getNamedEntitiesLayer();

    public CoreferencesLayer getCoreferencesLayer();

    @SuppressWarnings("deprecation")
    public RelationsLayer getRelationsLayer();

    public MatchesLayer getMatchesLayer();

    public WordSplittingLayer getWordSplittingLayer();

    public PhoneticsLayer getPhoneticsLayer();

    public LexicalSemanticsLayer getSynonymyLayer();

    public LexicalSemanticsLayer getAntonymyLayer();

    public LexicalSemanticsLayer getHyponymyLayer();

    public LexicalSemanticsLayer getHyperonymyLayer();
}
