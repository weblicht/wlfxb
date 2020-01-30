/**
 * wlfxb - a library for creating and processing of TCF data streams.
 *
 * Copyright (C) University of Tübingen.
 *
 * This file is part of wlfxb.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.clarin.weblicht.wlfxb.io;

import eu.clarin.weblicht.wlfxb.lx.api.Lexicon;
import eu.clarin.weblicht.wlfxb.lx.xb.LexiconStored;
import eu.clarin.weblicht.wlfxb.md.xb.MetaData;
import eu.clarin.weblicht.wlfxb.tc.api.TextCorpus;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusStored;
import eu.clarin.weblicht.wlfxb.utils.CommonConstants;
import eu.clarin.weblicht.wlfxb.xb.WLData;
import javanet.staxutils.IndentingXMLEventWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.XMLEvent;

/**
 * Class <tt>WLDObjector</tt> helps to read/write
 * {@link eu.clarin.weblicht.wlfxb.xb.WLData} from/to TCF stream.
 *
 * @author Yana Panchenko
 */
public class WLDObjector {
    public static JAXBContextFactory JAXB_CONTEXT_FACTORY = JAXBContext::newInstance;

    public static WLData read(InputStream inputStream) throws WLFormatException {
        WLData data = null;
        try {
            JAXBContext context = JAXB_CONTEXT_FACTORY.newInstance(WLData.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            data = ((WLData) unmarshaller.unmarshal(inputStream));
        } catch (JAXBException e) {
            throw new WLFormatException(e.getMessage(), e);
        }
        return data;
    }

    public static WLData read(Reader reader) throws WLFormatException {
        WLData data = null;
        try {
            JAXBContext context = JAXB_CONTEXT_FACTORY.newInstance(WLData.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            data = ((WLData) unmarshaller.unmarshal(reader));
        } catch (JAXBException e) {
            throw new WLFormatException(e.getMessage(), e);
        }
        return data;
    }

    public static WLData read(File file) throws WLFormatException {
        WLData data = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            data = read(fis);
        } catch (IOException e) {
            throw new WLFormatException(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new WLFormatException(e);
                }
            }
        }
        return data;
    }

    public static void write(WLData wlData, OutputStream outputStream) throws WLFormatException {
        write(wlData.getMetaData(), wlData.getTextCorpus(), wlData.getLexicon(), outputStream, false, wlData.getVersion());
    }

    public static void write(WLData wlData, File file) throws WLFormatException {
        write(wlData.getMetaData(), wlData.getTextCorpus(), wlData.getLexicon(), file, false, wlData.getVersion());
    }

    public static void write(WLData wlData, OutputStream outputStream, boolean outputAsXmlFragment) throws WLFormatException {
        write(wlData.getMetaData(), wlData.getTextCorpus(), wlData.getLexicon(), outputStream, outputAsXmlFragment, wlData.getVersion());
    }

    public static void write(WLData wlData, File file, boolean outputAsXmlFragment) throws WLFormatException {
        write(wlData.getMetaData(), wlData.getTextCorpus(), wlData.getLexicon(), file, outputAsXmlFragment, wlData.getVersion());
    }

    public static void write(MetaData md, TextCorpus tc, File file, boolean outputAsXmlFragment) throws WLFormatException {
        write(md, tc, null, file, outputAsXmlFragment, WLData.XML_DEFAULT_VERSION);
    }

    public static void write(MetaData md, TextCorpus tc, OutputStream outputStream, boolean outputAsXmlFragment)
            throws WLFormatException {
        write(md, tc, null, outputStream, outputAsXmlFragment, WLData.XML_DEFAULT_VERSION);
    }

    public static void write(MetaData md, Lexicon lex, File file, boolean outputAsXmlFragment) throws WLFormatException {
        write(md, null, lex, file, outputAsXmlFragment, WLData.XML_DEFAULT_VERSION);
    }

    public static void write(MetaData md, Lexicon lex, OutputStream outputStream, boolean outputAsXmlFragment)
            throws WLFormatException {
        write(md, null, lex, outputStream, outputAsXmlFragment, WLData.XML_DEFAULT_VERSION);
    }

