/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.clarin.weblicht.wlfxb.tc.api;

import java.util.List;
import java.util.Set;

/**
 *
 * @author felahi
 */
public interface ChunkLayer extends TextCorpusLayer {

    public String getType();

    public Chunk getChunk(int index);

    public Chunk getChunk(Token token);

    public List<Chunk> getChunks(Token token);

    public Token[] getTokens(Chunk chunk);

    public Chunk addChunk(String ChunkType, List<Token> ChunkTokens);

    public Set<String> getFoundTypes();

    //public Chunk addChunk(String ChunkType, Token ChunkToken);
    //public Chunk addChunk(Set<String> ChunkTypes, Token ChunkToken);
    //public Chunk addChunk(Set<String> ChunkTypes, List<Token> ChunkTokens);
}
