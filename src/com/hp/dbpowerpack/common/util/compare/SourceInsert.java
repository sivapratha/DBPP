package com.hp.dbpowerpack.common.util.compare;


/**
 * Insert a block new lines into the old file.
 */
public class SourceInsert extends DBPPSourceChange {
	
	/**
	 * Instantiates a new source insert.
	 *
	 * @param oldFileInfo the old file info
	 * @param newFileInfo the new file info
	 */
	public SourceInsert(DBPPSourceInfo oldFileInfo, DBPPSourceInfo newFileInfo) {
		super(oldFileInfo, newFileInfo);
		setCommand("Insert before");
		setOldLines(oldFileInfo.getBlockAt(oldFileInfo.getLineNum()));
		setNewLines(newFileInfo.nextBlock());
		getNewLines().setReportable(true);
		setColorFlag("RED");
	}
}
