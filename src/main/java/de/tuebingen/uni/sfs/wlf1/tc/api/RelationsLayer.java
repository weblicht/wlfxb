/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.api;

import java.util.List;

/**
 * @author Yana Panchenko
 *
 */
@Deprecated
public interface RelationsLayer {
	
	public int size();
	public String getType();
	public Relation getRelation(int index);
	public Relation getRelation(Token token);
	public Token[] getTokens(Relation relation);
	public Relation addRelation(String function, Token relationToken);
	public Relation addRelation(String function, List<Token> relationTokens);
	public Relation addRelation(Token relationToken);
	public Relation addRelation(List<Token> relationTokens);

}
