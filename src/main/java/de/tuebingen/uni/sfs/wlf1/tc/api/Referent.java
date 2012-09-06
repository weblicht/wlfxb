/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.api;

/**
 * @author Yana Panchenko
 *
 */
public interface Referent {

    public String getType();

    public String getExternalId();

    public Coreference[] getCoreferences();
}
