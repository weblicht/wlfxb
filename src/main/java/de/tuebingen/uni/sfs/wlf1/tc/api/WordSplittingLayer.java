/**
 *
 */
package de.tuebingen.uni.sfs.wlf1.tc.api;

/**
 * @author Yana Panchenko
 *
 */
public interface WordSplittingLayer {

    public String getType();

    public int size();

    public WordSplit getSplit(int index);

    public WordSplit getSplit(Token token);

    public Token getToken(WordSplit split);

    public WordSplit addSplit(Token token, int[] splitIndices);
}
