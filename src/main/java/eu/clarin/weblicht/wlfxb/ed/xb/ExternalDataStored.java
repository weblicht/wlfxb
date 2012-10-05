/**
 *
 */
package eu.clarin.weblicht.wlfxb.ed.xb;

import eu.clarin.weblicht.wlfxb.ed.api.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = ExternalDataStored.XML_NAME, namespace = ExternalDataStored.XML_NAMESPACE)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {
    "speechSignalLayer",
    "tokenSegmentationLayer",
    "phoneticSegmentationLayer",
    "canonicalSegmentationLayer"
})
public class ExternalDataStored implements ExternalData {

    public static final String XML_NAME = "ExternalData";
    public static final String XML_NAMESPACE = "http://www.dspin.de/data/extdata";
    private ExternalDataLayerStored[] layersInOrder;

    public ExternalDataStored() {
        layersInOrder = new ExternalDataLayerStored[ExternalDataLayerTag.orderedLayerTags().size()];
    }

    @Override
    public List<ExternalDataLayer> getLayers() {
        List<ExternalDataLayer> allLayers = new ArrayList<ExternalDataLayer>(this.layersInOrder.length);
        for (ExternalDataLayer layer : this.layersInOrder) {
            if (layer != null) {
                allLayers.add(layer);
            }
        }
        return allLayers;
    }

    @Override
    public SpeechSignalLayer createSpeechSignalLayer(String mimeType) {
        SpeechSignalLayer layer = initializeLayer(SpeechSignalLayerStored.class, mimeType);
        return layer;
    }

    @Override
    public SpeechSignalLayer createSpeechSignalLayer(String mimeType, int numberOfChannels) {
        SpeechSignalLayer layer = initializeLayer(SpeechSignalLayerStored.class,
                mimeType, new Integer(numberOfChannels));
        return layer;
    }

    @Override
    public TokenSegmentationLayer createTokenSegmentationLayer(String mimeType) {
        TokenSegmentationLayer layer = initializeLayer(TokenSegmentationLayerStored.class, mimeType);
        return layer;
    }

    @Override
    public PhoneticSegmentationLayer createPhoneticSegmentationLayer(String mimeType) {
        PhoneticSegmentationLayer layer = initializeLayer(PhoneticSegmentationLayerStored.class, mimeType);
        return layer;
    }

    @Override
    public CanonicalSegmentationLayer createCanonicalSegmentationLayer(String mimeType) {
        CanonicalSegmentationLayer layer = initializeLayer(CanonicalSegmentationLayerStored.class, mimeType);
        return layer;
    }

    @SuppressWarnings("unchecked")
    private <T extends ExternalDataLayerStored> T initializeLayer(Class<T> layerClass, Object... params) {

        Class<?>[] paramsClass = null;
        if (params != null) {
            paramsClass = new Class<?>[params.length];
            for (int i = 0; i < params.length; i++) {
                paramsClass[i] = params[i].getClass();
            }
        }
        ExternalDataLayerTag layerTag = ExternalDataLayerTag.getFromClass(layerClass);
        try {
            Constructor<?> ct;
            T instance;
            if (params == null) {
                ct = layerClass.getDeclaredConstructor();
                instance = (T) ct.newInstance();
            } else {
                ct = layerClass.getDeclaredConstructor(paramsClass);
                instance = (T) ct.newInstance(params);
            }
            layersInOrder[layerTag.ordinal()] = instance;
            //instance.setLayersConnector(connector);
        } catch (Exception e) {
            //  e.printStackTrace();
            Logger.getLogger(ExternalDataStored.class.getName()).log(Level.SEVERE, null, e);
        }
        return (T) layersInOrder[layerTag.ordinal()];
    }

    @XmlElement(name = SpeechSignalLayerStored.XML_NAME)
    protected void setSpeechSignalLayer(SpeechSignalLayerStored layer) {
        System.out.println(layer);
        layersInOrder[ExternalDataLayerTag.SPEECH_SIGNAL.ordinal()] = layer;
    }

    @Override
    public SpeechSignalLayerStored getSpeechSignalLayer() {
        return ((SpeechSignalLayerStored) layersInOrder[ExternalDataLayerTag.SPEECH_SIGNAL.ordinal()]);
    }

    @XmlElement(name = TokenSegmentationLayerStored.XML_NAME)
    protected void setTokenSegmentationLayer(TokenSegmentationLayerStored layer) {
        System.out.println(layer);
        layersInOrder[ExternalDataLayerTag.TOKEN_SEGMENTATION.ordinal()] = layer;
    }

    @Override
    public TokenSegmentationLayerStored getTokenSegmentationLayer() {
        return ((TokenSegmentationLayerStored) layersInOrder[ExternalDataLayerTag.TOKEN_SEGMENTATION.ordinal()]);
    }

    @XmlElement(name = PhoneticSegmentationLayerStored.XML_NAME)
    protected void setPhoneticSegmentationLayer(PhoneticSegmentationLayerStored layer) {
        System.out.println(layer);
        layersInOrder[ExternalDataLayerTag.PHONETIC_SEGMENTATION.ordinal()] = layer;
    }

    @Override
    public PhoneticSegmentationLayerStored getPhoneticSegmentationLayer() {
        return ((PhoneticSegmentationLayerStored) layersInOrder[ExternalDataLayerTag.PHONETIC_SEGMENTATION.ordinal()]);
    }

    @XmlElement(name = CanonicalSegmentationLayerStored.XML_NAME)
    protected void setCanonicalSegmentationLayer(CanonicalSegmentationLayerStored layer) {
        System.out.println(layer);
        layersInOrder[ExternalDataLayerTag.CANONICAL_SEGMENTATION.ordinal()] = layer;
    }

    @Override
    public CanonicalSegmentationLayerStored getCanonicalSegmentationLayer() {
        return ((CanonicalSegmentationLayerStored) layersInOrder[ExternalDataLayerTag.CANONICAL_SEGMENTATION.ordinal()]);
    }

//		protected void afterUnmarshal(Unmarshaller u, Object parent) {
//			connectLayers();
//		}
//
//
//		protected void connectLayers() {
//			for (int i = 0; i < this.layersInOrder.length; i++) {
//				if (layersInOrder[i] != null) {
//					layersInOrder[i].setLayersConnector(connector);
//				}
//			}
//		}
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(XML_NAME);
        sb.append(":\n");

        for (ExternalDataLayer layer : this.layersInOrder) {
            if (layer != null) {
                sb.append(layer);
                sb.append("\n");
            }
        }

        return sb.toString().trim();
    }

    /**
     * Composes the layers into one document. Normally, you should not use this
     * method, unless you want to manually compose document from the layer
     * pieces.
     *
     * @param layers
     * @return external data composed of the provided layers
     */
    public static ExternalDataStored compose(ExternalDataLayerStored... layers) {
        ExternalDataStored ed = new ExternalDataStored();
        for (ExternalDataLayerStored layer : layers) {
            ed.layersInOrder[ExternalDataLayerTag.getFromClass(layer.getClass()).ordinal()] = layer;
        }
        return ed;
    }
}
