package de.tuebingen.uni.sfs.wlf1.lx.api;

public interface PosTagsLayer extends LexiconLayer {

    public int size();

    public String getTagset();

    public PosTag getTag(int index);
    //can be several tags for each lemma

    public PosTag[] getTags(Lemma lemma);

    public Lemma getLemma(PosTag tag);

    public PosTag addTag(String tagString, Lemma tagLemma);
}
