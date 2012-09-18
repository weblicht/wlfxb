/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.api;

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
