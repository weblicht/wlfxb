/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.api;

/**
 * @author Yana Panchenko
 *
 */
public interface MorphologyAnalysis {

    //public FeatureStructure getFeatures();
    public Feature[] getFeatures();

    public MorphologySegment[] getSegmentation();
}
