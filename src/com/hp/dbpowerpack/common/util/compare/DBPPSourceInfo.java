package com.hp.dbpowerpack.common.util.compare;


/**
 * One of the two files being compared. lineNum supports nextBlock something
 * like an iterator.
 */
public class DBPPSourceInfo {
	
	/** The lines. */
	private String[] lines;

	/** The line info. */
	private DBPPSourceLineInfo[] lineInfo;
	
	/** The length. */
	private int length;
	
	/** The line num. */
	private int lineNum;
	
	/** The Constant EOF. */
	private static final DBPPSourceLineInfo EOF = new DBPPSourceLineInfo(-1,
			-1, -1, -1);

	/**
	 * Instantiates a new dBPP source info.
	 *
	 * @param lines the lines
	 */
	public DBPPSourceInfo(String[] lines) {
		this.lines = lines;
		length = lines.length;
		lineInfo = new DBPPSourceLineInfo[length];
	}

	/**
	 * Gets the lines.
	 *
	 * @return the lines
	 */
	public String[] getLines() {
		return lines;
	}

	/**
	 * Sets the lines.
	 *
	 * @param lines the lines to set
	 */
	public void setLines(String[] lines) {
		this.lines = lines;
	}

	/**
	 * Gets the line info.
	 *
	 * @return the lineInfo
	 */
	public DBPPSourceLineInfo[] getLineInfo() {
		return lineInfo;
	}

	/**
	 * Sets the line info.
	 *
	 * @param lineInfo the lineInfo to set
	 */
	public void setLineInfo(DBPPSourceLineInfo[] lineInfo) {
		this.lineInfo = lineInfo;
	}

	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Sets the length.
	 *
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * Gets the line num.
	 *
	 * @return the lineNum
	 */
	public int getLineNum() {
		return lineNum;
	}

	/**
	 * Sets the line num.
	 *
	 * @param lineNum the lineNum to set
	 */
	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}

	/**
	 * Current line info.
	 *
	 * @return the dBPP source line info
	 */
	public DBPPSourceLineInfo currentLineInfo() {
		return lineInfoAt(lineNum);
	}

	/**
	 * Line info at.
	 *
	 * @param lineNum the line num
	 * @return the dBPP source line info
	 */
	public DBPPSourceLineInfo lineInfoAt(int lineNum) {
		if (lineNum >= lines.length)
			return EOF;
		else
			return lineInfo[lineNum];
	}

	/**
	 * Next block.
	 *
	 * @return the dBPP source line block
	 */
	public DBPPSourceLineBlock nextBlock() {
		DBPPSourceLineBlock lineBlock = getBlockAt(lineNum);
		if (null != lineBlock)
			lineNum += lineBlock.getLines().length;
		return lineBlock;
	}

	/**
	 * Gets the block at.
	 *
	 * @param lineNum the line num
	 * @return the block at
	 */
	public DBPPSourceLineBlock getBlockAt(int lineNum) {
		if (lineNum >= lines.length)
			return null;
		int fromLineNum = lineNum;
		int blockNum = lineInfo[lineNum].getBlockNum();
		while (blockNum == lineInfoAt(lineNum).getBlockNum()) {
			lineNum++;
		}
		int thruLineNum = lineNum - 1;
		DBPPSourceLineBlock lBlock = new DBPPSourceLineBlock(lines,
				fromLineNum, thruLineNum);
		return lBlock;
	}

	/**
	 * Sets the block number.
	 *
	 * @param lineNum the line num
	 * @param blockNum the block num
	 */
	public void setBlockNumber(int lineNum, int blockNum) {
		lineInfo[lineNum].setBlockNumber(blockNum);
	}

	/**
	 * Checks if is valid line num.
	 *
	 * @param lineNum the line num
	 * @return true, if is valid line num
	 */
	public boolean isValidLineNum(int lineNum) {
		return ((lineNum >= 0) && (lineNum < lines.length));
	}
}