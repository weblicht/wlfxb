package eu.clarin.weblicht.wlfxb.lx.xb;

import eu.clarin.weblicht.wlfxb.lx.api.Frequency;
import eu.clarin.weblicht.wlfxb.lx.api.Lemma;
import eu.clarin.weblicht.wlfxb.lx.api.PosTag;
import eu.clarin.weblicht.wlfxb.lx.api.Relation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LexiconLayersConnector {

    // maps for connecting elements of different layers that reference one another
    protected Map<String, Lemma> lemmaId2ItsLemma = new HashMap<String, Lemma>();
    protected Map<Lemma, List<PosTag>> lemma2ItsTags = new HashMap<Lemma, List<PosTag>>();
    protected Map<Lemma, Frequency> lemma2ItsFreq = new HashMap<Lemma, Frequency>();
    protected Map<Lemma, List<Relation>> lemma2ItsRels = new HashMap<Lemma, List<Relation>>();

    LexiconLayersConnector() {
        super();
    }
}
