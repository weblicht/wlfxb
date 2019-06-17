/**
 * wlfxb - a library for creating and processing of TCF data streams.
 *
 * Copyright (C) University of Tübingen.
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
package eu.clarin.weblicht.wlfxb.tc.api;

import java.util.List;

/**
 * The <tt>TopologicalFieldLayer</tt> layer annotates tokens with topological field tags. 
 * Each tag element references a token, or sequence of tokens, and provides the 
 * tag string value. Tag values usually belong to some predefined standard tagset.
 * The layer specifies the name of the tagset via the tagset attribute.
 * 
 * @author Neele Witte
 */
public interface TopologicalFieldsLayer extends TextCorpusLayer {

    public String getTagset();

    public TopologicalField getTag(int index);

    public TopologicalField getTag(Token token);

    public Token[] getTokens(TopologicalField tag);

    public TopologicalField addTag(String tagString, Token tagToken);

    public TopologicalField addTag(String tagString, List<Token> tagTokens);
}
