/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.tc.api.TokensLayer;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = TokensLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class TokensLayerStored extends TextCorpusLayerStoredAbstract implements TokensLayer {

    public static final String XML_NAME = "tokens";
    private TextCorpusLayersConnector connector;
    @XmlElement(name = TokenStored.XML_NAME)
    private List<TokenStored> tokens = new ArrayList<TokenStored>();
    @XmlAttribute(name = CommonAttributes.CHAR_OFFSETS)
    private Boolean charOffsets;

    protected TokensLayerStored() {
    }

    protected TokensLayerStored(Boolean hasCharOffsets) {
        this.charOffsets = hasCharOffsets;
    }

    protected TokensLayerStored(TextCorpusLayersConnector connector) {
        this.connector = connector;
    }

    protected void setLayersConnector(TextCorpusLayersConnector connector) {
        this.connector = connector;
        for (TokenStored token : tokens) {
            token.order = this.connector.tokenId2ItsToken.size();
            this.connector.tokenId2ItsToken.put(token.tokenId, token);
        }
        this.connector.tokens = tokens;
    }

    @Override
    public Token getToken(int index) {
        Token token = tokens.get(index);
        return token;
    }

    @Override
    public Token getToken(String tokenId) {
        Token token = connector.tokenId2ItsToken.get(tokenId);
        return token;
    }

    @Override
    public Token addToken(String tokenString) {
        int tokenCount = tokens.size();
        String tokenId = TokenStored.ID_PREFIX + tokenCount;
        return addToken(tokenString, null, null, tokenId);
    }

    @Override
    public Token addToken(String tokenString, String tokenId) {
        return addToken(tokenString, null, null, tokenId);
    }

    @Override
    public Token addToken(String tokenString, long start, long end) {
        int tokenCount = tokens.size();
        String tokenId = TokenStored.ID_PREFIX + tokenCount;
        return addToken(tokenString, (Long) start, (Long) end, tokenId);
    }

    @Override
    public Token addToken(String tokenString, long start, long end, String tokenId) {
        return addToken(tokenString, (Long) start, (Long) end, tokenId);
    }

    private Token addToken(String tokenString, Long start, Long end, String tokenId) {
        TokenStored token = new TokenStored();
        token.tokenId = tokenId;
        token.tokenString = tokenString;
        if (start != null && end != null) {
            token.start = start;
            this.charOffsets = true;
        }
        token.order = tokens.size();
        connector.tokenId2ItsToken.put(token.tokenId, token);
        tokens.add(token);
        return token;
    }

    @Override
    public boolean isEmpty() {
        if (tokens.size() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return tokens.size();
    }

    @Override
    public boolean hasCharOffsets() {
        if (charOffsets == null) {
            return false;
        }
        return charOffsets;
    }

    protected boolean beforeMarshal(Marshaller m) {
        setFalseAttrToNull();
        return true;
    }

    private void setFalseAttrToNull() {
        if (this.charOffsets == Boolean.FALSE) {
            this.charOffsets = null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(XML_NAME);
        sb.append(" {");
        if (hasCharOffsets()) {
            sb.append(CommonAttributes.CHAR_OFFSETS).append(" ").append(Boolean.toString(charOffsets));
        }
        sb.append("}: ");
        sb.append(tokens.toString());
        return sb.toString();
    }
}
