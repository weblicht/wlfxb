/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.api;

import de.tuebingen.uni.sfs.wlf1.tc.xb.MatchedItemStored;

/**
 * @author Yana Panchenko
 *
 */
public interface MatchedCorpus {

    public MatchedItemStored[] getMatchedItems();

    public String getName();

    public String getPID();
}
