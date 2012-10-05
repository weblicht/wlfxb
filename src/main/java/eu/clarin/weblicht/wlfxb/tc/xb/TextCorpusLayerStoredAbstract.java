/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.xb;

import eu.clarin.weblicht.wlfxb.tc.api.TextCorpusLayer;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Yana Panchenko
 *
 */
@XmlTransient
public abstract class TextCorpusLayerStoredAbstract implements TextCorpusLayer {

    /**
     * Should not be used directly by users. Should be extended by all
     * implementations of TextCorpusLayers, as they might depend on other layers
     * and therefore be able to get referenced/referencing information.
     *
     * @param connector
     */
    protected abstract void setLayersConnector(TextCorpusLayersConnector connector);
}
