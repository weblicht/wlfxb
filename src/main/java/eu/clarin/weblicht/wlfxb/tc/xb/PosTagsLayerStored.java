/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.xb;

import eu.clarin.weblicht.wlfxb.tc.api.PosTag;
import eu.clarin.weblicht.wlfxb.tc.api.PosTagsLayer;
import eu.clarin.weblicht.wlfxb.tc.api.Token;
import eu.clarin.weblicht.wlfxb.utils.CommonAttributes;
import eu.clarin.weblicht.wlfxb.utils.WlfUtilities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = PosTagsLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class PosTagsLayerStored extends TextCorpusLayerStoredAbstract implements PosTagsLayer {

    public static final String XML_NAME = "POStags";
    @XmlAttribute(name = CommonAttributes.TAGSET, required = true)
    private String tagset;
    @XmlElement(name = PosTagStored.XML_NAME)
    private List<PosTagStored> tags = new ArrayList<PosTagStored>();
    private TextCorpusLayersConnector connector;

    protected PosTagsLayerStored() {
    }

    protected PosTagsLayerStored(String tagset) {
        this.tagset = tagset;
    }

    protected PosTagsLayerStored(TextCorpusLayersConnector connector) {
        this.connector = connector;
    }

    protected void setLayersConnector(TextCorpusLayersConnector connector) {
        this.connector = connector;
        for (PosTagStored tag : tags) {
            for (String tokRef : tag.tokRefs) {
                connector.token2ItsPosTag.put(connector.tokenId2ItsToken.get(tokRef), tag);
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return tags.isEmpty();
    }

    @Override
    public int size() {
        return tags.size();
    }

    @Override
    public String getTagset() {
        return tagset;
    }

    @Override
    public PosTag getTag(int index) {
        PosTag tag = tags.get(index);
        return tag;
    }

    @Override
    public PosTag getTag(Token token) {
        PosTag tag = connector.token2ItsPosTag.get(token);
        return tag;
    }

    @Override
    public Token[] getTokens(PosTag tag) {
        if (tag instanceof PosTagStored) {
            PosTagStored tagStored = (PosTagStored) tag;
            return WlfUtilities.tokenIdsToTokens(tagStored.tokRefs, connector.tokenId2ItsToken);
        } else {
            return null;
        }
    }

    @Override
    public PosTag addTag(String tagString, Token tagToken) {
        List<Token> tagTokens = Arrays.asList(new Token[]{tagToken});
        return addTag(tagString, tagTokens);
    }

    @Override
    public PosTag addTag(String tagString, List<Token> tagTokens) {
        PosTagStored tag = new PosTagStored();
        //int count = tags.size();
        //tagStored.tagId = PosTagStored.ID_PREFIX + count;
        tag.tagString = tagString;
        tag.tokRefs = new String[tagTokens.size()];
        for (int i = 0; i < tagTokens.size(); i++) {
            Token token = tagTokens.get(i);
            tag.tokRefs[i] = token.getID();
            connector.token2ItsPosTag.put(token, tag);
        }
        tags.add(tag);
        return tag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(XML_NAME);
        sb.append(" ");
        sb.append("{");
        sb.append(tagset);
        sb.append("} :");
        sb.append(tags.toString());
        return sb.toString();
    }
}
