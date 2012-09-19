/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.api;

/**
 * @author Yana Panchenko
 *
 */
public enum GeoLongLatFormat {

    /**
     * Coordinate containing only degrees (positive or negative real number).
     * Example: 40.446195, -79.948862
     */
    DegDec,
    /**
     * Coordinate containing degrees (integer) and minutes (real number).
     * Example: 40°26.7717, -79°56.93172
     */
    MinDec,
    /**
     * Coordinate containing degrees (integer), minutes (integer), and seconds
     * (integer, or real number). Example: Latitude 40:26:46N, Longitude
     * 79:56:55W
     */
    DMS;
}
