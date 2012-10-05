/**
 *
 */
package eu.clarin.weblicht.wlfxb.ed.api;

/**
 * @author Yana Panchenko
 *
 */
public interface ExternalDataLayer {

    public String getDataMimeType();

    public String getLink();

    public void addLink(String dataURI);
}
