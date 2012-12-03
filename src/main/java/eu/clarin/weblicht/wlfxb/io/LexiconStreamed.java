/**
 *
 */
package eu.clarin.weblicht.wlfxb.io;

import eu.clarin.weblicht.wlfxb.lx.api.LexiconLayer;
import eu.clarin.weblicht.wlfxb.lx.xb.LexiconLayerStoredAbstract;
import eu.clarin.weblicht.wlfxb.lx.xb.LexiconLayerTag;
import eu.clarin.weblicht.wlfxb.lx.xb.LexiconStored;
import eu.clarin.weblicht.wlfxb.md.xb.MetaData;
import eu.clarin.weblicht.wlfxb.md.xb.MetaDataItem;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumSet;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.XMLEvent;

/**
 * @author Yana Panchenko
 *
 */
public class LexiconStreamed extends LexiconStored {

    private EnumSet<LexiconLayerTag> layersToRead;
    private EnumSet<LexiconLayerTag> readSucceeded = EnumSet.noneOf(LexiconLayerTag.class);
    private XMLEventReader xmlEventReader;
    private XMLEventWriter xmlEventWriter;
    private XmlReaderWriter xmlReaderWriter;
    private static final int LAYER_INDENT_RELATIVE = 1;

    public LexiconStreamed(InputStream inputStream,
            EnumSet<LexiconLayerTag> layersToRead)
            throws WLFormatException {
        super("unknown");
        this.layersToRead = layersToRead;
        try {
            initializeReaderAndWriter(inputStream, null, false);
            process();
        } catch (WLFormatException e) {
            xmlReaderWriter.close();
            throw e;
        }
    }

    public LexiconStreamed(InputStream inputStream,
            EnumSet<LexiconLayerTag> layersToRead, OutputStream outputStream)
            throws WLFormatException {
        super("unknown");
        this.layersToRead = layersToRead;
        try {
            initializeReaderAndWriter(inputStream, outputStream, false);
            process();
        } catch (WLFormatException e) {
            xmlReaderWriter.close();
            throw e;
        }
    }

    public LexiconStreamed(InputStream inputStream,
            EnumSet<LexiconLayerTag> layersToRead, OutputStream outputStream,
            boolean outputAsXmlFragment)
            throws WLFormatException {
        super("unknown");
        this.layersToRead = layersToRead;
        try {
            initializeReaderAndWriter(inputStream, outputStream, outputAsXmlFragment);
            process();
        } catch (WLFormatException e) {
            xmlReaderWriter.close();
            throw e;
        }
    }

    public LexiconStreamed(InputStream inputStream,
            EnumSet<LexiconLayerTag> layersToRead, OutputStream outputStream,
            List<MetaDataItem> metaDataToAdd)
            throws WLFormatException {
        super("unknown");
        this.layersToRead = layersToRead;
        try {
            initializeReaderAndWriter(inputStream, outputStream, false);
            addMetadata(metaDataToAdd);
            process();
        } catch (WLFormatException e) {
            xmlReaderWriter.close();
            throw e;
        }
    }

