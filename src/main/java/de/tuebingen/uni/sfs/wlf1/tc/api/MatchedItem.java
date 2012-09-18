/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.api;

import java.util.Set;

/**
 * @author Yana Panchenko
 *
 */
public interface MatchedItem {
	
	public String[] getOriginCorpusTokenIds();

	public Set<String> getTargetNames();
	
	public String getTargetValue(String targetName);
	
	public Set<String> getCategoriesNames();
	
	public String getCategoryValue(String categoryName);

}
