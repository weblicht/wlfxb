package de.tuebingen.uni.sfs.wlf1.lx.api;

import java.util.List;

public interface RelationsLayer extends LexiconLayer {

    public int size();

    public Relation getRelation(int index);

    public Relation[] getRelations(Lemma lemma);

    public Lemma[] getLemmas(Relation relation);

    public String[] getWords(Relation relation);

    public Relation addRelation(String type, String function, Integer frequency, List<Term> terms);

    public Relation addRelation(String type, String function, Integer frequency, Sig sig, List<Term> terms);

    public Term createTerm(Lemma lemma);

    public Term createTerm(String word);

    public Sig createSig(String measure, double value);
}
