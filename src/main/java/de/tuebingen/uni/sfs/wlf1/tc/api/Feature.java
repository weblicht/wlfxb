/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.api;

/**
 * @author Yana Panchenko
 *
 */
public interface Feature {

    public String getName();

    public boolean isTerminal();
    //if terminal

    public String getValue();
    //if not terminal
    //public FeatureStructure getSubfeatures();

    public Feature[] getSubfeatures();
}
