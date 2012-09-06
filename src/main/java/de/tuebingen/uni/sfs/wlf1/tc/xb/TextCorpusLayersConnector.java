package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.*;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
public class TextCorpusLayersConnector {

    // maps for connecting elements of different layers that reference one another
    Map<String, Token> tokenId2ItsToken = new HashMap<String, Token>();
    Map<String, Lemma> lemmaId2ItsLemma = new HashMap<String, Lemma>();
    Map<Token, Lemma> token2ItsLemma = new HashMap<Token, Lemma>();
    Map<Token, PosTag> token2ItsPosTag = new HashMap<Token, PosTag>();
    Map<Token, Sentence> token2ItsSentence = new HashMap<Token, Sentence>();
    Map<Token, MorphologyAnalysis> token2ItsAnalysis = new HashMap<Token, MorphologyAnalysis>();
    Map<Token, NamedEntity> token2ItsNE = new HashMap<Token, NamedEntity>();
    Map<Token, Referent> token2ItsReferent = new HashMap<Token, Referent>();
    Map<Token, Relation> token2ItsRelation = new HashMap<Token, Relation>();
    Map<Token, WordSplit> token2ItsSplit = new HashMap<Token, WordSplit>();
    Map<Token, PhoneticsSegment> token2ItsPhseg = new HashMap<Token, PhoneticsSegment>();
    Map<Lemma, Orthform> lemma2ItsSynonyms = new HashMap<Lemma, Orthform>();
    Map<Lemma, Orthform> lemma2ItsAntonyms = new HashMap<Lemma, Orthform>();
    Map<Lemma, Orthform> lemma2ItsHyponyms = new HashMap<Lemma, Orthform>();
    Map<Lemma, Orthform> lemma2ItsHyperonyms = new HashMap<Lemma, Orthform>();
    Map<String, EmptyTokenStored> emptyTokId2EmptyTok = new HashMap<String, EmptyTokenStored>();

    TextCorpusLayersConnector() {
        super();
    }
}
