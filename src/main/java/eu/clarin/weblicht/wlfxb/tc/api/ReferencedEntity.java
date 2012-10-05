/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.api;

/**
 * @author Yana Panchenko
 *
 */
public interface ReferencedEntity {

    public String getExternalId();

    public Reference[] getReferences();
}
