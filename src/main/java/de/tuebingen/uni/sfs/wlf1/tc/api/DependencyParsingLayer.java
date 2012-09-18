/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.api;

import java.util.List;

/**
 * @author Yana Panchenko
 *
 */
public interface DependencyParsingLayer {
	
public boolean isEmpty();
	
	public int size();
	
	public String getTagset();
	
	public DependencyParse getParse(int index);
	
	public Token[] getGovernorTokens(Dependency dependency);
	
	public Token[] getDependentTokens(Dependency dependency);
	
	//TODO could be empty tokens...
	public Dependency createDependency(String function, List<Token> dependent, List<Token> governor);
	public Dependency createDependency(String function, List<Token> dependent);
	public Dependency createDependency(List<Token> dependent, List<Token> governor);
	public Dependency createDependency(List<Token> dependent);
	public Dependency createDependency(String function, Token dependent, Token governor);
	public Dependency createDependency(String function, Token dependent);
	public Dependency createDependency(Token dependent, Token governor);
	public Dependency createDependency(Token dependent);
	public Token createEmptyToken(String tokenString);
	public DependencyParse addParse(List<Dependency> dependencies);


	public boolean hasEmptyTokens();

	public boolean hasMultipleGovernors();

}
