/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.api;

import java.util.List;

/**
 * @author Yana Panchenko
 *
 */
public interface CoreferencesLayer {

    public String getTagset();

    public boolean hasExternalReferences();

    public String getExternalReferenceSource();

    public int size();

    public Referent getReferent(int index);

    public Referent getReferent(Token token);

    public Token[] getTokens(Coreference coreference);

    public Token[] getMinimumTokens(Coreference coreference);

    public Referent addReferent(List<Coreference> coreferences);

    public Referent addReferent(List<Coreference> coreferences, String externalId);

    public Referent addReferent(String referentType, Coreference coreference);

    public Referent addReferent(String referentType, List<Coreference> coreference);

    public Referent addReferent(String referentType, Coreference coreference, String externalId);

    public Referent addReferent(String referentType, List<Coreference> coreference, String externalId);

    public Coreference createCoreference(String type, String role,
            List<Token> coreferenceTokens, List<Token> minCoreferenceTokens);

    public Coreference createCoreference(String type,
            List<Token> coreferenceTokens, List<Token> minCoreferenceTokens);

    public Coreference createCoreference(List<Token> coreferenceTokens);
}
