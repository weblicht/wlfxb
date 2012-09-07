package de.tuebingen.uni.sfs.wlf1.tc.api;

import java.util.List;

public interface LemmasLayer extends TextCorpusLayer {

    public int size();

    public Lemma getLemma(int index);

    public Lemma getLemma(Token token);

    public Token[] getTokens(Lemma lemma);

    public Lemma addLemma(String lemmaString, Token lemmaToken);

    public Lemma addLemma(String lemmaString, List<Token> lemmaTokens);
}