    public static void write(MetaData md, TextCorpus tc, Lexicon lex, File file, boolean outputAsXmlFragment, String version) throws WLFormatException {
        // IMPORTANT: using JAXB marshaller for marshalling directly into File or FileOutputStream
        // replaces quotes with &quot; entities which is not desirable for linguistic data, therefore
        // XMLEventWriter should be used...
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            write(md, tc, lex, outputStream, outputAsXmlFragment, version);
        } catch (Exception e) {
            throw new WLFormatException(e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new WLFormatException(e);
                }
            }
        }
    }

    public static void write(MetaData md, TextCorpus tc, Lexicon lex, OutputStream outputStream, boolean outputAsXmlFragment, String version) throws WLFormatException {
        if (tc == null && lex == null) {
            throw new WLFormatException("cannot write tcf: both textcorpus and lexicon formats are missing");
        } else if (tc != null && lex != null) {
            throw new WLFormatException("cannot write tcf: both textcorpus and lexicon formats are present, but only one is allowed");
        }

        String xmlModel;
        switch (version) {
            case WLData.XML_VERSION_5:
                xmlModel = CommonConstants.XML_WL1_MODEL_PI_CONTENT_FOR_VERSION_5;
                break;
            case WLData.XML_VERSION_04:
                xmlModel = CommonConstants.XML_WL1_MODEL_PI_CONTENT_FOR_VERSION_04;
                break;
            default:
                throw new WLFormatException("Unsupported version. Supported versions are " +
                        WLData.XML_VERSION_5 + " and " + WLData.XML_VERSION_04 + ".");
        }

        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLOutputFactory xmlOututFactory = XMLOutputFactory.newInstance();
        XMLEvent e;
        XMLEventWriter xmlEventWriter = null;

        try {
            xmlEventWriter = new IndentingXMLEventWriter(xmlOututFactory.createXMLEventWriter(outputStream, "UTF-8"));

            if (!outputAsXmlFragment) {
                e = eventFactory.createStartDocument("UTF-8");
                xmlEventWriter.add(e);
                e = eventFactory.createIgnorableSpace(XmlReaderWriter.NEW_LINE);
                xmlEventWriter.add(e);
                e = eventFactory.createProcessingInstruction(XmlReaderWriter.XML_WL1_MODEL_PI_NAME, xmlModel);
                xmlEventWriter.add(e);
                e = eventFactory.createIgnorableSpace(XmlReaderWriter.NEW_LINE);
                xmlEventWriter.add(e);
            }

            Attribute versionAttr = eventFactory.createAttribute("version", version);
            List<Attribute> attrs = new ArrayList<Attribute>();
            attrs.add(versionAttr);
            Namespace ns = eventFactory.createNamespace(WLData.XML_NAMESPACE);
            List<Namespace> nss = new ArrayList<Namespace>();
            nss.add(ns);
            e = eventFactory.createStartElement("", WLData.XML_NAMESPACE, WLData.XML_NAME, attrs.iterator(), nss.iterator());
            xmlEventWriter.add(e);
            e = eventFactory.createIgnorableSpace(XmlReaderWriter.NEW_LINE);
            xmlEventWriter.add(e);

            JAXBContext mdContext = JAXB_CONTEXT_FACTORY.newInstance(MetaData.class);
            Marshaller mdMarshaller = mdContext.createMarshaller();
            //does not work with XMLEventWriter:
            //mdMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            mdMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            mdMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, CommonConstants.CMD_SCHEMA_LOCATION);
            mdMarshaller.marshal(md, xmlEventWriter);

            e = eventFactory.createIgnorableSpace(XmlReaderWriter.NEW_LINE);
            xmlEventWriter.add(e);

            // marshalling textcorpus or lexicon
            {
                JAXBContext context = JAXB_CONTEXT_FACTORY.newInstance(tc != null ? TextCorpusStored.class : LexiconStored.class);
                Marshaller marshaller = context.createMarshaller();
                //does not work with XMLEventWriter:
                //tcMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
                marshaller.marshal(tc != null ? tc : lex, xmlEventWriter);
            }

            e = eventFactory.createIgnorableSpace(XmlReaderWriter.NEW_LINE);
            xmlEventWriter.add(e);

            e = eventFactory.createEndElement("", WLData.XML_NAMESPACE, WLData.XML_NAME);
            xmlEventWriter.add(e);

            if (!outputAsXmlFragment) {
                e = eventFactory.createEndDocument();
                xmlEventWriter.add(e);
            }
        } catch (Exception ex) {
            throw new WLFormatException(ex.getMessage(), ex);
        } finally {
            if (xmlEventWriter != null) {
                try {
                    xmlEventWriter.flush();
                    xmlEventWriter.close();
                } catch (XMLStreamException ex2) {
                    throw new WLFormatException(ex2.getMessage(), ex2);
                }
            }
        }
    }
}
