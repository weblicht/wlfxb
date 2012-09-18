/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.api;

/**
 * @author Yana Panchenko
 *
 */
public interface Constituent {
	
	public boolean isTerminal();
	public boolean isEmptyTerminal();
	public Constituent[] getChildren();
	public ConstituentReference[] getSecondaryEdgeChildren();
	public String getCategory();
	public String getEdge();

}
