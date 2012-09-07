/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.tc.api.TokensLayer;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import java.util.ArrayList;
import java.util.List;
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
            this.connector.tokenId2ItsToken.put(token.tokenId, token);
        }
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
        return addToken(tokenString, null, null);
    }

    @Override
    public Token addToken(String tokenString, int start, int end) {
        return addToken(tokenString, (Integer) start, (Integer) end);
    }

    public Token addToken(String tokenString, Integer start, Integer end) {
        TokenStored token = new TokenStored();
        int tokenCount = tokens.size();
        token.tokenId = TokenStored.ID_PREFIX + tokenCount;
        token.tokenString = tokenString;
        if (start != null && end != null) {
            token.start = start;
            token.end = end;
            this.charOffsets = true;
        }
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(XML_NAME);
        sb.append(" {");
        if (hasCharOffsets()) {
            sb.append(CommonAttributes.CHAR_OFFSETS + " " + Boolean.toString(charOffsets));
        }
        sb.append("}: ");
        sb.append(tokens.toString());
        return sb.toString();
    }
}
