/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.api;

/**
 * @author Yana Panchenko
 *
 */
public interface MorphologyAnalysis {

    //public FeatureStructure getFeatures();
    public Feature[] getFeatures();

    public MorphologySegment[] getSegmentation();
}
