package de.tuebingen.uni.sfs.wlf1.tc.api;

import java.util.List;

public interface OrthographyLayer extends TextCorpusLayer {

	public int size();
	public OrthCorrection getCorrection(int index);
	public OrthCorrection getCorrection(Token token);
	public Token[] getTokens(OrthCorrection correction);
	public OrthCorrection addCorrection(String correctionString, Token correctedToken, CorrectionOperation operation);
	public OrthCorrection addCorrection(String correctionString, List<Token> correctedTokens, CorrectionOperation operation);
	
}
