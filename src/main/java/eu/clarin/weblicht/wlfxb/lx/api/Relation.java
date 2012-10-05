package eu.clarin.weblicht.wlfxb.lx.api;

public interface Relation {

    public String getType();

    public String getFunction();

    public Integer getFrequency();

    public Sig getSig();
    //public List<Term> getTerms();
}
