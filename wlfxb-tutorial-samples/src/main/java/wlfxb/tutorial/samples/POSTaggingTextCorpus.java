package wlfxb.tutorial.samples;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import de.tuebingen.uni.sfs.wlf1.io.TextCorpusStreamed;
import de.tuebingen.uni.sfs.wlf1.io.WLFormatException;
import de.tuebingen.uni.sfs.wlf1.tc.api.PosTagsLayer;
import de.tuebingen.uni.sfs.wlf1.tc.api.Token;
import de.tuebingen.uni.sfs.wlf1.tc.xb.TextCorpusLayerTag;


public class POSTaggingTextCorpus {
	
	POSTaggerSimulator tagger;
	
	public POSTaggingTextCorpus() {
		tagger = new POSTaggerSimulator();
	}

	// a tagger to simulate part-of-speech tagging for this tutorial
	class POSTaggerSimulator {
		
		private Map<String,String> knownTags = new HashMap<String,String>();
		private String defaultTag;
		POSTaggerSimulator() {
			initTags();
		}
		private void initTags() {
			knownTags.put("Karin", "NN");
			knownTags.put("fliegt", "VVFIN");
			knownTags.put("will", "VVFIN");
			knownTags.put("machen", "VVINF");
			knownTags.put("nach", "APPR");
			knownTags.put("New", "NE");
			knownTags.put("York", "NE");
			knownTags.put("Sie", "PPER");
			knownTags.put("dort", "ADV");
			knownTags.put(".", "$.");
			defaultTag = "NN";
		}
		
		String tag(String token) {
			String tag = knownTags.get(token);
			if (tag == null) {
				tag = defaultTag;
			}
			return tag;
		}
		
	}
	
    private void process(InputStream is, OutputStream os) throws WLFormatException  {
    	
    	// we specify which layer/layers annotations you want to read in order to process 
    	// them using them for creating new layer annotations.
    	// In this example we only need token annotations in order to assign part-of-speech tags:
    	EnumSet<TextCorpusLayerTag> layersToRead = EnumSet.of(TextCorpusLayerTag.TOKENS);
    	
    	// we create TextCorpusStreamed object with the specified layers and input output streams
    	// This object will load into memory only the layers you've specified and will skip other layers
    	// present in the input:
    	TextCorpusStreamed textCorpus = new TextCorpusStreamed(is, layersToRead, os);
    	
    	// we create part-of-speech tags layer and specify the part-of-speech tagset used:
    	PosTagsLayer posLayer = textCorpus.createPosTagsLayer("STTS");
    	
    	// we process token annotations from tokens layer
    	for (int i = 0; i < textCorpus.getTokensLayer().size(); i++) {
			Token token = textCorpus.getTokensLayer().getToken(i);
			// we create part-of-speech tags for each token string
			// and add them to TCF referencing the corresponding token:
			posLayer.addTag(tagger.tag(token.getString()), token);
		}
    	
    	// IMPORTANT: Close the TextCorpusStreamer streams!!!
    	textCorpus.close();
    	
    	// that's it!
    	// now check output file, the layer is added there
	}
		
	
	    public static void main(String[] args) throws IOException, WLFormatException {
	    	
	    	String fileNameIn = "data/tcf_with_text_toks_sents.xml";
	    	String fileNameOut = "data/OUT-tcf_with_text_toks_sents_pos.xml";
	    	
	    	FileInputStream fis = new FileInputStream(fileNameIn);
	    	FileOutputStream fos = new FileOutputStream(fileNameOut);
	    	
	    	POSTaggingTextCorpus sample = new POSTaggingTextCorpus();
	    	sample.process(fis, fos);
	    	System.out.println("Done! Check added part-of-speech tags layer in " + fileNameOut);
	    	
	    }
}
