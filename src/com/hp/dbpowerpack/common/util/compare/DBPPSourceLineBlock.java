package com.hp.dbpowerpack.common.util.compare;


/**
 * A contiguous block of one or more lines with the same state, eg match or
 * other.
 */
public class DBPPSourceLineBlock {
	
	/** The from line num. */
	private int fromLineNum;
	
	/** The thru line num. */
	private int thruLineNum;
	
	/** The lines. */
	private String[] lines;
	
	/** The reportable. */
	private boolean reportable = false;

	/**
	 * Gets the from line num.
	 *
	 * @return the fromLineNum
	 */
	public int getFromLineNum() {
		return fromLineNum;
	}

	/**
	 * Sets the from line num.
	 *
	 * @param fromLineNum the fromLineNum to set
	 */
	public void setFromLineNum(int fromLineNum) {
		this.fromLineNum = fromLineNum;
	}

	/**
	 * Gets the thru line num.
	 *
	 * @return the thruLineNum
	 */
	public int getThruLineNum() {
		return thruLineNum;
	}

	/**
	 * Sets the thru line num.
	 *
	 * @param thruLineNum the thruLineNum to set
	 */
	public void setThruLineNum(int thruLineNum) {
		this.thruLineNum = thruLineNum;
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
	 * Checks if is reportable.
	 *
	 * @return the reportable
	 */
	public boolean isReportable() {
		return reportable;
	}

	/**
	 * Sets the reportable.
	 *
	 * @param reportable the reportable to set
	 */
	public void setReportable(boolean reportable) {
		this.reportable = reportable;
	}

	/**
	 * Instantiates a new dBPP source line block.
	 *
	 * @param sourceLines the source lines
	 * @param fromLineNum the from line num
	 * @param thruLineNum the thru line num
	 */
	public DBPPSourceLineBlock(String[] sourceLines, int fromLineNum,
			int thruLineNum) {
		this.fromLineNum = fromLineNum;
		this.thruLineNum = thruLineNum;
		lines = new String[thruLineNum - fromLineNum + 1];
		System.arraycopy(sourceLines, fromLineNum, lines, 0, lines.length);
	}
}
