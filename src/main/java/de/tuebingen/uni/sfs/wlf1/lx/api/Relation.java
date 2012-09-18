package de.tuebingen.uni.sfs.wlf1.lx.api;

public interface Relation {
	
	public String getType();
	public String getFunction();
	public Integer getFrequency();
	public Sig getSig();
	//public List<Term> getTerms();

}
