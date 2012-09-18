/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.api;
import java.util.List;

/**
 * @author Yana Panchenko
 *
 */
public interface ConstituentParsingLayer extends TextCorpusLayer {
	
	public boolean isEmpty();
	
	public int size();
	
	public String getTagset();
	
	public ConstituentParse getParse(int index);
	
	public Constituent getParseRoot(int index);
	
	public Token[] getTokens(ConstituentParse parse);
	
	public Token[] getTokens(Constituent constituent);
	
	/**
	 * Creates non-terminal constituent with the given category and constituent children.
	 * Children should have been created by the same ConstituentParsingLayer object before
	 */
	public Constituent createConstituent(String category, List<Constituent> children);
	public Constituent createConstituent(String category, String edge, List<Constituent> children);
	
	public Constituent createConstituent(String category, List<Constituent> children, String id);
	public Constituent createConstituent(String category, String edge, List<Constituent> children, String id);

	/**
	 * Creates non-terminal constituent with the given category, children should be added later
	 */
	public Constituent createConstituent(String category);
	public Constituent createConstituent(String category, String edge);

	public Constituent createConstituent(String category, String edge, String id);
	
	/**
	 * Adds constituent child to a parent constituent. Both child and parent constituent
	 * should have been created by the same ConstituentParsingLayer object before
	 */
	public Constituent addChild(Constituent parent, Constituent child);
	
	/**
	 * Adds secondary edge child to a constituent. Both child and parent constituent
	 * should have been created by the same ConstituentParsingLayer object before
	 */
	public Constituent addSecondaryEdgeChild(Constituent parent, Constituent child, String edgeLabel);
	
	/**
	 * Gets the Constituent reference by this ConstituentReference object
	 */
	public Constituent getConstituent(ConstituentReference cref);
	
	/**
	 * Creates terminal constituent with the given category and tokens
	 */
	public Constituent createTerminalConstituent(String category, List<Token> tokens);
	public Constituent createTerminalConstituent(String category, String edge, List<Token> tokens);
	
	public Constituent createTerminalConstituent(String category, List<Token> tokens, String id);
	public Constituent createTerminalConstituent(String category, String edge, List<Token> tokens, String id);
	
	/**
	 * Creates terminal constituent with the given category and token
	 */
	public Constituent createTerminalConstituent(String category, Token token);
	public Constituent createTerminalConstituent(String category, String edge, Token token);
	
	public Constituent createTerminalConstituent(String category, String edge, Token token, String id);
	
	/**
	 * Creates sentence parse with the given constituent root. Root should have been
	 * created by the same ConstituentParsingLayer object before
	 */
	public ConstituentParse addParse(Constituent root);

}
