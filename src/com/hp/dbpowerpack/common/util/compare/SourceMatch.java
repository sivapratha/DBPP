package com.hp.dbpowerpack.common.util.compare;


/**
 * A block of matching lines is either a "match" for no edit required, or "move"
 * for blocks that changed order.
 */
public class SourceMatch extends DBPPSourceChange {
	
	/**
	 * Instantiates a new source match.
	 *
	 * @param oldFileInfo the old file info
	 * @param newFileInfo the new file info
	 */
	public SourceMatch(DBPPSourceInfo oldFileInfo, DBPPSourceInfo newFileInfo) {
		super(oldFileInfo, newFileInfo);
		DBPPSourceLineInfo oldLineInfo = oldFileInfo.currentLineInfo();
		if (oldLineInfo.getNewLineNum() == newFileInfo.getLineNum()) {
			setCommand("Match");
			setNewLines(newFileInfo.nextBlock());
		} else {
			setCommand("Move");
			setNewLines(newFileInfo.getBlockAt(oldLineInfo.getNewLineNum()));
		}
		setOldLines(oldFileInfo.nextBlock());
		getOldLines().setReportable(true);
		getNewLines().setReportable(false);
	}
}
