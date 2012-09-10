/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.NamedEntitiesLayer;
import de.tuebingen.uni.sfs.wlf1.tc.api.NamedEntity;
import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import de.tuebingen.uni.sfs.wlf1.utils.WlfUtilities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = NamedEntitiesLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class NamedEntitiesLayerStored extends TextCorpusLayerStoredAbstract implements NamedEntitiesLayer {

    public static final String XML_NAME = "namedEntities";
    @XmlAttribute(name = CommonAttributes.TYPE, required = true)
    private String type;
    @XmlElement(name = NamedEntityStored.XML_NAME)
    private List<NamedEntityStored> entities = new ArrayList<NamedEntityStored>();
    TextCorpusLayersConnector connector;

    protected NamedEntitiesLayerStored() {
    }

    protected NamedEntitiesLayerStored(String type) {
        this.type = type;
    }

    protected NamedEntitiesLayerStored(TextCorpusLayersConnector connector) {
        this.connector = connector;
    }

    protected void setLayersConnector(TextCorpusLayersConnector connector) {
        this.connector = connector;
        for (NamedEntityStored ne : entities) {
            for (String tokRef : ne.tokRefs) {
                connector.token2ItsNE.put(connector.tokenId2ItsToken.get(tokRef), ne);
            }
        }
    }

    @Override
    public boolean isEmpty() {
        if (entities.size() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return entities.size();
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public NamedEntity getEntity(int index) {
        return entities.get(index);
    }

    @Override
    public NamedEntity getEntity(Token token) {
        NamedEntity tag = connector.token2ItsNE.get(token);
        return tag;
    }

    @Override
    public Token[] getTokens(NamedEntity entity) {
        if (entity instanceof NamedEntityStored) {
            NamedEntityStored ne = (NamedEntityStored) entity;
            return WlfUtilities.tokenIdsToTokens(ne.tokRefs, connector.tokenId2ItsToken);
        } else {
            return null;
        }
    }

    @Override
    public NamedEntity addEntity(String entityType, Token tagToken) {
        List<Token> tagTokens = Arrays.asList(new Token[]{tagToken});
        return addEntity(entityType, tagTokens);
    }

    @Override
    public NamedEntity addEntity(String entityType, List<Token> entityTokens) {
        NamedEntityStored ne = new NamedEntityStored();
        ne.type = entityType;
        ne.tokRefs = new String[entityTokens.size()];
        for (int i = 0; i < entityTokens.size(); i++) {
            Token token = entityTokens.get(i);
            ne.tokRefs[i] = token.getID();
            connector.token2ItsNE.put(token, ne);
        }
        entities.add(ne);
        return ne;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(XML_NAME);
        sb.append(" ");
        sb.append("{");
        sb.append(type);
        sb.append("} :");
        sb.append(entities.toString());
        return sb.toString();
    }
}
