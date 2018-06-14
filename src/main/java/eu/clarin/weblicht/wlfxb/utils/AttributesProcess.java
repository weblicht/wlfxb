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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.clarin.weblicht.wlfxb.utils;

import java.util.LinkedHashMap;
import javax.xml.namespace.QName;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public class AttributesProcess {

    public static LinkedHashMap<String, String> retrieveAtrributes(LinkedHashMap<QName, String> qnameAttributes) {
        LinkedHashMap<String, String> extraAttributes = new LinkedHashMap<String, String>();
        for (QName qName : qnameAttributes.keySet()) {
            extraAttributes.put(qName.toString(), qnameAttributes.get(qName).toString());
        }
        return extraAttributes;
    }
}
