package de.tuebingen.uni.sfs.wlf1.lx.xb;

import de.tuebingen.uni.sfs.wlf1.lx.api.Frequency;
import de.tuebingen.uni.sfs.wlf1.lx.api.Lemma;
import de.tuebingen.uni.sfs.wlf1.lx.api.PosTag;
import de.tuebingen.uni.sfs.wlf1.lx.api.Relation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LexiconLayersConnector {

    // maps for connecting elements of different layers that reference one another
    Map<String, Lemma> lemmaId2ItsLemma = new HashMap<String, Lemma>();
    Map<Lemma, List<PosTag>> lemma2ItsTags = new HashMap<Lemma, List<PosTag>>();
    Map<Lemma, Frequency> lemma2ItsFreq = new HashMap<Lemma, Frequency>();
    Map<Lemma, List<Relation>> lemma2ItsRels = new HashMap<Lemma, List<Relation>>();

    LexiconLayersConnector() {
        super();
    }
}
