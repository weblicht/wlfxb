/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.api;

import eu.clarin.weblicht.wlfxb.tc.xb.TextSpanStored;

/**
 * @author Yana Panchenko
 *
 */
public interface TextSpan {

    //public TextSpanType getType();
    public String getType();

    public boolean isTerminal();

    public String getValue();

    public TextSpanStored[] getSubspans();
}
