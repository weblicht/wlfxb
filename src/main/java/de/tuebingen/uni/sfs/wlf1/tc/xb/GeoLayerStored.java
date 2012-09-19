/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.*;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import de.tuebingen.uni.sfs.wlf1.utils.WlfUtilities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = GeoLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class GeoLayerStored extends TextCorpusLayerStoredAbstract implements GeoLayer {

    public static final String XML_NAME = "geo";
    @XmlElement(name = CommonAttributes.SOURCE, required = true)
    private String source;
    @XmlAttribute(name = "coordFormat", required = true)
    private GeoLongLatFormat coordFormat;
    @XmlAttribute(name = "contitentFormat")
    private GeoContinentFormat contitentFormat;
    @XmlAttribute(name = "countryFormat")
    private GeoCountryFormat countryFormat;
    @XmlAttribute(name = "capitalFormat")
    private GeoCapitalFormat capitalFormat;
    @XmlElement(name = GeoPointStored.XML_NAME)
    private List<GeoPointStored> points = new ArrayList<GeoPointStored>();
    private TextCorpusLayersConnector connector;

    protected GeoLayerStored() {
    }

    protected GeoLayerStored(String source, GeoLongLatFormat coordFormat) {
        this(source, coordFormat, null, null, null);
    }

    protected GeoLayerStored(String source, GeoLongLatFormat coordFormat,
            GeoContinentFormat continentFormat) {
        this(source, coordFormat, continentFormat, null, null);
    }

    protected GeoLayerStored(String source, GeoLongLatFormat coordFormat,
            GeoContinentFormat continentFormat, GeoCountryFormat countryFormat) {
        this(source, coordFormat, continentFormat, countryFormat, null);
    }

    protected GeoLayerStored(String source, GeoLongLatFormat coordFormat,
            GeoContinentFormat continentFormat,
            GeoCapitalFormat capitalFormat) {
        this(source, coordFormat, continentFormat, null, capitalFormat);
    }

    protected GeoLayerStored(String source, GeoLongLatFormat coordFormat,
            GeoCountryFormat countryFormat) {
        this(source, coordFormat, null, countryFormat, null);
    }

    protected GeoLayerStored(String source, GeoLongLatFormat coordFormat,
            GeoCountryFormat countryFormat, GeoCapitalFormat capitalFormat) {
        this(source, coordFormat, null, countryFormat, capitalFormat);
    }

    protected GeoLayerStored(String source, GeoLongLatFormat coordFormat,
            GeoCapitalFormat capitalFormat) {
        this(source, coordFormat, null, null, capitalFormat);
    }

    protected GeoLayerStored(String source, GeoLongLatFormat coordFormat,
            GeoContinentFormat continentFormat, GeoCountryFormat countryFormat,
            GeoCapitalFormat capitalFormat) {
        this.source = source;
        this.coordFormat = coordFormat;
        this.contitentFormat = continentFormat;
        this.countryFormat = countryFormat;
        this.capitalFormat = capitalFormat;
    }

    protected GeoLayerStored(TextCorpusLayersConnector connector) {
        this.connector = connector;
    }

    protected void setLayersConnector(TextCorpusLayersConnector connector) {
        this.connector = connector;
        for (GeoPointStored point : points) {
            for (String tokRef : point.tokRefs) {
                connector.token2ItsGeopoint.put(connector.tokenId2ItsToken.get(tokRef), point);
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return points.isEmpty();
    }

    @Override
    public int size() {
        return points.size();
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public GeoLongLatFormat getCoordinatesFormat() {
        return this.coordFormat;
    }

    @Override
    public GeoContinentFormat getContinentFormat() {
        return this.contitentFormat;
    }

    @Override
    public GeoCountryFormat getCountryFormat() {
        return this.countryFormat;
    }

    @Override
    public GeoCapitalFormat getCapitalFormat() {
        return this.capitalFormat;
    }

    @Override
    public GeoPoint getPoint(int index) {
        return points.get(index);
    }

    @Override
    public GeoPoint getPoint(Token token) {
        GeoPoint point = connector.token2ItsGeopoint.get(token);
        return point;
    }

    @Override
    public Token[] getTokens(GeoPoint point) {
        if (point instanceof GeoPointStored) {
            GeoPointStored p = (GeoPointStored) point;
            return WlfUtilities.tokenIdsToTokens(p.tokRefs, connector.tokenId2ItsToken);
        } else {
            return null;
        }
    }

    @Override
    public GeoPoint addPoint(String longitude, String latitude,
            Double altitude, String continent, String country, String capital,
            Token pointToken) {
        List<Token> tokens = Arrays.asList(new Token[]{pointToken});
        return addPoint(longitude, latitude, altitude, continent, country, capital, tokens);
    }

    @Override
    public GeoPoint addPoint(String longitude, String latitude,
            Double altitude, String continent, String country, String capital,
            List<Token> pointTokens) {

        GeoPointStored p = new GeoPointStored();
        p.longitude = longitude;
        p.latitude = latitude;
        p.altitude = altitude;
        p.continent = continent;
        p.country = country;
        p.capital = capital;
        p.tokRefs = new String[pointTokens.size()];
        for (int i = 0; i < pointTokens.size(); i++) {
            Token token = pointTokens.get(i);
            p.tokRefs[i] = token.getID();
            connector.token2ItsGeopoint.put(token, p);
        }
        points.add(p);
        return p;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(XML_NAME);
        sb.append(" ");
        sb.append("{");
        sb.append(source);
        sb.append(",");
        sb.append(coordFormat);
        if (this.contitentFormat != null) {
            sb.append(",");
            sb.append(contitentFormat.name());
        }
        if (this.countryFormat != null) {
            sb.append(",");
            sb.append(countryFormat.name());
        }
        if (this.capitalFormat != null) {
            sb.append(",");
            sb.append(capitalFormat.name());
        }
        sb.append("} :");
        sb.append(points.toString());
        return sb.toString();
    }
}
