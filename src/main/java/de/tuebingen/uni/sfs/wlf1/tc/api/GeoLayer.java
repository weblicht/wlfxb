/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.tc.api;

import java.util.List;

/**
 * @author Yana Panchenko
 *
 */
public interface GeoLayer {
	
	public int size();
	public GeoLongLatFormat getCoordinatesFormat();
	public GeoContinentFormat getContinentFormat();
	public GeoCountryFormat getCountryFormat();
	public GeoCapitalFormat getCapitalFormat();
	public String getSource();
	public GeoPoint getPoint(int index);
	public GeoPoint getPoint(Token token);
	public Token[] getTokens(GeoPoint point);
	public GeoPoint addPoint(String longitude, String latitude, Double altitude, 
			String continent, String country, String capital,
			Token entityToken);
	public GeoPoint addPoint(String longitude, String latitude, Double altitude, 
			String continent, String country, String capital,
			List<Token> entityTokens);
}
