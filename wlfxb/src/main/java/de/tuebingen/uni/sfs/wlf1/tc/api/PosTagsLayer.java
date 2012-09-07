package de.tuebingen.uni.sfs.wlf1.tc.api;

import java.util.List;

public interface PosTagsLayer extends TextCorpusLayer {

    public int size();

    public String getTagset();

    public PosTag getTag(int index);

    public PosTag getTag(Token token);

    public Token[] getTokens(PosTag tag);

    public PosTag addTag(String tagString, Token tagToken);

    public PosTag addTag(String tagString, List<Token> tagTokens);
}
