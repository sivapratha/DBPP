package com.hp.dbpowerpack.common.util.compare;


/**
 * Delete a block from the old file.
 */
public class SourceDelete extends DBPPSourceChange {
	
	/**
	 * Instantiates a new source delete.
	 *
	 * @param oldFileInfo the old file info
	 * @param newFileInfo the new file info
	 */
	public SourceDelete(DBPPSourceInfo oldFileInfo, DBPPSourceInfo newFileInfo) {
		super(oldFileInfo, newFileInfo);
		setCommand("Delete");
		setOldLines(oldFileInfo.nextBlock());
		getOldLines().setReportable(true);
		setColorFlag("BLUE");
	}
}
