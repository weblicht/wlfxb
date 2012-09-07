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
     * Creates non-terminal constituent with the given category and constituent
     * children. Children should have been created by the same
     * ConstituentParsingLayer object before
     */
    public Constituent createConstituent(String category, List<Constituent> children);

    /**
     * Creates non-terminal constituent with the given category, children should
     * be added later
     */
    public Constituent createConstituent(String category);

    /**
     * Adds constituent child to a parent constituent. Both child and parent
     * constituent should have been created by the same ConstituentParsingLayer
     * object before
     */
    public Constituent addChild(Constituent parent, Constituent child);

    /**
     * Creates terminal constituent with the given category and tokens
     */
    public Constituent createTerminalConstituent(String category, List<Token> tokens);

    /**
     * Creates terminal constituent with the given category and token
     */
    public Constituent createTerminalConstituent(String category, Token token);

    /**
     * Creates sentence parse with the given constituent root. Root should have
     * been created by the same ConstituentParsingLayer object before
     */
    public ConstituentParse addParse(Constituent root);
}
