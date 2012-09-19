/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.lx.xb;

import de.tuebingen.uni.sfs.wlf1.lx.api.Lemma;
import de.tuebingen.uni.sfs.wlf1.lx.api.LemmasLayer;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = LemmasLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class LemmasLayerStored extends LexiconLayerStoredAbstract implements LemmasLayer {

    public static final String XML_NAME = "lemmas";
    private LexiconLayersConnector connector;
    @XmlElement(name = LemmaStored.XML_NAME)
    private List<LemmaStored> lemmas = new ArrayList<LemmaStored>();

    protected LemmasLayerStored() {
    }

    protected LemmasLayerStored(LexiconLayersConnector connector) {
        this.connector = connector;
    }

    public void setLayersConnector(LexiconLayersConnector connector) {
        this.connector = connector;
        for (LemmaStored lemma : lemmas) {
            this.connector.lemmaId2ItsLemma.put(lemma.lemmaId, lemma);
        }
    }

    @Override
    public Lemma getLemma(int index) {
        return lemmas.get(index);
    }

    @Override
    public Lemma getLemma(String lemmaId) {
        Lemma lemma = connector.lemmaId2ItsLemma.get(lemmaId);
        return lemma;
    }

    @Override
    public Lemma addLemma(String lemmaString) {
        LemmaStored lemma = new LemmaStored();
        int lemmaCount = lemmas.size();
        lemma.lemmaId = LemmaStored.ID_PREFIX + lemmaCount;
        lemma.lemmaString = lemmaString;
        connector.lemmaId2ItsLemma.put(lemma.lemmaId, lemma);
        lemmas.add(lemma);
        return lemma;
    }

    @Override
    public boolean isEmpty() {
        return lemmas.isEmpty();
    }

    @Override
    public int size() {
        return lemmas.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(XML_NAME);
        sb.append(" : ");
        sb.append(lemmas.toString());
        return sb.toString();
    }
}
