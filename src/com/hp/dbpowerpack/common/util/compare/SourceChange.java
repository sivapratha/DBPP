package com.hp.dbpowerpack.common.util.compare;


/**
 * Change a block in the old file to a different block in the new file.
 */
public class SourceChange extends DBPPSourceChange {
	
	/**
	 * Instantiates a new source change.
	 *
	 * @param oldFileInfo the old file info
	 * @param newFileInfo the new file info
	 */
	public SourceChange(DBPPSourceInfo oldFileInfo, DBPPSourceInfo newFileInfo) {

		super(oldFileInfo, newFileInfo);
		setCommand("Change");
		setOldLines(oldFileInfo.nextBlock());
		setNewLines(newFileInfo.nextBlock());
		getOldLines().setReportable(true);
		getNewLines().setReportable(true);
		setColorFlag("GREEN");
	}
}
