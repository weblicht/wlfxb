/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.tuebingen.uni.sfs.wlf1.tc.api.Sentence;
import de.tuebingen.uni.sfs.wlf1.tc.api.SentencesLayer;
import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import de.tuebingen.uni.sfs.wlf1.utils.WlfUtilities;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name=SentencesLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class SentencesLayerStored extends TextCorpusLayerStoredAbstract implements SentencesLayer {
	
	public static final String XML_NAME = "sentences";
	
	@XmlElement(name=SentenceStored.XML_NAME, type=SentenceStored.class)
	List<SentenceStored> sentences = new ArrayList<SentenceStored>();
	@XmlAttribute(name=CommonAttributes.CHAR_OFFSETS)
	Boolean charOffsets;

	TextCorpusLayersConnector connector;
	
	protected SentencesLayerStored() {}
	
	protected SentencesLayerStored(Boolean hasCharOffsets) {
		this.charOffsets = hasCharOffsets;
	}
	
	protected SentencesLayerStored(TextCorpusLayersConnector connector) {
		this.connector = connector;
	}
	
	protected void setLayersConnector(TextCorpusLayersConnector connector) {
		this.connector = connector;
		for (SentenceStored sentence : sentences) {
			for (String tokRef : sentence.tokIds) {
				connector.token2ItsSentence.put(connector.tokenId2ItsToken.get(tokRef), sentence);
			}
		}
	}

	@Override
	public boolean isEmpty() {
		if (sentences.size() == 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public int size() {
		return sentences.size();
	}

	@Override
	public boolean hasCharOffsets() {
		if (charOffsets == null) {
			return false;
		}
		return charOffsets;
	}
	

	@Override
	public Sentence getSentence(int index) {
		return sentences.get(index);
	}


	@Override
	public Sentence getSentence(Token token) {
		Sentence sentence = connector.token2ItsSentence.get(token);
		return sentence;	
	}


	@Override
	public Token[] getTokens(Sentence sentence) {
		if (sentence instanceof SentenceStored) {
			SentenceStored sStored = (SentenceStored) sentence;
			return WlfUtilities.tokenIdsToTokens(sStored.tokIds, connector.tokenId2ItsToken);
		} else {
			return null;
		}
	}


	@Override
	public Sentence addSentence(List<Token> sentenceTokens) {
		return addSentence(sentenceTokens, null, null);
	}


	@Override
	public Sentence addSentence(List<Token> sentenceTokens, int start, int end) {
		return addSentence(sentenceTokens, (Integer) start, (Integer) end);
	}


	public Sentence addSentence(List<Token> sentenceTokens, Integer start, Integer end) {
		SentenceStored sentence = new SentenceStored();
		sentence.tokIds = new String[sentenceTokens.size()];
		if (start != null && end != null) {
			sentence.start = start;
			sentence.end = end;
			this.charOffsets = true;
		}
		for (int i = 0; i < sentenceTokens.size(); i++) {
			Token sentenceToken = sentenceTokens.get(i);
			sentence.tokIds[i] = sentenceToken.getID();
			connector.token2ItsSentence.put(sentenceToken, sentence);
		}
		sentences.add(sentence);
		return sentence;
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
			sb.append(CommonAttributes.CHAR_OFFSETS + " " + Boolean.toString(charOffsets));
		}
		sb.append("}: ");
		sb.append(sentences.toString());
		return sb.toString();
	}

}
