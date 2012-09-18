/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.api;

/**
 * @author Yana Panchenko
 *
 */
public interface ReferencedEntity {
	
	public String getExternalId();
	public Reference[] getReferences();

}
