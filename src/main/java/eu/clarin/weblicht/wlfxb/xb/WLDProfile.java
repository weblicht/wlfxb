/**
 *
 */
package eu.clarin.weblicht.wlfxb.xb;

import eu.clarin.weblicht.wlfxb.lx.xb.LexiconProfile;
import eu.clarin.weblicht.wlfxb.md.xb.MetaData;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusProfile;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = "D-Spin")
@XmlAccessorType(XmlAccessType.NONE)
public class WLDProfile {

    @XmlAttribute
    private String version;
    @XmlElement(name = "MetaData", namespace = "http://www.dspin.de/data/metadata")
    private MetaData metadata;
    @XmlElement(name = "TextCorpus", namespace = "http://www.dspin.de/data/textcorpus")
    private TextCorpusProfile tcProfile;
    @XmlElement(name = "Lexicon", namespace = "http://www.dspin.de/data/lexicon")
    private LexiconProfile lexProfile;

    public String getVersion() {
        return version;
    }

    public MetaData getMetadata() {
        return metadata;
    }

    public TextCorpusProfile getTcProfile() {
        return tcProfile;
    }

    public LexiconProfile getLexProfile() {
        return lexProfile;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(version);
        sb.append("\n");
        sb.append(metadata);
        sb.append("\n");
        if (tcProfile != null) {
            sb.append(tcProfile);
        } else if (lexProfile != null) {
            sb.append(lexProfile);
        }
        return sb.toString();
    }
}
