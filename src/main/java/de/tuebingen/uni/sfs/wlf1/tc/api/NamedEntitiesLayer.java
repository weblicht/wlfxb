/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.api;

import java.util.List;
import java.util.Set;

/**
 * @author Yana Panchenko
 *
 */
public interface NamedEntitiesLayer {

    public int size();

    public String getType();

    public NamedEntity getEntity(int index);

    public NamedEntity getEntity(Token token);

    public List<NamedEntity> getEntities(Token token);

    public Token[] getTokens(NamedEntity entity);

    public NamedEntity addEntity(String entityType, Token entityToken);

    public NamedEntity addEntity(String entityType, List<Token> entityTokens);

    public Set<String> getFoundTypes();
}
