package eu.clarin.weblicht.wlfxb.api;

import eu.clarin.weblicht.wlfxb.tc.api.TextCorpus;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusLayerTag;
import java.util.EnumSet;

/**
 *
 * @author akislev
 */
public interface TextCorpusProcessor {

    public EnumSet<TextCorpusLayerTag> getRequiredLayers();

    public void process(TextCorpus textCorpus) throws TextCorpusProcessorException;
}
