package de.tuebingen.uni.sfs.wlf1.tc.api;

public interface TokensLayer extends TextCorpusLayer {

    //public List<Token> getTokens();
    public int size();

    public Token getToken(int index);

    public Token getToken(String tokenId);

    public boolean hasCharOffsets();

    public Token addToken(String tokenString);

    public Token addToken(String tokenString, String tokenId);

    public Token addToken(String tokenString, long start, long end);

    public Token addToken(String tokenString, long start, long end, String tokenId);
}
