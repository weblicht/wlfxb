/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test;

import eu.clarin.weblicht.wlfxb.tc.api.GeoContinentFormat;
import eu.clarin.weblicht.wlfxb.tc.api.GeoLayer;
import eu.clarin.weblicht.wlfxb.tc.api.GeoLongLatFormat;
import eu.clarin.weblicht.wlfxb.tc.xb.GeoLayerStored;
import eu.clarin.weblicht.wlfxb.test.utils.TestUtils;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 * @author Yana Panchenko and Mohammad Fazleh Elahi
 *
 */
public class GeopointsTest {

    private static final String INPUT = "/data/tc-geo/layer-input.xml";
    private static final String INPUT_ANY_ATTRIBUTES = "/data/tc-geo/layer-inputExtraAtt.xml";
    public static final double DELTA = 1e-15;
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        GeoLayer layer = TestUtils.read(GeoLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals(GeoLongLatFormat.DegDec, layer.getCoordinatesFormat());
        Assert.assertEquals(GeoContinentFormat.name, layer.getContinentFormat());
        Assert.assertEquals("http://www.geonames.org/", layer.getSource());
        Assert.assertEquals(2, layer.size());
        Assert.assertEquals("39.11417", layer.getPoint(0).getLatitude());
        Assert.assertEquals("Europe", layer.getPoint(0).getContinent());
    }

    @Test
    public void testReadAndWriteBack_ExtraAttribute() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT_ANY_ATTRIBUTES);
        OutputStream os = new FileOutputStream(testFolder.newFile("layer-output.xml"));

        GeoLayer layer = TestUtils.read(GeoLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        String anyAttribute = layer.getPoint(0).getExtraAtrributes().keySet().iterator().next();
        Assert.assertEquals("language", anyAttribute);
        Assert.assertEquals("german", layer.getPoint(0).getExtraAtrributes().get(anyAttribute));
    }
}
