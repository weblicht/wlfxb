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
import de.tuebingen.uni.sfs.wlf1.tc.api.Dependency;
import de.tuebingen.uni.sfs.wlf1.tc.api.DependencyParse;
import de.tuebingen.uni.sfs.wlf1.tc.api.DependencyParsingLayer;
import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import de.tuebingen.uni.sfs.wlf1.utils.WlfUtilities;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name=DependencyParsingLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class DependencyParsingLayerStored extends TextCorpusLayerStoredAbstract implements DependencyParsingLayer {
	
	public static final String XML_NAME = "depparsing";
	public static final String XML_ATTR_MULTIPLE_GOVERNORS = "multigovs";
	public static final String XML_ATTR_EMPTY_TOKENS = "emptytoks";
	
	@XmlAttribute(name=CommonAttributes.TAGSET)
	private String tagset;
	@XmlAttribute(name=XML_ATTR_MULTIPLE_GOVERNORS, required=true)
	private boolean multigovs;
	@XmlAttribute(name=XML_ATTR_EMPTY_TOKENS, required=true)
	private boolean emptytoks;
	@XmlElement(name=DependencyParseStored.XML_NAME) 
	private List<DependencyParseStored> parses = new ArrayList<DependencyParseStored>();
	
	TextCorpusLayersConnector connector;
	
	protected void setLayersConnector(TextCorpusLayersConnector connector) {
		this.connector = connector;
		for (DependencyParseStored parse : parses) {
			if (parse.emptytoks != null) {
				for (EmptyTokenStored etok : parse.emptytoks) {
					etok.order = this.connector.emptyTokId2EmptyTok.size();
					this.connector.emptyTokId2EmptyTok.put(etok.getID(), etok);
				}
			}
		}
	}
	


	protected DependencyParsingLayerStored() {}
	
	protected DependencyParsingLayerStored(String tagset, Boolean hasMultipleGovernors, Boolean hasEmptyTokens) {
		this.tagset = tagset;
		this.multigovs = hasMultipleGovernors;
		this.emptytoks = hasEmptyTokens;
	}
	
	protected DependencyParsingLayerStored(Boolean hasMultipleGovernors, Boolean hasEmptyTokens) {
		this.multigovs = hasMultipleGovernors;
		this.emptytoks = hasEmptyTokens;
	}
	
	protected DependencyParsingLayerStored(TextCorpusLayersConnector connector) {
		this.connector = connector;
	}
	
	@Override
	public boolean isEmpty() {
		if (parses.size() == 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public int size() {
		return parses.size();
	}
	
	@Override
	public String getTagset() {
		return tagset;
	}
	
	@Override
	public boolean hasEmptyTokens() {
		return this.emptytoks;
	}
	
	@Override
	public boolean hasMultipleGovernors() {
		return this.multigovs;
	}
	

	@Override
	public DependencyParse getParse(int index) {
		DependencyParseStored parse = parses.get(index);
		return parse;	
	}
	
	
	@Override
	public Token[] getGovernorTokens(Dependency dependency) {
		if (dependency instanceof DependencyStored) {
			DependencyStored dep = (DependencyStored) dependency;
			if (dep.govIds != null) {
				return getTokens(dep.govIds);
			}
		} 
		return null;
	}
	
	@Override
	public Token[] getDependentTokens(Dependency dependency) {
		if (dependency instanceof DependencyStored) {
			DependencyStored dep = (DependencyStored) dependency;
			return getTokens(dep.depIds);
		} else {
			return null;
		}
	}
	
	private Token[] getTokens(String[] tokRefs) {
		Token[] tokens = new Token[tokRefs.length];
		for (int i = 0; i < tokens.length; i++) {
			if (connector.emptyTokId2EmptyTok.containsKey(tokRefs[i])) {
				tokens[i] = connector.emptyTokId2EmptyTok.get(tokRefs[i]);
			} else {
				tokens[i] = connector.tokenId2ItsToken.get(tokRefs[i]);
			}
		}
		return tokens;
	}
	
	public Token createEmptyToken(String tokenString) {
		if (tokenString == null) {
			tokenString = "";
		}
		EmptyTokenStored token = new EmptyTokenStored();
		token.tokenString = tokenString;
		int tokenCount = connector.emptyTokId2EmptyTok.size();
		token.order = tokenCount;
		token.id = EmptyTokenStored.ID_PREFIX + tokenCount;
		//add to connector map
		connector.emptyTokId2EmptyTok.put(token.id, token);
		return token;
	}
	

	public Dependency createDependency(String function, List<Token> dependent, List<Token> governor) {
		DependencyStored dep = new DependencyStored();
		dep.function = function;
		dep.depIds = WlfUtilities.tokens2TokenIds(dependent);
		dep.govIds = WlfUtilities.tokens2TokenIds(governor);;
		return dep;
	}
	
	public Dependency createDependency(String function, List<Token> dependent) {
		DependencyStored dep = new DependencyStored();
		dep.function = function;
		dep.depIds = WlfUtilities.tokens2TokenIds(dependent);
		return dep;
	}

	public Dependency createDependency(List<Token> dependent, List<Token> governor) {
		return createDependency(null, dependent, governor);
	}
	
	public Dependency createDependency(List<Token> dependent) {
		DependencyStored dep = new DependencyStored();
		dep.depIds = WlfUtilities.tokens2TokenIds(dependent);
		return dep;
	}
	
	public Dependency createDependency(String function, Token dependent, Token governor) {
		DependencyStored dep = new DependencyStored();
		dep.function = function;
		dep.depIds = new String[]{dependent.getID()};
		dep.govIds = new String[]{governor.getID()};
		return dep;
	}
	
	public Dependency createDependency(String function, Token dependent) {
		DependencyStored dep = new DependencyStored();
		dep.function = function;
		dep.depIds = new String[]{dependent.getID()};
		return dep;
	}

	public Dependency createDependency(Token dependent, Token governor) {
		return createDependency(null, dependent, governor);
	}
	
	public Dependency createDependency(Token dependent) {
		DependencyStored dep = new DependencyStored();
		dep.depIds = new String[]{dependent.getID()};
		return dep;
	}

	public DependencyParse addParse(List<Dependency> dependencies) {
		
		DependencyParseStored parse = new DependencyParseStored();
		List<DependencyStored> deps = new ArrayList<DependencyStored>(dependencies.size());
		List<EmptyTokenStored> emptyTokens = new ArrayList<EmptyTokenStored>();
		for (Dependency dep : dependencies) {
			if (dep instanceof DependencyStored) {
				DependencyStored depS = (DependencyStored) dep;
				deps.add(depS);
				for (String ref : depS.depIds) {
					if (connector.emptyTokId2EmptyTok.containsKey(ref) && !emptyTokens.contains(ref)) {
						emptyTokens.add(connector.emptyTokId2EmptyTok.get(ref));
					} 
				}
				if (depS.govIds != null) {
					for (String ref : depS.govIds) {
						if (connector.emptyTokId2EmptyTok.containsKey(ref) && !emptyTokens.contains(ref)) {
							emptyTokens.add(connector.emptyTokId2EmptyTok.get(ref));
						} 
					}
				}
			}
		}
		if (!emptyTokens.isEmpty()) {
			parse.emptytoks = emptyTokens;
		}
		parse.dependencies = deps;
		parses.add(parse);
		return parse;
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(XML_NAME);
		sb.append(" {");
		sb.append(CommonAttributes.TAGSET + " " + getTagset() + " "); 
		sb.append(XML_ATTR_MULTIPLE_GOVERNORS + " " + this.multigovs + " ");
		sb.append(XML_ATTR_EMPTY_TOKENS + " " + this.emptytoks);
		sb.append("}: ");
		sb.append(parses.toString());
		return sb.toString();
	}

}
