package de.tuebingen.uni.sfs.jaxbtest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

public class TokensLayerImpl implements TokensLayerI {

    @XmlElement(name = "token", type = TokenImpl.class)
    List<TokenI> tokens = new ArrayList<TokenI>();

    @Override
    public List<TokenI> getTokens() {
        return tokens;
    }

    @Override
    public String toString() {
        return tokens.toString();
    }
}
