package eu.clarin.weblicht.wlfxb.tc.api;

import java.util.List;

public interface MorphologyLayer extends TextCorpusLayer {

    public int size();

    public boolean hasSegmentation();

    public boolean hasCharoffsets();

    public MorphologyAnalysis getAnalysis(int index);

    public MorphologyAnalysis getAnalysis(Token token);

    public Token[] getTokens(MorphologyAnalysis analysis);

    public MorphologyAnalysis addAnalysis(Token analysedToken, List<Feature> morphologyFeatures);

    public MorphologyAnalysis addAnalysis(Token analysedToken, List<Feature> morphologyFeatures,
            List<MorphologySegment> segments);

    public MorphologyAnalysis addAnalysis(List<Token> analysedTokens, List<Feature> morphologyFeatures);

    public MorphologyAnalysis addAnalysis(List<Token> analysedTokens, List<Feature> morphologyFeatures,
            List<MorphologySegment> segments);

    public Feature createFeature(String name, String value);

    public Feature createFeature(String name, List<Feature> subfeatures);

    public MorphologySegment createSegment(String type, String category,
            String function, Integer start, Integer end, String value);

    public MorphologySegment createSegment(String type, String category,
            String function, Integer start, Integer end, List<MorphologySegment> subsegments);
}
