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

import de.tuebingen.uni.sfs.wlf1.tc.api.Reference;
import de.tuebingen.uni.sfs.wlf1.tc.api.ReferencesLayer;
import de.tuebingen.uni.sfs.wlf1.tc.api.ReferencedEntity;
import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.utils.WlfUtilities;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name=ReferencesLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class ReferencesLayerStored extends TextCorpusLayerStoredAbstract implements ReferencesLayer {
	
	public static final String XML_NAME = "references";
	
	@XmlAttribute(name="typetagset")
	String typetagset;
	@XmlAttribute(name="reltagset")
	String reltagset;
	@XmlAttribute(name="extrefs")
	String externalReferenceSource;
	@XmlElement(name=ReferencedEntityStored.XML_NAME)
	private List<ReferencedEntityStored> referentcedEntities = new ArrayList<ReferencedEntityStored>();
	
	TextCorpusLayersConnector connector;
	
	protected void setLayersConnector(TextCorpusLayersConnector connector) {
		this.connector = connector;
		for (ReferencedEntityStored r : referentcedEntities) {
			for (ReferenceStored ref : r.references) {
				connector.referenceId2ItsReference.put(ref.id, ref);
				for (String tokRef : ref.tokRefs) {
					//connector.token2ItsReferent.put(connector.tokenId2ItsToken.get(tokRef), r);
					Token tok = connector.tokenId2ItsToken.get(tokRef);
					if (!connector.token2ItsReferent.containsKey(tok)) {
						connector.token2ItsReferent.put(tok, new ArrayList<ReferencedEntity>());
					}
					connector.token2ItsReferent.get(tok).add(r);
				}
			}
		}
	}
	
	protected ReferencesLayerStored() {}
	
	protected ReferencesLayerStored(TextCorpusLayersConnector connector) {
		this.connector = connector;
	}
	
	@Override
	public boolean isEmpty() {
		return referentcedEntities.isEmpty();
	}
	
	@Override
	public int size() {
		return referentcedEntities.size();
	}
	
	
	@Override
	public String getTypetagset() {
		return typetagset;
	}
	
	@Override
	public String getReltagset() {
		return reltagset;
	}


	@Override
	public boolean hasExternalReferences() {
		return (this.externalReferenceSource != null);
	}


	@Override
	public String getExternalReferenceSource() {
		return this.externalReferenceSource;
	}


	@Override
	public ReferencedEntity getReferencedEntity(int index) {
		return referentcedEntities.get(index);
	}

	
	@Override
	public List<ReferencedEntity> getReferencedEntities(Token token) {
		return this.connector.token2ItsReferent.get(token);
	}

	@Override
	public Token[] getTokens(Reference reference) {
		if (reference instanceof ReferenceStored) {
			ReferenceStored cor = (ReferenceStored) reference;
			return WlfUtilities.tokenIdsToTokens(cor.tokRefs, connector.tokenId2ItsToken);
		}
		return null;
	}
	
	@Override
	public Token[] getMinimumTokens(Reference reference) {
		if (reference instanceof ReferenceStored) {
			ReferenceStored cor = (ReferenceStored) reference;
			return WlfUtilities.tokenIdsToTokens(cor.minTokRefs, connector.tokenId2ItsToken);
		}
		return null;
	}


	@Override
	public Reference[] getTarget(Reference reference) {
		if (reference instanceof ReferenceStored) {
			ReferenceStored ref = (ReferenceStored) reference;
			Reference[] targetRefs = new Reference[ref.targetIds.length];
			for (int i = 0; i < targetRefs.length; i++) {
				targetRefs[i] = connector.referenceId2ItsReference.get(ref.targetIds[i]);
			}
			return targetRefs;
		}
		return null;
	}

	@Override
	public ReferencedEntity addReferent(List<Reference> references) {
		return addReferent(references, null);
	}
	
	@Override
	public ReferencedEntity addReferent(List<Reference> references, String externalId) {
		ReferencedEntityStored referent = new ReferencedEntityStored();
		if (externalId != null) {
			referent.externalRef = new ExternalReferenceStored(externalId);
		}
		for (Reference ref : references) {
			addReference(referent, ref);
		}
		referentcedEntities.add(referent);
		return referent;
	}
	
	
	private void addReference(ReferencedEntityStored referent, Reference reference) {
		if (reference instanceof ReferenceStored) {
			ReferenceStored ref = (ReferenceStored) reference;
			referent.references.add(ref);
			for (String tokRef : ref.tokRefs) {
				Token tok = connector.tokenId2ItsToken.get(tokRef);
				if (!connector.token2ItsReferent.containsKey(tok)) {
					connector.token2ItsReferent.put(tok, new ArrayList<ReferencedEntity>());
				}
				connector.token2ItsReferent.get(tok).add(referent);
			}
		}
	}
	
	@Override
	public Reference createReference(List<Token> referenceTokens) {
		return createReference(null, referenceTokens, null);
	}
	
	@Override
	public Reference createReference(List<Token> referenceTokens, List<Token> minReferenceTokens) {
		return createReference(null, referenceTokens, minReferenceTokens);
	}

	@Override
	public Reference createReference(String type, List<Token> referenceTokens,
			List<Token> minReferenceTokens) {
		ReferenceStored reference = new ReferenceStored();
		reference.id = ReferenceStored.ID_PREFIX + connector.referenceId2ItsReference.size();
		connector.referenceId2ItsReference.put(reference.id, reference);
		reference.type = type;
		reference.tokRefs = WlfUtilities.tokens2TokenIds(referenceTokens);
		if (minReferenceTokens != null) {
			reference.minTokRefs = WlfUtilities.tokens2TokenIds(minReferenceTokens);
		}
		return reference;
	}

	@Override
	public void addRelation(Reference reference, String relation, Reference ... target) {
		if (reference instanceof ReferenceStored) {
			ReferenceStored ref = (ReferenceStored) reference;
			ref.relation = relation;
			ref.targetIds = new String[target.length];
			for (int i = 0; i < target.length; i++) {
				if (target[i] instanceof ReferenceStored) {
					ref.targetIds[i] = ((ReferenceStored) target[i]).id;
				}
			}
		}
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(XML_NAME);
		sb.append(" {");
		if (typetagset != null) {
			sb.append(this.typetagset);
		}
		if (reltagset != null) {
			sb.append( " " + this.reltagset);
		}
		if (this.externalReferenceSource != null) {
			sb.append(" " + this.externalReferenceSource);
		}
		sb.append("}: ");
		sb.append(referentcedEntities.toString());
		return sb.toString();
	}

	

}
