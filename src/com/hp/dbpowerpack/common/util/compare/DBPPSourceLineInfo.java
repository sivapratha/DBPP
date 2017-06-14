package com.hp.dbpowerpack.common.util.compare;


/**
 * One line in one file. For UniqueMatch lines, holds a line number pointer into
 * the other file.
 */
public class DBPPSourceLineInfo {
	
	/** The Constant EOF. */
	public static final int EOF = -1;
	
	/** The Constant OLDONLY. */
	public static final int OLDONLY = 1;
	
	/** The Constant NEWONLY. */
	public static final int NEWONLY = 2;
	
	/** The Constant UNIQUEMATCH. */
	public static final int UNIQUEMATCH = 3;
	
	/** The Constant OTHER. */
	public static final int OTHER = 4;

	/** The line status. */
	private int lineStatus = 0;
	
	/** The old line num. */
	private int oldLineNum = 0;
	
	/** The new line num. */
	private int newLineNum = 0;
	
	/** The block num. */
	private int blockNum = 0;

	/**
	 * Gets the line status.
	 *
	 * @return the lineStatus
	 */
	public int getLineStatus() {
		return lineStatus;
	}

	/**
	 * Sets the line status.
	 *
	 * @param lineStatus the lineStatus to set
	 */
	public void setLineStatus(int lineStatus) {
		this.lineStatus = lineStatus;
	}

	/**
	 * Gets the old line num.
	 *
	 * @return the oldLineNum
	 */
	public int getOldLineNum() {
		return oldLineNum;
	}

	/**
	 * Sets the old line num.
	 *
	 * @param oldLineNum the oldLineNum to set
	 */
	public void setOldLineNum(int oldLineNum) {
		this.oldLineNum = oldLineNum;
	}

	/**
	 * Gets the new line num.
	 *
	 * @return the newLineNum
	 */
	public int getNewLineNum() {
		return newLineNum;
	}

	/**
	 * Sets the new line num.
	 *
	 * @param newLineNum the newLineNum to set
	 */
	public void setNewLineNum(int newLineNum) {
		this.newLineNum = newLineNum;
	}

	/**
	 * Gets the block num.
	 *
	 * @return the blockNum
	 */
	public int getBlockNum() {
		return blockNum;
	}

	/**
	 * Sets the block num.
	 *
	 * @param blockNum the blockNum to set
	 */
	public void setBlockNum(int blockNum) {
		this.blockNum = blockNum;
	}

	/**
	 * Instantiates a new dBPP source line info.
	 */
	public DBPPSourceLineInfo() {
		this(0, 0, 0, 0);
	}

	/**
	 * Instantiates a new dBPP source line info.
	 *
	 * @param aLineStatus the a line status
	 * @param aOldLineNum the a old line num
	 * @param aNewLineNum the a new line num
	 * @param aBlockNum the a block num
	 */
	public DBPPSourceLineInfo(int aLineStatus, int aOldLineNum,
			int aNewLineNum, int aBlockNum) {
		lineStatus = aLineStatus;
		oldLineNum = aOldLineNum;
		newLineNum = aNewLineNum;
		blockNum = aBlockNum;
	}

	/**
	 * Checks if is eOF.
	 *
	 * @return true, if is eOF
	 */
	public boolean isEOF() {
		return (lineStatus == EOF);
	}

	/**
	 * Checks if is match.
	 *
	 * @return true, if is match
	 */
	public boolean isMatch() {
		return (lineStatus == UNIQUEMATCH);
	}

	/**
	 * Checks if is old only.
	 *
	 * @return true, if is old only
	 */
	public boolean isOldOnly() {
		return (lineStatus == OLDONLY || lineStatus == OTHER);
	}

	/**
	 * Checks if is new only.
	 *
	 * @return true, if is new only
	 */
	public boolean isNewOnly() {
		return (lineStatus == NEWONLY || lineStatus == OTHER);
	}

	/**
	 * Sets the block number.
	 *
	 * @param aBlockNum the new block number
	 */
	public void setBlockNumber(int aBlockNum) {
		blockNum = aBlockNum;
		lineStatus = UNIQUEMATCH;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Status=" + lineStatus + " OldNum=" + oldLineNum + " NewNum="
				+ newLineNum + " Block=" + blockNum;
	}
}