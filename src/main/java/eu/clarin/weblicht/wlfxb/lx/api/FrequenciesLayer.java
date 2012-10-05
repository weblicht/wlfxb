package eu.clarin.weblicht.wlfxb.lx.api;

public interface FrequenciesLayer extends LexiconLayer {

    public int size();

    public Frequency getFrequency(int index);

    public Frequency getFrequency(Lemma lemma);

    public Lemma getLemma(Frequency frequency);

    public Frequency addFrequency(Lemma lemma, int frequency);
}
