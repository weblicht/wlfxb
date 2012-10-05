package eu.clarin.weblicht.wlfxb.tc.api;

public interface TextLayer extends TextCorpusLayer {

    //public List<TextSegment> getTextSegments();
    public String getText();

    public void addText(String text);
}
