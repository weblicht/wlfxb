/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.api;

import eu.clarin.weblicht.wlfxb.tc.xb.MatchedItemStored;

/**
 * @author Yana Panchenko
 *
 */
public interface MatchedCorpus {

    public MatchedItemStored[] getMatchedItems();

    public String getName();

    public String getPID();
}
