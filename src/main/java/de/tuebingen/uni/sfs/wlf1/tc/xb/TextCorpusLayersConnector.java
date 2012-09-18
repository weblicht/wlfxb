package de.tuebingen.uni.sfs.wlf1.tc.xb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tuebingen.uni.sfs.wlf1.tc.api.Constituent;
import de.tuebingen.uni.sfs.wlf1.tc.api.DiscourseConnective;
import de.tuebingen.uni.sfs.wlf1.tc.api.GeoPoint;
import de.tuebingen.uni.sfs.wlf1.tc.api.Lemma;
import de.tuebingen.uni.sfs.wlf1.tc.api.MatchedItem;
import de.tuebingen.uni.sfs.wlf1.tc.api.MorphologyAnalysis;
import de.tuebingen.uni.sfs.wlf1.tc.api.NamedEntity;
import de.tuebingen.uni.sfs.wlf1.tc.api.OrthCorrection;
import de.tuebingen.uni.sfs.wlf1.tc.api.Orthform;
import de.tuebingen.uni.sfs.wlf1.tc.api.PhoneticsSegment;
import de.tuebingen.uni.sfs.wlf1.tc.api.PosTag;
import de.tuebingen.uni.sfs.wlf1.tc.api.Reference;
import de.tuebingen.uni.sfs.wlf1.tc.api.ReferencedEntity;
import de.tuebingen.uni.sfs.wlf1.tc.api.Relation;
import de.tuebingen.uni.sfs.wlf1.tc.api.Sentence;
import de.tuebingen.uni.sfs.wlf1.tc.api.TextSpan;
import de.tuebingen.uni.sfs.wlf1.tc.api.TextSpanType;
import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.tc.api.WordSplit;



@SuppressWarnings("deprecation")
public class TextCorpusLayersConnector {
	
	// maps for connecting elements of different layers that reference one another
	Map<String,Token> tokenId2ItsToken = new HashMap<String,Token>();
	List<TokenStored> tokens;
	Map<String,Lemma> lemmaId2ItsLemma = new HashMap<String,Lemma>();
	
	Map<Token,Lemma> token2ItsLemma = new HashMap<Token,Lemma>();
	Map<Token,PosTag> token2ItsPosTag = new HashMap<Token,PosTag>();
	Map<Token,Sentence> token2ItsSentence = new HashMap<Token,Sentence>();
	Map<Token,MorphologyAnalysis> token2ItsAnalysis = new HashMap<Token,MorphologyAnalysis>();
	//Map<Token,NamedEntity> token2ItsNE = new HashMap<Token,NamedEntity>();
	Map<Token,List<NamedEntity>> token2ItsNE = new HashMap<Token,List<NamedEntity>>();
	//Map<Token,Referent> token2ItsReferent = new HashMap<Token,Referent>();
	Map<Token,List<ReferencedEntity>> token2ItsReferent = new HashMap<Token,List<ReferencedEntity>>();
	Map<String,Reference> referenceId2ItsReference = new HashMap<String,Reference>();
	
	Map<Token,Relation> token2ItsRelation = new HashMap<Token,Relation>();
	Map<Token,WordSplit> token2ItsSplit = new HashMap<Token,WordSplit>();
	Map<Token,PhoneticsSegment> token2ItsPhseg = new HashMap<Token,PhoneticsSegment>();
	Map<Token,MatchedItem> token2ItsMatchedItem = new HashMap<Token,MatchedItem>();
	Map<Token,GeoPoint> token2ItsGeopoint = new HashMap<Token,GeoPoint>();
	
	Map<Lemma,Orthform> lemma2ItsSynonyms = new HashMap<Lemma,Orthform>();
	Map<Lemma,Orthform> lemma2ItsAntonyms = new HashMap<Lemma,Orthform>();
	Map<Lemma,Orthform> lemma2ItsHyponyms = new HashMap<Lemma,Orthform>();
	Map<Lemma,Orthform> lemma2ItsHyperonyms = new HashMap<Lemma,Orthform>();
	
	Map<String,EmptyTokenStored> emptyTokId2EmptyTok = new HashMap<String,EmptyTokenStored>();
	
	
	Map<Token,OrthCorrection> token2ItsCorrection = new HashMap<Token,OrthCorrection>();
	Map<TextSpanType,Map<Token,TextSpan>> token2ItsTextSpans = new HashMap<TextSpanType,Map<Token,TextSpan>>();
	
	Map<Token,DiscourseConnective> token2ItsDConnective = new HashMap<Token,DiscourseConnective>();
	
	Map<String,Constituent> constitId2ItsConstit = new HashMap<String,Constituent>();
    
	TextCorpusLayersConnector() {
		super();
		initSubmaps();
	}

	private void initSubmaps() {
		for (TextSpanType value : TextSpanType.values()) {
			token2ItsTextSpans.put(value, new HashMap<Token,TextSpan>());
		}
	}
	
}
