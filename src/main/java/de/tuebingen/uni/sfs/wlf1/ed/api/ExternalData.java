package de.tuebingen.uni.sfs.wlf1.ed.api;

import java.util.List;

/**
 * @author Yana Panchenko
 *
 */

public interface ExternalData {
	
	public List<ExternalDataLayer> getLayers();
	
	public SpeechSignalLayer getSpeechSignalLayer();
	public SpeechSignalLayer createSpeechSignalLayer(String mimeType);
	public SpeechSignalLayer createSpeechSignalLayer(String mimeType, int numberOfChannels);
	
	public TokenSegmentationLayer getTokenSegmentationLayer();
	public TokenSegmentationLayer createTokenSegmentationLayer(String mimeType);
	
	public PhoneticSegmentationLayer getPhoneticSegmentationLayer();
	public PhoneticSegmentationLayer createPhoneticSegmentationLayer(String mimeType);
	
	public CanonicalSegmentationLayer getCanonicalSegmentationLayer();
	public CanonicalSegmentationLayer createCanonicalSegmentationLayer(String mimeType);
	
}
