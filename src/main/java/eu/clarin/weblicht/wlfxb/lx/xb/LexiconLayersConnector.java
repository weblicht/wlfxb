/**
 * wlfxb - a library for creating and processing of TCF data streams.
 *
 * Copyright (C) Yana Panchenko.
 *
 * This file is part of wlfxb.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
