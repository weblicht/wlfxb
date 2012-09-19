/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.xb;

import de.tuebingen.uni.sfs.wlf1.lx.xb.LexiconProfile;
import de.tuebingen.uni.sfs.wlf1.md.xb.MetaData;
import de.tuebingen.uni.sfs.wlf1.tc.xb.TextCorpusProfile;
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
