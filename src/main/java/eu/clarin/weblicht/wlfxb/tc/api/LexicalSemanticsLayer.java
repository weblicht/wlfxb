/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.api;

/**
 * @author Yana Panchenko
 *
 */
public interface LexicalSemanticsLayer {

    public String getSource();

    public int size();

    public Orthform getOrthform(int index);

    public Orthform getOrthform(Lemma lemma);

    public Lemma[] getLemmas(Orthform orthform);

    public Orthform addOrthform(String orthformValues, Lemma lemma);

    public Orthform addOrthform(String[] orthformValues, Lemma lemma);
}