    private void initializeReaderAndWriter(InputStream inputStream, OutputStream outputStream, boolean outputAsXmlFragment) throws WLFormatException {
        if (inputStream != null) {
            try {
                XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
                xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream, "UTF-8");
            } catch (XMLStreamException e) {
                throw new WLFormatException(e.getMessage(), e);
            }
        }
        if (outputStream != null) {
            try {
                XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
                xmlEventWriter = xmlOutputFactory.createXMLEventWriter(outputStream, "UTF-8");
            } catch (XMLStreamException e) {
                throw new WLFormatException(e.getMessage(), e);
            }
        }
        xmlReaderWriter = new XmlReaderWriter(xmlEventReader, xmlEventWriter);
        xmlReaderWriter.setOutputAsXmlFragment(outputAsXmlFragment);
    }

    private void addMetadata(List<MetaDataItem> metaDataToAdd) throws WLFormatException {
        try {
            xmlReaderWriter.readWriteUpToEndElement(MetaData.XML_NAME);
            marshall(metaDataToAdd);
            // rewrite metadata end element
            XMLEvent event = xmlEventReader.nextEvent();
            xmlReaderWriter.add(event);
        } catch (XMLStreamException e) {
            throw new WLFormatException(e.getMessage(), e);
        }
    }

    private void process() throws WLFormatException {
        try {
            xmlReaderWriter.readWriteUpToStartElement(LexiconStored.XML_NAME);
            // process TextCorpus start element
            XMLEvent event = xmlEventReader.nextEvent();
            super.lang = event.asStartElement().getAttributeByName(new QName("lang")).getValue();
            // add processed TextCorpus start back
            xmlReaderWriter.add(event);
            // create TextCorpus object
            // read layers requested stopping before TextCorpus end element
            processLayers();
            super.connectLayers();
            // if no writing requested finish reading the document
            if (xmlEventWriter == null) {
                xmlReaderWriter.readWriteToTheEnd();
            }
        } catch (XMLStreamException e) {
            throw new WLFormatException(e.getMessage(), e);
        }
        if (layersToRead.size() != readSucceeded.size()) {
            layersToRead.removeAll(readSucceeded);
            throw new WLFormatException("Following layers could not be read: " + layersToRead.toString());
        }
    }

    private void processLayers() throws WLFormatException {
        boolean textCorpusEnd = false;
        XMLEvent peekedEvent;
        try {
            peekedEvent = xmlEventReader.peek();
            while (!textCorpusEnd && peekedEvent != null) {
                if (peekedEvent.getEventType() == XMLStreamConstants.END_ELEMENT
                        && peekedEvent.asEndElement().getName().getLocalPart().equals(LexiconStored.XML_NAME)) {
                    textCorpusEnd = true;
                } else if (peekedEvent.getEventType() == XMLStreamConstants.START_ELEMENT) {
                    processLayer();
                    peekedEvent = xmlEventReader.peek();
                } else {
                    XMLEvent readEvent = xmlReaderWriter.readEvent();
                    xmlReaderWriter.add(readEvent);
                    peekedEvent = xmlEventReader.peek();
                }
            }
        } catch (XMLStreamException e) {
            throw new WLFormatException(e.getMessage(), e);
        }

        if (!textCorpusEnd) {
            throw new WLFormatException(LexiconStored.XML_NAME + " end tag not found");
        }
    }

    private void processLayer() throws WLFormatException {

        XMLEvent peekedEvent;
        try {
            peekedEvent = xmlEventReader.peek();
            // now we assume that this event is start of a TextCorpus layer
            String tagName = peekedEvent.asStartElement().getName().getLocalPart();
            LexiconLayerTag layerTag = LexiconLayerTag.getFromXmlName(tagName);

            if (layerTag == null) { // unknown layer, just add it to output
                //readWriteElement(tagName);
                xmlReaderWriter.readWriteElement(tagName);
            } else if (this.layersToRead.contains(layerTag)) { // known layer, and is requested for reading
                // add it to the output, but store its data
                readLayerData(layerTag);
            } else { // known layer, and is not requested for reading
                // just add it to the output
                xmlReaderWriter.readWriteElement(tagName);
            }
        } catch (XMLStreamException e) {
            throw new WLFormatException(e.getMessage(), e);
        }


    }

    private void readLayerData(LexiconLayerTag layerTag) throws WLFormatException {
        JAXBContext context;
        Unmarshaller unmarshaller;
        try {
            context = JAXBContext.newInstance(layerTag.getLayerClass());
            unmarshaller = context.createUnmarshaller();
            LexiconLayerStoredAbstract layer = (LexiconLayerStoredAbstract) unmarshaller.unmarshal(xmlEventReader);
            super.layersInOrder[layerTag.ordinal()] = layer;
            marshall(super.layersInOrder[layerTag.ordinal()]);
        } catch (JAXBException e) {
            throw new WLFormatException(e.getMessage(), e);
        }
        readSucceeded.add(layerTag);
    }

    private void marshall(LexiconLayer layer) throws WLFormatException {
        if (xmlEventWriter == null) {
            return;
        }
        JAXBContext context;
        try {
            xmlReaderWriter.startExternalFragment(LAYER_INDENT_RELATIVE);
            context = JAXBContext.newInstance(layer.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(layer, xmlEventWriter);
            xmlReaderWriter.endExternalFragment(LAYER_INDENT_RELATIVE);
        } catch (JAXBException e) {
            throw new WLFormatException(e.getMessage(), e);
        } catch (XMLStreamException e) {
            throw new WLFormatException(e.getMessage(), e);
        }
    }

    private void marshall(List<MetaDataItem> metaDataToAdd) throws WLFormatException {
        if (xmlEventWriter == null) {
            return;
        }
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(MetaDataItem.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            for (MetaDataItem mdi : metaDataToAdd) {
                xmlReaderWriter.startExternalFragment(LAYER_INDENT_RELATIVE);
                marshaller.marshal(mdi, xmlEventWriter);
                xmlReaderWriter.endExternalFragment(LAYER_INDENT_RELATIVE);
            }
        } catch (JAXBException e) {
            throw new WLFormatException(e.getMessage(), e);
        } catch (XMLStreamException e) {
            throw new WLFormatException(e.getMessage(), e);
        }
    }

    public void close() throws WLFormatException {
        boolean[] layersRead = new boolean[super.layersInOrder.length];
        for (LexiconLayerTag layerRead : layersToRead) {
            layersRead[layerRead.ordinal()] = true;
        }

        for (int i = 0; i < super.layersInOrder.length; i++) {
            if (super.layersInOrder[i] != null && !layersRead[i]
                    // && !super.layersInOrder[i].isEmpty() 
                    ) {
                marshall(super.layersInOrder[i]);
            }
        }
        xmlReaderWriter.readWriteToTheEnd();
    }
}
