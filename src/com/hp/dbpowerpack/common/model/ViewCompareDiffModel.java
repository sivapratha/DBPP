package com.hp.dbpowerpack.common.model;


/**
 * The Class ViewCompareDiffModel.
 */
public class ViewCompareDiffModel {

	/** The line num. */
	private int lineNum;

	/** The old line. */
	private String oldLine;

	/** The new line. */
	private String newLine;

	/** The color flag. */
	private String colorFlag;

	/**
	 * Gets the line num.
	 *
	 * @return the line num
	 */
	public int getLineNum() {
		return lineNum;
	}

	/**
	 * Sets the line num.
	 *
	 * @param lineNum the new line num
	 */
	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}

	/**
	 * Gets the old line.
	 *
	 * @return the old line
	 */
	public String getOldLine() {
		return oldLine;
	}

	/**
	 * Sets the old line.
	 *
	 * @param oldLine the new old line
	 */
	public void setOldLine(String oldLine) {
		this.oldLine = oldLine;
	}

	/**
	 * Gets the new line.
	 *
	 * @return the new line
	 */
	public String getNewLine() {
		return newLine;
	}

	/**
	 * Sets the new line.
	 *
	 * @param newLine the new new line
	 */
	public void setNewLine(String newLine) {
		this.newLine = newLine;
	}

	/**
	 * Gets the color flag.
	 *
	 * @return the color flag
	 */
	public String getColorFlag() {
		return colorFlag;
	}

	/**
	 * Sets the color flag.
	 *
	 * @param colorFlag the new color flag
	 */
	public void setColorFlag(String colorFlag) {
		this.colorFlag = colorFlag;
	}

}
