/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.tuebingen.uni.sfs.wlf1.tc.api.DiscourseConnective;
import de.tuebingen.uni.sfs.wlf1.tc.api.DiscourseConnectivesLayer;
import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import de.tuebingen.uni.sfs.wlf1.utils.WlfUtilities;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name=DiscourseConnectivesLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class DiscourseConnectivesLayerStored extends TextCorpusLayerStoredAbstract implements DiscourseConnectivesLayer {
	
	public static final String XML_NAME = "discourseconnectives";
	
	@XmlElement(name=DiscourseConnectiveStored.XML_NAME, type=DiscourseConnectiveStored.class)
	List<DiscourseConnectiveStored> connectives = new ArrayList<DiscourseConnectiveStored>();
	@XmlAttribute(name=CommonAttributes.TAGSET)
	String typesTagset;

	TextCorpusLayersConnector connector;
	
	protected DiscourseConnectivesLayerStored() {}
	
	protected DiscourseConnectivesLayerStored(String typesTagset) {
		this.typesTagset = typesTagset;
	}
	
	protected DiscourseConnectivesLayerStored(TextCorpusLayersConnector connector) {
		this.connector = connector;
	}
	
	protected void setLayersConnector(TextCorpusLayersConnector connector) {
		this.connector = connector;
		for (DiscourseConnectiveStored connective : connectives) {
			for (String tokRef : connective.tokRefs) {
				connector.token2ItsDConnective.put(connector.tokenId2ItsToken.get(tokRef), connective);
			}
		}
	}

	@Override
	public boolean isEmpty() {
		if (connectives.size() == 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public int size() {
		return connectives.size();
	}

	@Override
	public String getTypesTagset() {
		return typesTagset;
	}
	

	@Override
	public DiscourseConnective getConnective(int index) {
		return connectives.get(index);
	}


	@Override
	public DiscourseConnective getConnective(Token token) {
		DiscourseConnective connective = connector.token2ItsDConnective.get(token);
		return connective;	
	}


	@Override
	public Token[] getTokens(DiscourseConnective connective) {
		if (connective instanceof DiscourseConnectiveStored) {
			DiscourseConnectiveStored cStored = (DiscourseConnectiveStored) connective;
			return WlfUtilities.tokenIdsToTokens(cStored.tokRefs, connector.tokenId2ItsToken);
		} else {
			return null;
		}
	}


	@Override
	public DiscourseConnective addConnective(List<Token> connectiveTokens) {
		return addConnective(connectiveTokens, null);
	}


	@Override
	public DiscourseConnective addConnective(List<Token> connectiveTokens, String semanticType) {
		DiscourseConnectiveStored connective = new DiscourseConnectiveStored();
		connective.tokRefs = new String[ connectiveTokens.size()];
		connective.type = semanticType;
		for (int i = 0; i <  connectiveTokens.size(); i++) {
			Token cToken =  connectiveTokens.get(i);
			connective.tokRefs[i] = cToken.getID();
			connector.token2ItsDConnective.put(cToken, connective);
		}
		connectives.add(connective);
		return connective;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(XML_NAME);
		sb.append(" {");
		if (typesTagset != null) {
			sb.append(CommonAttributes.TAGSET + " " + typesTagset);
		}
		sb.append("}: ");
		sb.append(connectives.toString());
		return sb.toString();
	}


}
