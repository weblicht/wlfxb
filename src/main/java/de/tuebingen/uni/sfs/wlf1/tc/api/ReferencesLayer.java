/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.api;

import java.util.List;

/**
 * @author Yana Panchenko
 *
 */
public interface ReferencesLayer {

    public String getTypetagset();

    public String getReltagset();

    public boolean hasExternalReferences();

    public String getExternalReferenceSource();

    public int size();

    public ReferencedEntity getReferencedEntity(int index);

    public List<ReferencedEntity> getReferencedEntities(Token token);

    public Token[] getTokens(Reference reference);

    public Token[] getMinimumTokens(Reference reference);

    public Reference[] getTarget(Reference reference);

    public ReferencedEntity addReferent(List<Reference> references);

    public ReferencedEntity addReferent(List<Reference> references, String externalId);

    public Reference createReference(List<Token> referenceTokens);

    public Reference createReference(List<Token> referenceTokens, List<Token> minReferenceTokens);

    public Reference createReference(String type, List<Token> referenceTokens, List<Token> minReferenceTokens);

    public void addRelation(Reference reference, String relation, Reference... target);
}
