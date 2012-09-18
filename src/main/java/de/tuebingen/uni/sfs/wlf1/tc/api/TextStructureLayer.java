/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.api;

import java.util.List;

/**
 * @author Yana Panchenko
 *
 */
public interface TextStructureLayer {
	
	public int size();
	public TextSpan getSpan(int index);
	public List<TextSpan> getSpans(Token token);
	public TextSpan getSpan(Token token, TextSpanType type);
	public List<TextSpan> getSpans(TextSpanType type);
	public Token[] getTokens(TextSpan span);
	public TextSpan addSpan(Token spanStart, Token spanEnd, TextSpanType type);
}
