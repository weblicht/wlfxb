/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.tuebingen.uni.sfs.wlf1.tc.api.OrthographyLayer;
import de.tuebingen.uni.sfs.wlf1.tc.api.TextSpan;
import de.tuebingen.uni.sfs.wlf1.tc.api.TextSpanType;
import de.tuebingen.uni.sfs.wlf1.tc.api.TextStructureLayer;
import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.utils.WlfUtilities;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name=TextStructureLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class TextStructureLayerStored extends TextCorpusLayerStoredAbstract implements TextStructureLayer {
	
	public static final String XML_NAME = "textstructure";
	
	@XmlElement(name=TextSpanStored.XML_NAME)
	private List<TextSpanStored> tspans = new ArrayList<TextSpanStored>();
	
	TextCorpusLayersConnector connector;
	
	protected void setLayersConnector(TextCorpusLayersConnector connector) {
		this.connector = connector;
		for (int i = 0; i < tspans.size(); i++) {
			TextSpanStored tspan = tspans.get(i);
			TextSpanType type = tspan.getType();
			if (tspan.startToken != null && tspan.endToken != null) {
				int start = connector.tokenId2ItsToken.get(tspan.startToken).getOrder();
				int end = connector.tokenId2ItsToken.get(tspan.endToken).getOrder() + 1;
				for (int j = start; j < end; j++) {
					connector.token2ItsTextSpans.get(type).put(connector.tokens.get(j), tspan);
				}
			}
		}
	}
	
	protected TextStructureLayerStored() {}

	
	protected TextStructureLayerStored(TextCorpusLayersConnector connector) {
		this.connector = connector;
	}
	
	@Override
	public boolean isEmpty() {
		return tspans.isEmpty();
	}
	
	@Override
	public int size() {
		return tspans.size();
	}
	
	
	@Override
	public TextSpan getSpan(int index) {
		return tspans.get(index);
	}
	
	@Override
	public List<TextSpan> getSpans(Token token) {
		List<TextSpan> spans = new ArrayList<TextSpan>();
		for (TextSpanType type : connector.token2ItsTextSpans.keySet()) {
			Map<Token,TextSpan> tokToSpan = connector.token2ItsTextSpans.get(type);
			TextSpan span = tokToSpan.get(token);
			if (span != null) {
				spans.add(span);
			}
		}
		return spans;
	}


	@Override
	public TextSpan getSpan(Token token, TextSpanType type) {
		Map<Token,TextSpan> tokToSpan = connector.token2ItsTextSpans.get(type);
		TextSpan span = tokToSpan.get(token);
		return span;
	}


	@Override
	public List<TextSpan> getSpans(TextSpanType type) {
		List<TextSpan> spans = new ArrayList<TextSpan>();
		Map<Token,TextSpan> tokToSpan = connector.token2ItsTextSpans.get(type);
		spans.addAll(tokToSpan.values());
		return spans;
	}


	@Override
	public Token[] getTokens(TextSpan span) {
		if (span instanceof TextSpanStored) {
			TextSpanStored tspan = (TextSpanStored) span;
			if (tspan.startToken != null && tspan.startToken != null) {
				int start = connector.tokenId2ItsToken.get(tspan.startToken).getOrder();
				int end = connector.tokenId2ItsToken.get(tspan.endToken).getOrder() + 1;
				Token[] tokens = new Token[end - start];
				for (int i = 0, j = start; j < end; i++, j++) {
					tokens[i] = connector.tokens.get(j);
				}
				return tokens;
			} else {
				return new Token[0];
			}
		} else {
			throw new UnsupportedOperationException(WlfUtilities.layersErrorMessage(TextSpan.class, OrthographyLayer.class));
		}
		
	}


	@Override
	public TextSpan addSpan(Token spanStart, Token spanEnd, TextSpanType type) {
		TextSpanStored tspan = new TextSpanStored();
		tspan.type = type;
		if (spanStart != null && spanEnd != null) {
			tspan.startToken = spanStart.getID();
			tspan.endToken = spanEnd.getID();
			int start = connector.tokenId2ItsToken.get(tspan.startToken).getOrder();
			int end = connector.tokenId2ItsToken.get(tspan.endToken).getOrder() + 1;
			for (int j = start; j < end; j++) {
				connector.token2ItsTextSpans.get(type).put(connector.tokens.get(j), tspan);
			}
		}
		tspans.add(tspan);
		return tspan;
	}
	
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(XML_NAME);
		sb.append(": ");
		sb.append(tspans.toString());
		return sb.toString();
	}

	





}
