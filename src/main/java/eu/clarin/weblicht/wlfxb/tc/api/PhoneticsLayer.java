/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.api;

import eu.clarin.weblicht.wlfxb.tc.xb.PronunciationType;
import java.util.List;

/**
 * @author Yana Panchenko
 *
 */
public interface PhoneticsLayer {

    public int size();

    public String getAlphabet();

    public PhoneticsSegment getSegment(int index);

    public PhoneticsSegment getSegment(Token token);

    public Token getToken(PhoneticsSegment segment);

    public Pronunciation createPronunciation(PronunciationType type,
            String canonicalPronunciation, String realizedPronunciation,
            float onsetInSeconds, float offsetInSeconds, List<Pronunciation> children);

    public Pronunciation createPronunciation(PronunciationType type,
            String canonicalPronunciation, String realizedPronunciation,
            List<Pronunciation> children);

    public Pronunciation createPronunciation(PronunciationType type, String realizedPronunciation,
            float onsetInSeconds, float offsetInSeconds, List<Pronunciation> children);

    public Pronunciation createPronunciation(PronunciationType type, String realizedPronunciation,
            float onsetInSeconds, float offsetInSeconds);

    public Pronunciation createPronunciation(PronunciationType type, String canonicalPronunciation);

    public Pronunciation addChild(Pronunciation parent, Pronunciation child);

    public PhoneticsSegment addSegment(Pronunciation pronunciation, Token token);

    public PhoneticsSegment addSegment(List<Pronunciation> pronunciations, Token token);
}
