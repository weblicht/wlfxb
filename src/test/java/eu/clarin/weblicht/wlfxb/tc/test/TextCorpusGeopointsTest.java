/**
 *
 */
package eu.clarin.weblicht.wlfxb.tc.test;

import eu.clarin.weblicht.wlfxb.io.TextCorpusStreamed;
import eu.clarin.weblicht.wlfxb.tc.api.GeoContinentFormat;
import eu.clarin.weblicht.wlfxb.tc.api.GeoLayer;
import eu.clarin.weblicht.wlfxb.tc.api.GeoLongLatFormat;
import eu.clarin.weblicht.wlfxb.tc.api.TextCorpus;
import eu.clarin.weblicht.wlfxb.tc.api.Token;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import java.util.EnumSet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import java.io.File;

/**
 * @author Yana Panchenko
 *
 */
public class TextCorpusGeopointsTest extends AbstractTextCorpusTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT_FILE_WITHOUT_LAYER = "/data/tc-geo/tcf-before.xml";
    private static final String INPUT_FILE_WITH_LAYER = "/data/tc-geo/tcf-after.xml";
    private static final String EXPECTED_OUTPUT_FILE = "/data/tc-geo/output-expected.xml";
    private static final String OUTPUT_FILE = "output.xml";
    private static final EnumSet<TextCorpusLayerTag> layersToReadBeforeGeopointsRecognition =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.NAMED_ENTITIES);
    private static final EnumSet<TextCorpusLayerTag> layersToReadAfterGeopointsRecognition =
            EnumSet.of(TextCorpusLayerTag.TOKENS, TextCorpusLayerTag.NAMED_ENTITIES, TextCorpusLayerTag.GEO);
    public static final String sampleToken = "Peter";
    public static final String sampleLon = "-94.62746";
    public static final String sampleLat = "39.11417";
    public static final double sampleAlt = 233;

    @Test
    public void testRead() throws Exception {
        TextCorpus tc = read(INPUT_FILE_WITH_LAYER, layersToReadAfterGeopointsRecognition);
        GeoLayer layer = tc.getGeoLayer();
        Assert.assertEquals(1, layer.size());
        Assert.assertEquals("-94.62746", layer.getPoint(0).getLongitude());
        Assert.assertEquals(tc.getTokensLayer().getToken(0), layer.getTokens(layer.getPoint(0))[0]);
    }

    @Test
    public void testReadWrite() throws Exception {
        String outfile = testFolder.getRoot() + File.separator + OUTPUT_FILE;
        TextCorpusStreamed tc = open(INPUT_FILE_WITHOUT_LAYER, outfile, layersToReadBeforeGeopointsRecognition);
        System.out.println(tc);
        // create geo layer, it's empty at first
        GeoLayer geo = tc.createGeoLayer("http://www.geonames.org/", GeoLongLatFormat.DegDec, GeoContinentFormat.name);
        for (int i = 0; i < tc.getTokensLayer().size(); i++) {
            Token token = tc.getTokensLayer().getToken(i);
            if (token.getString().equals(sampleToken)) {
                // create and add geo point to the geo layer
                geo.addPoint(sampleLon, sampleLat, sampleAlt, "Europe", null, null, token);
            }
        }
        // IMPORTANT close the streams!!!
        tc.close();
        System.out.println(tc);
        // compare output xml with expected xml
        assertEqualXml(EXPECTED_OUTPUT_FILE, outfile);
    }
}
