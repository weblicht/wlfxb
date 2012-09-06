package de.tuebingen.uni.sfs.wlf1.lx.api;

/**
 * @author Yana Panchenko
 *
 */
public interface Lexicon {

    public LemmasLayer getLemmasLayer();

    public PosTagsLayer getPosTagsLayer();

    public FrequenciesLayer getFrequenciesLayer();

    public RelationsLayer getRelationsLayer();
}
