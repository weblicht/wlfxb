/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.api;

import java.util.List;
import java.util.Map;

/**
 * @author Yana Panchenko
 *
 */
public interface MatchesLayer extends TextCorpusLayer {

    public boolean isEmpty();

    public int size();

    public String getQueryType();

    public String getQueryString();

    public MatchedCorpus getCorpus(int index);

    public MatchedCorpus addCorpus(String corpusName, String corpusPID);

    public MatchedItem addItem(MatchedCorpus corpusToAddItem,
            List<Token> itemTokens, List<String> itemOriginCorpusTokenIds);

    public MatchedItem addItem(MatchedCorpus corpusToAddItem,
            List<Token> itemTokens);

    public MatchedItem addItem(MatchedCorpus corpusToAddItem,
            List<Token> itemTokens, List<String> itemOriginCorpusTokenIds,
            Map<String, String> itemTargets, Map<String, String> itemCategories);

    public MatchedItem addItem(MatchedCorpus corpusToAddItem,
            List<Token> itemTokens,
            Map<String, String> itemTargets, Map<String, String> itemCategories);

    public Token[] getTokens(MatchedItem item);

    public MatchedItem getMatchedItem(Token token);
}
