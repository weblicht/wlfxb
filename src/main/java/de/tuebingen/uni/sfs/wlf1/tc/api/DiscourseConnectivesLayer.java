package de.tuebingen.uni.sfs.wlf1.tc.api;

import java.util.List;

public interface DiscourseConnectivesLayer extends TextCorpusLayer {

	public String getTypesTagset();
	public int size();
	public DiscourseConnective getConnective(int index);
	public DiscourseConnective getConnective(Token token);
	public Token[] getTokens(DiscourseConnective connective);
	public DiscourseConnective addConnective(List<Token> tokens);
	public DiscourseConnective addConnective(List<Token> tokens, String semanticType);
	
}
