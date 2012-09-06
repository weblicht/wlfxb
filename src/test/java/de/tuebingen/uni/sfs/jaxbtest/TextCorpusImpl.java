package de.tuebingen.uni.sfs.jaxbtest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TextCorpus")
public class TextCorpusImpl implements TextCorpusI {

    @XmlElement(name = "tokens", type = TokensLayerImpl.class)
    TokensLayerI toksLayer;

    @Override
    public TokensLayerI getTokensLayer() {
        return toksLayer;
    }
}
