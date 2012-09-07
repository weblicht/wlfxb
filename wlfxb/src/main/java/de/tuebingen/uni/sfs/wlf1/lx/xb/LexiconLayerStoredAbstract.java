/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.lx.xb;

import de.tuebingen.uni.sfs.wlf1.lx.api.LexiconLayer;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Yana Panchenko
 *
 */
@XmlTransient
public abstract class LexiconLayerStoredAbstract implements LexiconLayer {

    /**
     * Should not be used directly by users. Should be extended by all
     * implementations of TextCorpusLayers, as they might depend on other layers
     * and therefore be able to get referenced/referencing information.
     *
     * @param connector
     */
    protected abstract void setLayersConnector(LexiconLayersConnector connector);
}
