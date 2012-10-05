/**
 * 
 */
package eu.clarin.weblicht.wlfxb.io;

import eu.clarin.weblicht.wlfxb.md.xb.MetaData;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusStored;
import eu.clarin.weblicht.wlfxb.utils.CommonConstants;
import eu.clarin.weblicht.wlfxb.xb.WLData;
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
 * @author Yana Panchenko
 * 
 */
public class WLDObjector {
	

	public static WLData read(InputStream inputStream) throws WLFormatException {
		WLData data = null;
		try {
			JAXBContext context = JAXBContext.newInstance(WLData.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			data = ((WLData) unmarshaller.unmarshal(inputStream));
		} catch (JAXBException e) {
			throw new WLFormatException(e);
		}
		return data;
	}
	
	public static WLData read(Reader reader) throws WLFormatException {
		WLData data = null;
		try {
			JAXBContext context = JAXBContext.newInstance(WLData.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			data = ((WLData) unmarshaller.unmarshal(reader));
		} catch (JAXBException e) {
			throw new WLFormatException(e);
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
		try {
			JAXBContext context = JAXBContext.newInstance(WLData.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			//marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, SCHEMA_LOCATION);
			marshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders", 
					CommonConstants.XML_MODEL_DECLARATION_WITH_WL1_PI_CONTENT); 
			marshaller.marshal(wlData, outputStream);
		} catch (JAXBException e) {
			throw new WLFormatException(e);
		}
		
	}
	
	public static void write(WLData wlData, File file)  throws WLFormatException {
		try {
			JAXBContext context = JAXBContext.newInstance(WLData.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			//marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, SCHEMA_LOCATION);
			marshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders", 
					CommonConstants.XML_MODEL_DECLARATION_WITH_WL1_PI_CONTENT); 
			marshaller.marshal(wlData, file);
		} catch (JAXBException e) {
				throw new WLFormatException(e);
		}
	}
	
	public static void write(WLData wlData, OutputStream outputStream, boolean outputAsXmlFragment)  throws WLFormatException {
		try {
			JAXBContext context = JAXBContext.newInstance(WLData.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, outputAsXmlFragment);
			//marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, SCHEMA_LOCATION);
			if (!outputAsXmlFragment) {
				marshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders", 
						CommonConstants.XML_MODEL_DECLARATION_WITH_WL1_PI_CONTENT); 
			}
			marshaller.marshal(wlData, outputStream);
		} catch (JAXBException e) {
			throw new WLFormatException(e);
		}
	}
	
	public static void write(WLData wlData, File file, boolean outputAsXmlFragment)  throws WLFormatException {
		try {
			JAXBContext context = JAXBContext.newInstance(WLData.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, outputAsXmlFragment);
			//marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, SCHEMA_LOCATION);
			if (!outputAsXmlFragment) {
				marshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders", 
						CommonConstants.XML_MODEL_DECLARATION_WITH_WL1_PI_CONTENT); 
			}
		marshaller.marshal(wlData, file);
		} catch (JAXBException e) {
				throw new WLFormatException(e);
		}
	}
	
	
	public static void write(MetaData md, TextCorpusStored tc,  File file, boolean outputAsXmlFragment)  throws WLFormatException {
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			write(md, tc, outputStream, outputAsXmlFragment);
			outputStream.close();
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
	
	
	public static void write(MetaData md, TextCorpusStored tc, 
			OutputStream outputStream, boolean outputAsXmlFragment) 
	 throws WLFormatException {
		
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLOutputFactory xmlOututFactory = XMLOutputFactory.newInstance();
		XMLEvent e;
		XMLEventWriter xmlEventWriter = null;
		
		try {
			xmlEventWriter = xmlOututFactory.createXMLEventWriter(outputStream, "UTF-8");

//			JAXBContext context = JAXBContext.newInstance(WLData.class);
//			Marshaller marshaller = context.createMarshaller();
//			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			
			if (!outputAsXmlFragment) {
				e = eventFactory.createStartDocument("UTF-8");
				xmlEventWriter.add(e);
				e = eventFactory.createIgnorableSpace(XmlReaderWriter.NEW_LINE);
	            xmlEventWriter.add(e);
				e = eventFactory.createProcessingInstruction(
						XmlReaderWriter.XML_WL1_MODEL_PI_NAME, 
						CommonConstants.XML_WL1_MODEL_PI_CONTENT);
	            xmlEventWriter.add(e);
	            e = eventFactory.createIgnorableSpace(XmlReaderWriter.NEW_LINE);
	            xmlEventWriter.add(e);
			}
	
			Attribute versionAttr = eventFactory.createAttribute("version", WLData.XML_VERSION);
			List<Attribute> attrs = new ArrayList<Attribute>();
			attrs.add(versionAttr);
			Namespace ns = eventFactory.createNamespace(WLData.XML_NAMESPACE);
			List<Namespace> nss = new ArrayList<Namespace>();
			nss.add(ns);
			e = eventFactory.createStartElement("", WLData.XML_NAMESPACE, WLData.XML_NAME, attrs.iterator(), nss.iterator());
			xmlEventWriter.add(e);
			e = eventFactory.createIgnorableSpace(XmlReaderWriter.NEW_LINE);
	        xmlEventWriter.add(e);
			
			JAXBContext mdContext = JAXBContext.newInstance(MetaData.class);
			Marshaller mdMarshaller = mdContext.createMarshaller();
			mdMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			mdMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			mdMarshaller.marshal(md, outputStream);
			
			JAXBContext tcContext = JAXBContext.newInstance(TextCorpusStored.class);
			Marshaller tcMarshaller = tcContext.createMarshaller();
			tcMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			tcMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			tcMarshaller.marshal(tc, outputStream);
	        
//	        marshaller.marshal(md, outputStream);
//			marshaller.marshal(tc, outputStream);
			
			e = eventFactory.createEndElement("", WLData.XML_NAMESPACE, WLData.XML_NAME);
			xmlEventWriter.add(e);
			
			if (!outputAsXmlFragment) {
				e = eventFactory.createEndDocument();
				xmlEventWriter.add(e);
			}
		} catch (Exception ex) {
			throw new WLFormatException(ex);
		} finally {
			if (xmlEventWriter != null) {
				try {
					xmlEventWriter.flush();
					xmlEventWriter.close();
				} catch (XMLStreamException ex2){
					throw new WLFormatException(ex2);
				}
			}
		}
	}

}
