package de.tuebingen.uni.sfs.wlf1.tc.api;

import java.util.List;

public interface SentencesLayer extends TextCorpusLayer {

    public boolean hasCharOffsets();

    public int size();

    public Sentence getSentence(int index);

    public Sentence getSentence(Token token);

    public Token[] getTokens(Sentence sentence);

    public Sentence addSentence(List<Token> tokens);

    public Sentence addSentence(List<Token> tokens, int start, int end);
}
