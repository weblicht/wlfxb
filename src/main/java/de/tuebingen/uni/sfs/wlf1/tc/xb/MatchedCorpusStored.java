package de.tuebingen.uni.sfs.wlf1.tc.xb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.tuebingen.uni.sfs.wlf1.tc.api.MatchedCorpus;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;

/**
 * @author Yana Panchenko
 *
 */
@XmlRootElement(name=MatchedCorpusStored.XML_NAME)
@XmlAccessorType(XmlAccessType.NONE)
public class MatchedCorpusStored implements MatchedCorpus {
	
	public static final String XML_NAME = "corpus";

	@XmlAttribute(name=CommonAttributes.NAME, required = true)
	String name;
	@XmlAttribute(name=CommonAttributes.PID, required = true)
	String pid;
	@XmlElement(name=MatchedItemStored.XML_NAME)
	List<MatchedItemStored> matchedItems = new ArrayList<MatchedItemStored>();
		
		
	MatchedCorpusStored() {}
	
	MatchedCorpusStored(String name, String pid) {
		this.name = name;
		this.pid = pid;
	}
	
	@Override
	public MatchedItemStored[] getMatchedItems() {
		// return array in order not to let user add new things to the items list
		MatchedItemStored[] items = new MatchedItemStored[matchedItems.size()];
		return matchedItems.toArray(items);
	}
		
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getPID() {
		return pid;
	}
		
	
	 @Override
	public String toString() {
		StringBuilder sb = new StringBuilder(XML_NAME);
		sb.append(" ");
		sb.append(name);
		sb.append(" ");
		sb.append(pid);
		sb.append(" ");
		sb.append(matchedItems.toString());
		return sb.toString().trim();
	}


}
