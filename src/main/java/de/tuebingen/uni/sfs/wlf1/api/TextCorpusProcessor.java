package de.tuebingen.uni.sfs.wlf1.api;

import de.tuebingen.uni.sfs.wlf1.tc.api.TextCorpus;
import de.tuebingen.uni.sfs.wlf1.tc.xb.TextCorpusLayerTag;
import java.util.EnumSet;

/**
 *
 * @author akislev
 */
public interface TextCorpusProcessor {

    public EnumSet<TextCorpusLayerTag> getRequiredLayers();

    public void process(TextCorpus textCorpus) throws TextCorpusProcessorException;
}
