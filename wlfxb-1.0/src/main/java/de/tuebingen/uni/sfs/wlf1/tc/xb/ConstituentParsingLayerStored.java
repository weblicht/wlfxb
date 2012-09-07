/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.Constituent;
import de.tuebingen.uni.sfs.wlf1.tc.api.ConstituentParse;
import de.tuebingen.uni.sfs.wlf1.tc.api.ConstituentParsingLayer;
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
@XmlRootElement(name = ConstituentParsingLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class ConstituentParsingLayerStored extends TextCorpusLayerStoredAbstract implements ConstituentParsingLayer {

    public static final String XML_NAME = "parsing";
    @XmlAttribute(name = CommonAttributes.TAGSET)
    private String tagset;
    @XmlElement(name = ConstituentParseStored.XML_NAME)
    private List<ConstituentParseStored> parses = new ArrayList<ConstituentParseStored>();
    TextCorpusLayersConnector connector;

    protected void setLayersConnector(TextCorpusLayersConnector connector) {
        this.connector = connector;
    }

    protected ConstituentParsingLayerStored() {
    }

    protected ConstituentParsingLayerStored(String tagset) {
        this.tagset = tagset;
    }

    protected ConstituentParsingLayerStored(TextCorpusLayersConnector connector) {
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
    public ConstituentParse getParse(int index) {
        ConstituentParseStored parse = parses.get(index);
        return parse;
    }

    @Override
    public Constituent getParseRoot(int index) {
        ConstituentStored root = parses.get(index).constituentParseRoot;
        return root;
    }

    @Override
    public Token[] getTokens(ConstituentParse parse) {
        if (parse instanceof ConstituentParseStored) {
            ConstituentParseStored pStored = (ConstituentParseStored) parse;
            return getTokens(pStored.constituentParseRoot);
        } else {
            return null;
        }
    }

    @Override
    public Token[] getTokens(Constituent constituent) {
        if (constituent instanceof ConstituentStored) {
            ConstituentStored cStored = (ConstituentStored) constituent;
            //if (cStored.isTerminal()) {
            //	return WlfUtilities.tokenIdsToTokens(cStored.tokRefs, connector.tokenId2ItsToken);
            //}
            List<Token> terminalsTokens = new ArrayList<Token>();
            collectTerminalsTokens(terminalsTokens, cStored);
            Token[] tokens = new Token[terminalsTokens.size()];
            return terminalsTokens.toArray(tokens);
        } else {
            return null;
        }
    }

    private void collectTerminalsTokens(List<Token> terminalsTokens,
            ConstituentStored c) {
        if (c.isTerminal()) {
            for (int i = 0; i < c.tokRefs.length; i++) {
                terminalsTokens.add(connector.tokenId2ItsToken.get(c.tokRefs[i]));
            }
        } else {
            for (ConstituentStored child : c.children) {
                collectTerminalsTokens(terminalsTokens, child);
            }
        }
    }

    @Override
    public Constituent createConstituent(String category, List<Constituent> children) {
        ConstituentStored c = new ConstituentStored();
        for (Constituent child : children) {
            c.category = category;
            if (child instanceof ConstituentStored) {
                c.children.add((ConstituentStored) child);
            } else {
                return null;
            }
        }
        return c;
    }

    @Override
    public Constituent createConstituent(String category) {
        ConstituentStored c = new ConstituentStored();
        c.category = category;
        return c;
    }

    @Override
    public Constituent addChild(Constituent parent, Constituent child) {
        if (parent instanceof ConstituentStored
                && child instanceof ConstituentStored) {
            ((ConstituentStored) parent).children.add((ConstituentStored) child);
        } else {
            return null;
        }
        return parent;
    }

    @Override
    public Constituent createTerminalConstituent(String category, List<Token> tokens) {
        ConstituentStored c = new ConstituentStored();
        String[] tokRefs = WlfUtilities.tokens2TokenIds(tokens);
        c.category = category;
        c.tokRefs = tokRefs;
        return c;
    }

    @Override
    public Constituent createTerminalConstituent(String category, Token token) {
        ConstituentStored c = new ConstituentStored();
        String[] tokRefs = new String[]{token.getID()};
        c.category = category;
        c.tokRefs = tokRefs;
        return c;
    }

    @Override
    public ConstituentParse addParse(Constituent root) {

        ConstituentParseStored parse = new ConstituentParseStored();
        if (root instanceof ConstituentStored) {
            parse.constituentParseRoot = (ConstituentStored) root;
            parses.add(parse);
            return parse;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(XML_NAME);
        sb.append(" {");
        sb.append(CommonAttributes.TAGSET + " " + getTagset());
        sb.append("}: ");
        sb.append(parses.toString());
        return sb.toString();
    }
}
