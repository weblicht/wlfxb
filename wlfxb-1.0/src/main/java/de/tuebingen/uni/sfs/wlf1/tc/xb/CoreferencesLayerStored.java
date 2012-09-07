/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.Coreference;
import de.tuebingen.uni.sfs.wlf1.tc.api.CoreferencesLayer;
import de.tuebingen.uni.sfs.wlf1.tc.api.Referent;
import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import de.tuebingen.uni.sfs.wlf1.utils.WlfUtilities;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = CoreferencesLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class CoreferencesLayerStored extends TextCorpusLayerStoredAbstract implements CoreferencesLayer {

    public static final String XML_NAME = "coreferences";
    @XmlAttribute(name = CommonAttributes.TAGSET, required = true)
    private String tagset;
    @XmlAttribute(name = "extrefs")
    private String externalReferenceSource;
    @XmlElement(name = ReferentStored.XML_NAME)
    private List<ReferentStored> referents = new ArrayList<ReferentStored>();
    TextCorpusLayersConnector connector;

    protected void setLayersConnector(TextCorpusLayersConnector connector) {
        this.connector = connector;
    }

    protected CoreferencesLayerStored() {
    }

    protected CoreferencesLayerStored(String tagset) {
        this.tagset = tagset;
    }

    protected CoreferencesLayerStored(String tagset, String externalReferenceSource) {
        this(tagset);
        this.externalReferenceSource = externalReferenceSource;
    }

    protected CoreferencesLayerStored(TextCorpusLayersConnector connector) {
        this.connector = connector;
        for (ReferentStored r : referents) {
            for (CoreferenceStored cor : r.coreferences) {
                for (String tokRef : cor.tokRefs) {
                    connector.token2ItsReferent.put(connector.tokenId2ItsToken.get(tokRef), r);
                }
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return referents.isEmpty();
    }

    @Override
    public int size() {
        return referents.size();
    }

    @Override
    public String getTagset() {
        return tagset;
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
    public Referent getReferent(int index) {
        return referents.get(index);
    }

    @Override
    public Referent getReferent(Token token) {
        return this.connector.token2ItsReferent.get(token);
    }

    @Override
    public Token[] getTokens(Coreference coreference) {
        if (coreference instanceof CoreferenceStored) {
            CoreferenceStored cor = (CoreferenceStored) coreference;
            return WlfUtilities.tokenIdsToTokens(cor.tokRefs, connector.tokenId2ItsToken);
        }
        return null;
    }

    @Override
    public Token[] getMinimumTokens(Coreference coreference) {
        if (coreference instanceof CoreferenceStored) {
            CoreferenceStored cor = (CoreferenceStored) coreference;
            return WlfUtilities.tokenIdsToTokens(cor.minTokRefs, connector.tokenId2ItsToken);
        }
        return null;
    }

    @Override
    public Referent addReferent(String referentType, Coreference coreference) {
        ReferentStored referent = new ReferentStored();
        referent.type = referentType;
        if (coreference instanceof CoreferenceStored) {
            referent.coreferences.add((CoreferenceStored) coreference);
        }
        return referent;
    }

    @Override
    public Referent addReferent(String referentType,
            List<Coreference> coreferences) {
        ReferentStored referent = new ReferentStored();
        referent.type = referentType;
        for (Coreference cor : coreferences) {
            if (cor instanceof CoreferenceStored) {
                referent.coreferences.add((CoreferenceStored) cor);
            }
        }
        referents.add(referent);
        return referent;
    }

    @Override
    public Referent addReferent(List<Coreference> coreferences) {
        ReferentStored referent = new ReferentStored();
        for (Coreference cor : coreferences) {
            if (cor instanceof CoreferenceStored) {
                referent.coreferences.add((CoreferenceStored) cor);
            }
        }
        referents.add(referent);
        return referent;
    }

    @Override
    public Referent addReferent(String referentType, Coreference coreference,
            String externalId) {
        ReferentStored r = (ReferentStored) addReferent(referentType, coreference);
        r.externalRef = new ExternalReferenceStored(externalId);
        return r;
    }

    @Override
    public Referent addReferent(String referentType,
            List<Coreference> coreferences, String externalId) {
        ReferentStored r = (ReferentStored) addReferent(referentType, coreferences);
        r.externalRef = new ExternalReferenceStored(externalId);
        return r;
    }

    @Override
    public Referent addReferent(List<Coreference> coreferences, String externalId) {
        ReferentStored r = (ReferentStored) addReferent(coreferences);
        r.externalRef = new ExternalReferenceStored(externalId);
        return r;
    }

    @Override
    public Coreference createCoreference(String type, String semanticRole,
            List<Token> coreferenceTokens, List<Token> coreferenceMinimumTokens) {
        CoreferenceStored cor = new CoreferenceStored();
        cor.type = type;
        cor.semanticRole = semanticRole;
        cor.tokRefs = WlfUtilities.tokens2TokenIds(coreferenceTokens);
        if (coreferenceMinimumTokens != null) {
            cor.minTokRefs = WlfUtilities.tokens2TokenIds(coreferenceMinimumTokens);
        }
        return cor;
    }

    @Override
    public Coreference createCoreference(String type,
            List<Token> coreferenceTokens, List<Token> coreferenceMinimumTokens) {
        return createCoreference(type, null, coreferenceTokens, coreferenceMinimumTokens);
    }

    @Override
    public Coreference createCoreference(List<Token> coreferenceTokens) {
        return createCoreference(null, null, coreferenceTokens, null);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(XML_NAME);
        sb.append(" {");
        sb.append(this.tagset);
        if (this.externalReferenceSource != null) {
            sb.append(" " + this.externalReferenceSource);
        }
        sb.append("}: ");
        sb.append(referents.toString());
        return sb.toString();
    }
}
