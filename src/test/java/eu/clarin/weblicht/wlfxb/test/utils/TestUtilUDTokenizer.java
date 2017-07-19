/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.clarin.weblicht.wlfxb.test.utils;

import eu.clarin.weblicht.wlfxb.tc.api.Token;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author felahi
 */
public class TestUtilUDTokenizer {

    public static Map<String, List<String>> compositeTokens = new HashMap<String, List<String>>();

    private static String surFaceForm = null;
    private static List<String> parts = new ArrayList<String>();

    static {
        compositeTokens.put("im", Arrays.asList("in", "dem"));
        compositeTokens.put("Dann", Arrays.asList("Dan", "n"));
        
    }

    public static boolean isCompositeToken(String tokenString) {

        if (compositeTokens.containsKey(tokenString)) {
            surFaceForm = tokenString;
            parts = compositeTokens.get(tokenString);
            return true;
        }
        return false;
    }

    public static String getSurFaceForm() {
        return surFaceForm;
    }

    public static List<String> getParts() {
        return parts;
    }
    

}
