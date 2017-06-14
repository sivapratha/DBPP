package com.hp.dbpowerpack.common.util.compare;


/**
 * Can be used to transform Old into New. Concrete classes fill in the command
 * name and old and new lines affected as appropriate.
 */
public abstract class DBPPSourceChange {
	
	/** The command. */
	private String command = "undefined";
	
	/** The old lines. */
	private DBPPSourceLineBlock oldLines = null;
	
	/** The new lines. */
	private DBPPSourceLineBlock newLines = null;
	
	/** The color flag. */
	private String colorFlag = "NONE";

	/**
	 * Instantiates a new dBPP source change.
	 *
	 * @param oldFileInfo the old file info
	 * @param newFileInfo the new file info
	 */
	public DBPPSourceChange(DBPPSourceInfo oldFileInfo,
			DBPPSourceInfo newFileInfo) {
	}

	/**
	 * Gets the command.
	 *
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * Sets the command.
	 *
	 * @param command the command to set
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * Gets the old lines.
	 *
	 * @return the oldLines
	 */
	public DBPPSourceLineBlock getOldLines() {
		return oldLines;
	}

	/**
	 * Sets the old lines.
	 *
	 * @param oldLines the oldLines to set
	 */
	public void setOldLines(DBPPSourceLineBlock oldLines) {
		this.oldLines = oldLines;
	}

	/**
	 * Gets the new lines.
	 *
	 * @return the newLines
	 */
	public DBPPSourceLineBlock getNewLines() {
		return newLines;
	}

	/**
	 * Sets the new lines.
	 *
	 * @param newLines the newLines to set
	 */
	public void setNewLines(DBPPSourceLineBlock newLines) {
		this.newLines = newLines;
	}

	/**
	 * Gets the color flag.
	 *
	 * @return the colorFlag
	 */
	public String getColorFlag() {
		return colorFlag;
	}

	/**
	 * Sets the color flag.
	 *
	 * @param colorFlag the colorFlag to set
	 */
	public void setColorFlag(String colorFlag) {
		this.colorFlag = colorFlag;
	}

}