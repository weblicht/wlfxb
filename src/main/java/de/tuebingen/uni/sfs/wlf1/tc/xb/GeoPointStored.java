/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.GeoPoint;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import java.util.Arrays;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = GeoPointStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class GeoPointStored implements GeoPoint {

    public static final String XML_NAME = "gpoint";
    @XmlAttribute(name = CommonAttributes.ID)
    private String id;
    @XmlAttribute(name = "lon", required = true)
    protected String longitude;
    @XmlAttribute(name = "lat", required = true)
    protected String latitude;
    @XmlAttribute(name = "alt")
    protected Double altitude;
    @XmlAttribute(name = "continent")
    protected String continent;
    @XmlAttribute(name = "country")
    protected String country;
    @XmlAttribute(name = "capital")
    protected String capital;
    @XmlAttribute(name = CommonAttributes.TOKEN_SEQUENCE_REFERENCE, required = true)
    protected String[] tokRefs;

    @Override
    public String getLongitude() {
        return longitude;
    }

    @Override
    public String getLatitude() {
        return latitude;
    }

    @Override
    public Double getAltitude() {
        return altitude;
    }

    @Override
    public String getContinent() {
        return continent;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public String getCapital() {
        return capital;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (id != null) {
            sb.append(id);
            sb.append(" -> ");
        }
        sb.append(" ").append(longitude);
        sb.append(" ").append(latitude);
        if (altitude != null) {
            sb.append(" ").append(altitude);
        }
        if (continent != null) {
            sb.append(" ").append(continent);
        }
        if (country != null) {
            sb.append(" ").append(country);
        }
        if (capital != null) {
            sb.append(" ").append(capital);
        }

        sb.append(Arrays.toString(tokRefs));
        return sb.toString();
    }
}
