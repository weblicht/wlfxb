/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.lx.xb;

import de.tuebingen.uni.sfs.wlf1.lx.api.Lemma;
import de.tuebingen.uni.sfs.wlf1.lx.api.PosTag;
import de.tuebingen.uni.sfs.wlf1.lx.api.PosTagsLayer;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = PosTagsLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class PosTagsLayerStored extends LexiconLayerStoredAbstract implements PosTagsLayer {

    public static final String XML_NAME = "POStags";
    @XmlAttribute(name = CommonAttributes.TAGSET, required = true)
    private String tagset;
    @XmlElement(name = PosTagStored.XML_NAME)
    private List<PosTagStored> tags = new ArrayList<PosTagStored>();
    LexiconLayersConnector connector;

    protected PosTagsLayerStored() {
    }

    protected PosTagsLayerStored(String tagset) {
        this.tagset = tagset;
    }

    protected PosTagsLayerStored(LexiconLayersConnector connector) {
        this.connector = connector;
    }

    protected void setLayersConnector(LexiconLayersConnector connector) {
        this.connector = connector;
        for (PosTagStored tag : tags) {
            connect(tag, connector.lemmaId2ItsLemma.get(tag.lemRef));
        }
    }

    private void connect(PosTagStored tag, Lemma lemma) {
        if (!connector.lemma2ItsTags.containsKey(lemma)) {
            connector.lemma2ItsTags.put(lemma, new ArrayList<PosTag>());
        }
        connector.lemma2ItsTags.get(lemma).add(tag);
    }

    @Override
    public boolean isEmpty() {
        if (tags.size() == 0) {
            return true;
        }
        return false;
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
    public PosTag[] getTags(Lemma lemma) {
        if (connector.lemma2ItsTags.containsKey(lemma)) {
            List<PosTag> tagsList = connector.lemma2ItsTags.get(lemma);
            PosTag[] tags = new PosTag[tagsList.size()];
            tags = tagsList.toArray(tags);
            return tags;
        }
        return null;
    }

    @Override
    public Lemma getLemma(PosTag tag) {
        if (tag instanceof PosTagStored) {
            PosTagStored tagStored = (PosTagStored) tag;
            return connector.lemmaId2ItsLemma.get(tagStored.lemRef);
        } else {
            return null;
        }
    }

    @Override
    public PosTag addTag(String tagString, Lemma tagLemma) {
        PosTagStored tag = new PosTagStored();
        tag.tagString = tagString;
        tag.lemRef = tagLemma.getID();
        connect(tag, tagLemma);
        tags.add(tag);
        return tag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(XML_NAME);
        sb.append(" : ");
        sb.append(tags.toString());
        return sb.toString();
    }
}
