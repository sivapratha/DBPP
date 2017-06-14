package com.hp.dbpowerpack.common.util.compare;

import java.util.HashMap;
import java.util.Map;


/**
 * Manages a set of Symbols. Current implementation is a HashMap.
 */
public class SymbolCollection {
	
	/** The symbols. */
	private Map<String, Symbol> symbols = new HashMap<String, Symbol>();

	/**
	 * Gets the symbol for.
	 *
	 * @param line the line
	 * @return the symbol for
	 */
	public Symbol getSymbolFor(String line) {
		Symbol symbol = (Symbol) symbols.get(line);
		if (null == symbol) {
			symbol = new Symbol();
			symbols.put(line, symbol);
		}
		return symbol;
	}

	/**
	 * Register symbol.
	 *
	 * @param fileIx the file ix
	 * @param line the line
	 * @param lineNum the line num
	 * @return the int
	 */
	public int registerSymbol(int fileIx, String line, int lineNum) {
		return getSymbolFor(line).countLine(fileIx, lineNum);
	}

	/**
	 * Gets the state of.
	 *
	 * @param line the line
	 * @return the state of
	 */
	public int getStateOf(String line) {
		return getSymbolFor(line).getState();
	}
}
