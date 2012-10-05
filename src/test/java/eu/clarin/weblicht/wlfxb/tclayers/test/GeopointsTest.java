/**
 *
 */
package eu.clarin.weblicht.wlfxb.tclayers.test;

import eu.clarin.weblicht.wlfxb.tc.api.GeoLayer;
import eu.clarin.weblicht.wlfxb.tc.api.GeoLongLatFormat;
import eu.clarin.weblicht.wlfxb.tc.xb.GeoLayerStored;
import eu.clarin.weblicht.wlfxb.test.utils.TestUtils;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yana Panchenko
 *
 */
public class GeopointsTest {

    private static final String INPUT = "/data/tc-geo/layer-input.xml";
    private static final String OUTPUT = "/tmp/layer-output.xml";
    public static final double DELTA = 1e-15;

    @Test
    public void testReadAndWriteBack() throws Exception {

        InputStream is = this.getClass().getResourceAsStream(INPUT);
        OutputStream os = new FileOutputStream(OUTPUT);


        GeoLayer layer = TestUtils.read(GeoLayerStored.class, is);
        System.out.println(layer);
        TestUtils.write(layer, os);

        is.close();
        os.close();

        Assert.assertEquals(GeoLongLatFormat.DegDec, layer.getCoordinatesFormat());
        Assert.assertEquals("http://www.geonames.org/", layer.getSource());
        Assert.assertEquals(2, layer.size());
        Assert.assertEquals("39.11417", layer.getPoint(0).getLatitude());

    }
}
