package eu.clarin.weblicht.wlfxb.lx.api;

public interface LemmasLayer extends LexiconLayer {

    public int size();

    public Lemma getLemma(int index);

    public Lemma getLemma(String lemmaId);

    public Lemma addLemma(String tokenString);
}
