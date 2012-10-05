/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.api;

import eu.clarin.weblicht.wlfxb.tc.xb.PronunciationType;

/**
 * @author Yana Panchenko
 *
 */
public interface Pronunciation {

    public PronunciationType getType();

    public String getCanonical();

    public String getRealized();

    public Float getOnsetInSeconds();

    public Float getOffsetInSeconds();

    public boolean hasChildren();

    public boolean hasOnOffsets();

    public Pronunciation[] getChildren();
}
