package com.hp.dbpowerpack.common.util.compare;

import java.util.ArrayList;


/**
 * Creates and holds a List of edit commands that can be used to tranform Old
 * into New. The constructor does all the work. Use any methods of the ancestor
 * ArrayList to access the commands. getCommand is a get wrapped to do the
 * casting to EditCommand.
 */
public class DBPPChangesList extends ArrayList<DBPPSourceChange> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2448790097506967482L;

	/**
	 * Create a Report (list of edit commands) from old and new file info.
	 *
	 * @param oldFileInfo the old file info
	 * @param newFileInfo the new file info
	 */
	public DBPPChangesList(DBPPSourceInfo oldFileInfo,
			DBPPSourceInfo newFileInfo) {
		int i = 0;
		while (true) {

			DBPPSourceLineInfo oldLineInfo = oldFileInfo.currentLineInfo();
			DBPPSourceLineInfo newLineInfo = newFileInfo.currentLineInfo();
			if (oldLineInfo.isEOF() && newLineInfo.isEOF())
				break;

			else if (oldLineInfo.isEOF() && newLineInfo.isNewOnly())
				this.add(new SourceAppend(oldFileInfo, newFileInfo));

			else if (oldLineInfo.isOldOnly() && newLineInfo.isNewOnly())
				this.add(new SourceChange(oldFileInfo, newFileInfo));

			else if (newLineInfo.isNewOnly())
				this.add(new SourceInsert(oldFileInfo, newFileInfo));

			else if (oldLineInfo.isOldOnly())
				this.add(new SourceDelete(oldFileInfo, newFileInfo));

			else if (oldLineInfo.isMatch())
				this.add(new SourceMatch(oldFileInfo, newFileInfo));

			else if (newLineInfo.isMatch())
				newFileInfo.nextBlock(); // discard

			i++;
		}
	}

	/**
	 * Gets the command.
	 *
	 * @param lineNum the line num
	 * @return the command
	 */
	public DBPPSourceChange getCommand(int lineNum) {
		return (DBPPSourceChange) super.get(lineNum);
	}
}
