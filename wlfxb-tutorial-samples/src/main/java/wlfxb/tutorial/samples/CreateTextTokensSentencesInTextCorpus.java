package wlfxb.tutorial.samples;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import de.tuebingen.uni.sfs.wlf1.io.WLDObjector;
import de.tuebingen.uni.sfs.wlf1.io.WLFormatException;
import de.tuebingen.uni.sfs.wlf1.tc.api.SentencesLayer;
import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.tc.api.TokensLayer;
import de.tuebingen.uni.sfs.wlf1.tc.xb.TextCorpusStored;
import de.tuebingen.uni.sfs.wlf1.xb.WLData;

/**
 * @author Yana Panchenko
 *
 */
public class CreateTextTokensSentencesInTextCorpus {
	
	public void process(String lang, String text, List<String[]> tokenizedSentences, OutputStream os) throws WLFormatException {
		
		// create TextCorpus object, specifying that the data will be in German language (de)
		TextCorpusStored textCorpus = new TextCorpusStored("de");
		// create text annotation layer and add the string of the text into the layer
		textCorpus.createTextLayer().addText(text);
		// create tokens annotation layer
		TokensLayer tokensLayer = textCorpus.createTokensLayer();
		// create sentences annotation layer
		SentencesLayer sentencesLayer = textCorpus.createSentencesLayer();
		// iterate sentence by sentence
		for (String[] tokenizedSentence : tokenizedSentences) {
			// prepare temporary list to store this sentence tokens
			List<Token> sentenceTokens = new ArrayList<Token>();
			// iterate token by token
			for (String tokenString : tokenizedSentence) {
				// create token annotation and add it into the tokens annotation layer:
				Token token = tokensLayer.addToken(tokenString);
				// add it into temporary list that stores this sentence tokens
				sentenceTokens.add(token);
			}
			// create sentence annotation and add it into the sentences annotation layer
			sentencesLayer.addSentence(sentenceTokens);
		}
		// wrap TextCorpus object into the object representing the annotated data in the exchange format:
		WLData wlData = new WLData(textCorpus);
		// write the annotated data object into the output stream
		WLDObjector.write(wlData, os);
		// that's it, you are done!
		
		// PS: alternatively, you could write textCorpus without creating the wrapping:
		// WLDObjector.write(new MetaData(), textCorpus, os, false);
	}
	


	public static void main(String[] args) throws IOException, WLFormatException {

    	String myText = "Karin fliegt nach New York. Sie will dort Urlaub machen.";
    	List<String[]> mySentences = new ArrayList<String[]>();
    	mySentences.add(new String[]{"Karin", "fliegt", "nach", "New", "York", "."});
    	mySentences.add(new String[]{"Sie", "will", "dort", "Urlaub", "machen", "."});
    	
    	String fileNameOut = "data/OUT-tcf_with_text_toks_sents.xml";
    	FileOutputStream fos = new FileOutputStream(fileNameOut);
    	
    	CreateTextTokensSentencesInTextCorpus sample = new CreateTextTokensSentencesInTextCorpus();
    	sample.process("de", myText, mySentences, fos);
    	
    	System.out.println("Done! Check " + fileNameOut);

    }

}
