package de.tuebingen.uni.sfs.wlf1.lx.api;

/**
 * @author Yana Panchenko
 *
 */

public interface Lexicon {
	
	public LemmasLayer getLemmasLayer();
	public LemmasLayer createLemmasLayer();
	
	public PosTagsLayer getPosTagsLayer();
	public PosTagsLayer createPosTagsLayer(String tagset);
	
	public FrequenciesLayer getFrequenciesLayer();
	public FrequenciesLayer createFrequenciesLayer();
	
	public RelationsLayer getRelationsLayer();
	public RelationsLayer createRelationsLayer();
}
