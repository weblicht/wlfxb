/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.xb;

import eu.clarin.weblicht.wlfxb.tc.api.Lemma;
import eu.clarin.weblicht.wlfxb.tc.api.Orthform;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = SynonymyLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class SynonymyLayerStored extends LexicalSemanticsLayerStored {

    public static final String XML_NAME = "synonymy";

    protected SynonymyLayerStored() {
    }

    protected SynonymyLayerStored(TextCorpusLayersConnector connector) {
        super(connector);
    }

    protected SynonymyLayerStored(String source) {
        super(source);
    }

    protected void setLayersConnector(TextCorpusLayersConnector connector) {
        super.connector = connector;
        for (OrthformStored orthform : super.orthforms) {
            for (String lemRef : orthform.lemmaRefs) {
                connector.lemma2ItsSynonyms.put(connector.lemmaId2ItsLemma.get(lemRef), orthform);
            }
        }
    }

    @Override
    public Orthform getOrthform(Lemma lemma) {
        return super.connector.lemma2ItsSynonyms.get(lemma);
    }

    @Override
    public String toString() {
        return XML_NAME + " " + super.toString();
    }
}
