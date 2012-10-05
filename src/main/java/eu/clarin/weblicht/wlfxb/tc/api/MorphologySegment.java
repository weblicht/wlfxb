/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.api;

/**
 * @author Yana Panchenko
 *
 */
public interface MorphologySegment {

    public String getCategory();

    public String getType();

    public String getFunction();

    public boolean hasCharoffsets();

    public Integer getStart();

    public Integer getEnd();

    public boolean isTerminal();
    //if terminal:

    public String getValue();
    //if not terminal:

    public MorphologySegment[] getSubsegments();
}
