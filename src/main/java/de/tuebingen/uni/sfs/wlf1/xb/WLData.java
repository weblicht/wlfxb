/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.xb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.tuebingen.uni.sfs.wlf1.ed.api.ExternalData;
import de.tuebingen.uni.sfs.wlf1.ed.xb.ExternalDataStored;
import de.tuebingen.uni.sfs.wlf1.lx.xb.LexiconStored;
import de.tuebingen.uni.sfs.wlf1.md.xb.MetaData;
import de.tuebingen.uni.sfs.wlf1.tc.xb.TextCorpusStored;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name = WLData.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class WLData {
	
	public static final String XML_NAME = "D-Spin";
	public static final String XML_NAMESPACE = "http://www.dspin.de/data";
	public static final String XML_VERSION = "0.4";
	
	@XmlAttribute
	private String version;
	@XmlElement(name = "MetaData", namespace = "http://www.dspin.de/data/metadata")
	private MetaData metaData;
	@XmlElement(name = "ExternalData", namespace = "http://www.dspin.de/data/extdata")
	private ExternalDataStored extData;
	@XmlElement(name = "TextCorpus", namespace = "http://www.dspin.de/data/textcorpus")
	private TextCorpusStored textCorpus;
	@XmlElement(name = "Lexicon", namespace = "http://www.dspin.de/data/lexicon")
	private LexiconStored lexicon;
	
	WLData() {

	}
	
	
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}


	/**
	 * @return the metaData
	 */
	public MetaData getMetaData() {
		return metaData;
	}

	/**
	 * @return the metaData
	 */
	public ExternalData getExternalData() {
		return extData;
	}

	/**
	 * @return the textCorpus
	 */
	public TextCorpusStored getTextCorpus() {
		return textCorpus;
	}
	
	public LexiconStored getLexicon() {
		return lexicon;
	}

	public WLData(MetaData metaData, ExternalDataStored extData) {
		this.version = XML_VERSION;
		this.metaData = metaData;
		this.extData = extData;
	}

	public WLData(MetaData metaData, TextCorpusStored textCorpus) {
		this.version = XML_VERSION;
		this.metaData = metaData;
		this.textCorpus = textCorpus;
	}
	
	public WLData(MetaData metaData, ExternalDataStored extData, TextCorpusStored textCorpus) {
		this.version = XML_VERSION;
		this.metaData = metaData;
		this.extData = extData;
		this.textCorpus = textCorpus;
	}
	
	public WLData(TextCorpusStored textCorpus) {
		this.version = XML_VERSION;
		this.metaData = new MetaData();
		this.textCorpus = textCorpus;
	}
	
	public WLData(ExternalDataStored extData) {
		this.version = XML_VERSION;
		this.metaData = new MetaData();
		this.extData = extData;
	}
	
	public WLData(ExternalDataStored extData, TextCorpusStored textCorpus) {
		this.version = XML_VERSION;
		this.metaData = new MetaData();
		this.extData = extData;
		this.textCorpus = textCorpus;
	}
	
	public WLData(MetaData metaData, LexiconStored lexicon) {
		this.version = XML_VERSION;
		this.metaData = metaData;
		this.lexicon = lexicon;
	}
	
	public WLData(LexiconStored lexicon) {
		this.version = XML_VERSION;
		this.metaData = new MetaData();
		this.lexicon = lexicon;
	}

}
