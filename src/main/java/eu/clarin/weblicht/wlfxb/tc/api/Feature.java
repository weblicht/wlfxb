/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.api;

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
