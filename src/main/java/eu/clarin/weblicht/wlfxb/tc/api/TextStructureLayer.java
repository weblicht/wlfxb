/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.api;

import eu.clarin.weblicht.wlfxb.io.WLFormatException;
import java.util.List;

/**
 * @author Yana Panchenko
 *
 */
public interface TextStructureLayer {

    public int size();

    public TextSpan getSpan(int index);

    public List<TextSpan> getSpans(Token token);

    //public TextSpan getSpan(Token token, TextSpanType type);
    public TextSpan getSpan(Token token, String type);

    //public List<TextSpan> getSpans(TextSpanType type);
    public List<TextSpan> getSpans(String type);
    
    public Token[] getTokens(TextSpan span);

    //public TextSpan addSpan(Token spanStart, Token spanEnd, TextSpanType type);
    
    public TextSpan addSpan(Token spanStart, Token spanEnd, String type);
    
    public TextSpan addSpan(Token spanStart, Token spanEnd, String type, String value);
    
    public TextSpan addSpan(TextSpan parentSpan, Token spanStart, Token spanEnd, String type) throws WLFormatException;
    
    public TextSpan addSpan(TextSpan parentSpan, Token spanStart, Token spanEnd, String type, String value) throws WLFormatException;
    
}
