package com.hp.dbpowerpack.common.util.compare;


/**
 * Append a block of lines to the end of the old file.
 */
public class SourceAppend extends DBPPSourceChange {
	
	/**
	 * Instantiates a new source append.
	 *
	 * @param oldFileInfo the old file info
	 * @param newFileInfo the new file info
	 */
	public SourceAppend(DBPPSourceInfo oldFileInfo, DBPPSourceInfo newFileInfo) {
		super(oldFileInfo, newFileInfo);
		setCommand("Append");
		setNewLines(newFileInfo.nextBlock());
		getNewLines().setReportable(true);
		setColorFlag("RED");
	}
}
