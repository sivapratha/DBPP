package com.hp.dbpowerpack.common.util.compare;


/**
 * A Symbol is a unique line of text. Symbol state tells if the symbol occurs in
 * OldOnly, NewOnly, UniqueMatch (Old & New exactly once each) or Other. Saves
 * the last line number from each file.
 */
public class Symbol {
	
	/** The Constant UNINITIALIZED. */
	public static final int UNINITIALIZED = -1;
	
	/** The Constant OLDONLY. */
	public static final int OLDONLY = 1;
	
	/** The Constant NEWONLY. */
	public static final int NEWONLY = 2;
	
	/** The Constant UNIQUEMATCH. */
	public static final int UNIQUEMATCH = 3;
	
	/** The Constant OTHER. */
	public static final int OTHER = 4;

	/** mState is a bitmap. */
	private int state = 0;
	
	/** The line num. */
	private int[] lineNum = new int[2];

	/**
	 * Instantiates a new symbol.
	 */
	public Symbol() {
	}

	/**
	 * Returns state after counting the line.
	 *
	 * @param aFile the a file
	 * @param aLineNum the a line num
	 * @return the int
	 */
	public int countLine(int aFile, int aLineNum) {
		lineNum[aFile] = aLineNum;
		return adjustState(aFile + 1);
	}

	/**
	 * Adusts and returns state.
	 *
	 * @param newState the new state
	 * @return the int
	 */
	private int adjustState(int newState) {
		if ((state & newState) == newState)
			state = OTHER;
		else
			state = Math.min(OTHER, state | newState);
		return state;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * Gets the line num.
	 *
	 * @param fileIx the file ix
	 * @return the line num
	 */
	public int getLineNum(int fileIx) {
		return lineNum[fileIx];
	}
}