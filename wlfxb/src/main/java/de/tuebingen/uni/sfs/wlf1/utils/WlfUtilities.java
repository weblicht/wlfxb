/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.utils;

import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import java.util.List;
import java.util.Map;

/**
 * @author Yana Panchenko
 *
 */
public class WlfUtilities {

    public static Token[] tokenIdsToTokens(String[] tokIds,
            Map<String, Token> tokenId2ItsToken) {
        Token[] tokens = new Token[tokIds.length];
        for (int i = 0; i < tokIds.length; i++) {
            tokens[i] = tokenId2ItsToken.get(tokIds[i]);
        }
        return tokens;
    }

    public static String[] tokens2TokenIds(List<Token> tokens) {
        String[] tokenIds = new String[tokens.size()];
        for (int i = 0; i < tokens.size(); i++) {
            tokenIds[i] = tokens.get(i).getID();
        }
        return tokenIds;
    }

    public static String layersErrorMessage(Class<?> interfaceOfLayerComponent,
            Class<?> interfaceOfLayer) {
        return "Applicable only to " + interfaceOfLayerComponent.getSimpleName()
                + " created-by/extracted-from the same " + interfaceOfLayer.getSimpleName();
    }
}
