/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.CorrectionOperation;
import de.tuebingen.uni.sfs.wlf1.tc.api.OrthCorrection;
import de.tuebingen.uni.sfs.wlf1.tc.api.OrthographyLayer;
import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.utils.WlfUtilities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = OrthographyLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class OrthographyLayerStored extends TextCorpusLayerStoredAbstract implements OrthographyLayer {

    public static final String XML_NAME = "orthography";
    @XmlElement(name = OrthCorrectionStored.XML_NAME)
    private List<OrthCorrectionStored> corrections = new ArrayList<OrthCorrectionStored>();
    private TextCorpusLayersConnector connector;

    protected OrthographyLayerStored() {
    }

    protected OrthographyLayerStored(TextCorpusLayersConnector connector) {
        this.connector = connector;
    }

    protected void setLayersConnector(TextCorpusLayersConnector connector) {
        this.connector = connector;
        for (int i = 0; i < corrections.size(); i++) {
            OrthCorrectionStored corr = corrections.get(i);
            for (String tokRef : corr.tokRefs) {
                connector.token2ItsCorrection.put(connector.tokenId2ItsToken.get(tokRef), corr);
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return corrections.isEmpty();
    }

    @Override
    public int size() {
        return corrections.size();
    }

    @Override
    public OrthCorrection getCorrection(int index) {
        OrthCorrection corr = corrections.get(index);
        return corr;
    }

    @Override
    public OrthCorrection getCorrection(Token token) {
        OrthCorrection corr = connector.token2ItsCorrection.get(token);
        return corr;
    }

    @Override
    public Token[] getTokens(OrthCorrection correction) {
        if (correction instanceof OrthCorrectionStored) {
            OrthCorrectionStored corrStored = (OrthCorrectionStored) correction;
            return WlfUtilities.tokenIdsToTokens(corrStored.tokRefs, connector.tokenId2ItsToken);
        } else {
            return null;
        }
    }

    @Override
    public OrthCorrection addCorrection(String correctionString, Token correctedToken, CorrectionOperation operation) {
        List<Token> corrTokens = Arrays.asList(new Token[]{correctedToken});
        return addCorrection(correctionString, corrTokens, operation);
    }

    @Override
    public OrthCorrection addCorrection(String correctionString, List<Token> correctedTokens, CorrectionOperation operation) {
        OrthCorrectionStored corr = new OrthCorrectionStored();
        corr.corrString = correctionString;
        corr.tokRefs = new String[correctedTokens.size()];
        corr.operation = operation;
        for (int i = 0; i < correctedTokens.size(); i++) {
            Token corrToken = correctedTokens.get(i);
            corr.tokRefs[i] = corrToken.getID();
            connector.token2ItsCorrection.put(corrToken, corr);
        }
        corrections.add(corr);
        return corr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(XML_NAME);
        sb.append(" : ");
        sb.append(corrections.toString());
        return sb.toString();
    }
}
