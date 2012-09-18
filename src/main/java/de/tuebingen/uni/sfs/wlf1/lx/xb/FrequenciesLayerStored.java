/**
 * 
 */
package de.tuebingen.uni.sfs.wlf1.lx.xb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.tuebingen.uni.sfs.wlf1.lx.api.FrequenciesLayer;
import de.tuebingen.uni.sfs.wlf1.lx.api.Frequency;
import de.tuebingen.uni.sfs.wlf1.lx.api.Lemma;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name=FrequenciesLayerStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class FrequenciesLayerStored extends LexiconLayerStoredAbstract implements FrequenciesLayer {
	
	public static final String XML_NAME = "frequencies";
	
	@XmlElement(name=FrequencyStored.XML_NAME)
	private List<FrequencyStored> frequencies = new ArrayList<FrequencyStored>();
	
	LexiconLayersConnector connector;
	
	protected FrequenciesLayerStored() {}
	
	protected FrequenciesLayerStored(LexiconLayersConnector connector) {
		this.connector = connector;
	}
	
	protected void setLayersConnector(LexiconLayersConnector connector) {
		this.connector = connector;
		for (FrequencyStored freq : frequencies) {
			connector.lemma2ItsFreq.put(connector.lemmaId2ItsLemma.get(freq.lemRef), freq);
		}
	}

	@Override
	public boolean isEmpty() {
		if (frequencies.size() == 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public int size() {
		return frequencies.size();
	}
	
	
	@Override
	public Frequency getFrequency(int index) {
		Frequency freq = frequencies.get(index);
		return freq;	
	}
	
	@Override
	public Frequency getFrequency(Lemma lemma) {
		Frequency freq = connector.lemma2ItsFreq.get(lemma);
		return freq;	
	}
	
	@Override
	public Lemma getLemma(Frequency freq) {
		if (freq instanceof FrequencyStored) {
			FrequencyStored freqStored = (FrequencyStored) freq;
			return connector.lemmaId2ItsLemma.get(freqStored.lemRef);
		} else {
			return null;
		}
	}
	
	@Override
	public Frequency addFrequency(Lemma lemma, int frequency) {
		FrequencyStored freq = new FrequencyStored();
		//int count = tags.size();
		//tagStored.tagId = PosTagStored.ID_PREFIX + count;
		freq.value = frequency;
		freq.lemRef = lemma.getID();
		connector.lemma2ItsFreq.put(lemma, freq);
		frequencies.add(freq);
		return freq;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(XML_NAME);
		sb.append(" : ");
		sb.append(frequencies.toString());
		return sb.toString();
	}

}
