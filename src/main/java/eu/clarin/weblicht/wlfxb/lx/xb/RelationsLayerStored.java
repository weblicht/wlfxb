/**
 *
 */
package eu.clarin.weblicht.wlfxb.lx.xb;

import eu.clarin.weblicht.wlfxb.lx.api.*;
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
@XmlRootElement(name = RelationsLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class RelationsLayerStored extends LexiconLayerStoredAbstract implements RelationsLayer {

    public static final String XML_NAME = "word-relations";
    @XmlElement(name = RelationStored.XML_NAME)
    private List<RelationStored> rels = new ArrayList<RelationStored>();
    private LexiconLayersConnector connector;

    protected RelationsLayerStored() {
    }

    protected RelationsLayerStored(LexiconLayersConnector connector) {
        this.connector = connector;
    }

    protected void setLayersConnector(LexiconLayersConnector connector) {
        this.connector = connector;
        for (RelationStored rel : rels) {
            connect(rel);

        }
    }

    @Override
    public boolean isEmpty() {
        return rels.isEmpty();
    }

    @Override
    public int size() {
        return rels.size();
    }

    @Override
    public Relation getRelation(int index) {
        return rels.get(index);
    }

    @Override
    public Relation[] getRelations(Lemma lemma) {
        List<Relation> relList = connector.lemma2ItsRels.get(lemma);
        if (relList != null) {
            return relList.toArray(new Relation[relList.size()]);
        } else {
            return new Relation[0];
        }
    }

    @Override
    public Lemma[] getLemmas(Relation rel) {
        if (rel instanceof RelationStored) {
            RelationStored relStored = (RelationStored) rel;
            List<Lemma> lemmas = new ArrayList<Lemma>();
            for (TermStored t : relStored.terms) {
                if (t.lemId != null) {
                    lemmas.add(connector.lemmaId2ItsLemma.get(t.lemId));
                }
            }
            return lemmas.toArray(new Lemma[lemmas.size()]);
        } else {
            return null;
        }
    }

    @Override
    public String[] getWords(Relation rel) {
        if (rel instanceof RelationStored) {
            RelationStored relStored = (RelationStored) rel;
            List<String> words = new ArrayList<String>();
            for (TermStored t : relStored.terms) {
                if (t.lemId != null) {
                    words.add(connector.lemmaId2ItsLemma.get(t.lemId).getString());
                } else if (t.word != null) {
                    words.add(t.word);
                }
            }
            return words.toArray(new String[words.size()]);
        } else {
            return null;
        }
    }

    @Override
    public Relation addRelation(String type, String function, Integer frequency, List<Term> terms) {
        RelationStored rel = new RelationStored();
        rel.type = type;
        rel.function = function;
        rel.freq = frequency;
        for (Term term : terms) {
            if (term instanceof TermStored) {
                rel.terms.add((TermStored) term);
            }
        }
        connect(rel);
        rels.add(rel);
        return rel;
    }

    private void connect(RelationStored rel) {
        for (TermStored term : rel.terms) {
            if (term.lemId != null) {
                Lemma lemma = connector.lemmaId2ItsLemma.get(term.lemId);
                if (!connector.lemma2ItsRels.containsKey(lemma)) {
                    connector.lemma2ItsRels.put(lemma, new ArrayList<Relation>());
                }
                connector.lemma2ItsRels.get(lemma).add(rel);
            }
        }
    }

    @Override
    public Relation addRelation(String type, String function, Integer frequency, Sig sig, List<Term> terms) {
        RelationStored rel = (RelationStored) addRelation(type, function, frequency, terms);
        if (sig instanceof SigStored) {
            rel.sig = (SigStored) sig;
        }
        return rel;
    }

    @Override
    public Term createTerm(Lemma lemma) {
        TermStored term = new TermStored();
        term.lemId = lemma.getID();
        return term;
    }

    @Override
    public Term createTerm(String word) {
        TermStored term = new TermStored();
        term.word = word;
        return term;
    }

    @Override
    public Sig createSig(String measure, double value) {
        SigStored sig = new SigStored(measure, value);
        return sig;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(XML_NAME);
        sb.append(" : ");
        sb.append(rels.toString());
        return sb.toString();
    }
}
