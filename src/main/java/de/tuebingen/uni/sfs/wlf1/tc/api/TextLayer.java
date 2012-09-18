package de.tuebingen.uni.sfs.wlf1.tc.api;

public interface TextLayer extends TextCorpusLayer {

	//public List<TextSegment> getTextSegments();
	public String getText();
	public void addText(String text);
	
}
