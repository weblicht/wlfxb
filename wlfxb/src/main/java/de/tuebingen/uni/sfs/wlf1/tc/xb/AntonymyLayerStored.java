/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.Lemma;
import de.tuebingen.uni.sfs.wlf1.tc.api.Orthform;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = AntonymyLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class AntonymyLayerStored extends LexicalSemanticsLayerStored {

    public static final String XML_NAME = "antonymy";

    protected AntonymyLayerStored() {
        super();
    }

    protected AntonymyLayerStored(TextCorpusLayersConnector connector) {
        super(connector);
    }

    protected AntonymyLayerStored(String source) {
        super(source);
    }

    protected void setLayersConnector(TextCorpusLayersConnector connector) {
        super.connector = connector;
        for (OrthformStored orthform : super.orthforms) {
            for (String lemRef : orthform.lemmaRefs) {
                connector.lemma2ItsAntonyms.put(connector.lemmaId2ItsLemma.get(lemRef), orthform);
            }
        }
    }

    @Override
    public Orthform getOrthform(Lemma lemma) {
        return super.connector.lemma2ItsAntonyms.get(lemma);
    }

    @Override
    public String toString() {
        return XML_NAME + " " + super.toString();
    }
}
