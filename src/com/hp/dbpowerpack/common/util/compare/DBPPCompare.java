package com.hp.dbpowerpack.common.util.compare;

import java.util.ArrayList;
import java.util.List;

import com.hp.dbpowerpack.common.model.ViewCompareDiffModel;


/**
 * The Class DBPPCompare.
 */
public class DBPPCompare {

	/** The Constant OLD. */
	public static final int OLD = 0;
	
	/** The Constant NEW. */
	public static final int NEW = 1;

	/** The symbols. */
	private SymbolCollection symbols;
	
	/** The old file info. */
	private DBPPSourceInfo oldFileInfo;
	
	/** The new file info. */
	private DBPPSourceInfo newFileInfo;

	/**
	 * Compare two string arrays.
	 *
	 * @param oldLines the old lines
	 * @param newLines the new lines
	 * @return the list
	 */
	public List<ViewCompareDiffModel> compare(String[] oldLines,
			String[] newLines) {
		createFileInfo(oldLines, newLines);
		createSymbols();
		createLineInfo();
		stretchMatches(oldFileInfo);
		DBPPChangesList report = new DBPPChangesList(oldFileInfo, newFileInfo);
		List<ViewCompareDiffModel> compareDiffList = printFileInfo(report);
		return compareDiffList;
	}

	/**
	 * Creates the file info.
	 *
	 * @param oldLines the old lines
	 * @param newLines the new lines
	 */
	private void createFileInfo(String[] oldLines, String[] newLines) {
		oldFileInfo = new DBPPSourceInfo(oldLines);
		newFileInfo = new DBPPSourceInfo(newLines);
	}

	/**
	 * Create a symbol for each unique string.
	 */
	private void createSymbols() {
		symbols = new SymbolCollection();
		createSymbols(oldFileInfo, OLD);
		createSymbols(newFileInfo, NEW);
	}

	/**
	 * Creates the symbols.
	 *
	 * @param fileInfo the file info
	 * @param fileIx the file ix
	 */
	private void createSymbols(DBPPSourceInfo fileInfo, int fileIx) {
		for (int line = 0; line < fileInfo.getLength(); line++)
			symbols.registerSymbol(fileIx, fileInfo.getLines()[line], line);
	}

	/** Initial line status is symbol status. Cross link any UniqueMatch lines */
	private void createLineInfo() {
		createLineInfo(oldFileInfo);
		createLineInfo(newFileInfo);
	}

	/**
	 * Creates the line info.
	 *
	 * @param fileInfo the file info
	 */
	private void createLineInfo(DBPPSourceInfo fileInfo) {
		for (int line = 0; line < fileInfo.getLength(); line++) {
			DBPPSourceLineInfo lineInfo = new DBPPSourceLineInfo();
			fileInfo.getLineInfo()[line] = lineInfo;
			Symbol symbol = symbols.getSymbolFor(fileInfo.getLines()[line]);
			lineInfo.setLineStatus(symbol.getState());
			if (lineInfo.isMatch()) {
				lineInfo.setOldLineNum(symbol.getLineNum(OLD));
				lineInfo.setNewLineNum(symbol.getLineNum(NEW));
			}
		}
	}

	/**
	 * Stretch each unique-match in the FileInfo.
	 *
	 * @param fileInfo the file info
	 */
	private void stretchMatches(DBPPSourceInfo fileInfo) {
		int FORWARD = 1;
		int BACKWARD = -1;
		int lBlockNum = 0;
		for (int line = 0; fileInfo.isValidLineNum(line); line++) {
			DBPPSourceLineInfo lineInfo = fileInfo.getLineInfo()[line];
			if ((lineInfo.isMatch()) && (lineInfo.getBlockNum() == 0)) {
				lBlockNum++;
				stretchOneMatch(lBlockNum, lineInfo.getOldLineNum(),
						lineInfo.getNewLineNum(), FORWARD);
				stretchOneMatch(lBlockNum, lineInfo.getOldLineNum(),
						lineInfo.getNewLineNum(), BACKWARD);
			}
		}
	}

	/**
	 * Find more matching lines before or after a unique match and mark them as
	 * unique match, too. If unique match lines are separated by matching but
	 * non-unique lines this will merge them all into one block.
	 *
	 * @param blockNum the block num
	 * @param oldLineNum the old line num
	 * @param newLineNum the new line num
	 * @param whichWay the which way
	 */
	private void stretchOneMatch(int blockNum, int oldLineNum, int newLineNum,
			int whichWay) {
		int lOldLineNum = oldLineNum;
		int lNewLineNum = newLineNum;
		while (true) {
			oldFileInfo.setBlockNumber(lOldLineNum, blockNum);
			newFileInfo.setBlockNumber(lNewLineNum, blockNum);
			oldFileInfo.getLineInfo()[lOldLineNum].setNewLineNum(lNewLineNum);
			newFileInfo.getLineInfo()[lNewLineNum].setOldLineNum(lOldLineNum);

			lOldLineNum += whichWay;
			lNewLineNum += whichWay;
			if (!(oldFileInfo.isValidLineNum(lOldLineNum)
					&& newFileInfo.isValidLineNum(lNewLineNum)
					&& oldFileInfo.getLines()[lOldLineNum] != null && oldFileInfo
					.getLines()[lOldLineNum]
					.equals(newFileInfo.getLines()[lNewLineNum])))
				break;
		}
	}

	/**
	 * Prints the file info.
	 *
	 * @param dbppChangesList the dbpp changes list
	 * @return the list
	 */
	private List<ViewCompareDiffModel> printFileInfo(
			DBPPChangesList dbppChangesList) {
		List<ViewCompareDiffModel> compareDiffList = new ArrayList<ViewCompareDiffModel>();
		int lineNbr = 0;
		for (int i = 0; i < dbppChangesList.size(); i++) {
			DBPPSourceChange command = (DBPPSourceChange) dbppChangesList
					.get(i);
			DBPPSourceLineBlock newLineBlock = command.getNewLines();
			DBPPSourceLineBlock oldLineBlock = command.getOldLines();
			if ("GREEN".equals(command.getColorFlag())) {
				String oldLines[] = oldLineBlock.getLines();
				String newLines[] = newLineBlock.getLines();
				int count = 0;
				if (oldLines.length >= newLines.length) {
					count = oldLines.length;
				} else {
					count = newLines.length;
				}
				for (int j = 0; j < count; j++) {
					ViewCompareDiffModel model = new ViewCompareDiffModel();
					if (j < oldLines.length) {
						model.setOldLine(oldLines[j]);
					} else {
						model.setOldLine(null);
					}
					if (j < newLines.length) {
						model.setNewLine(newLines[j]);
					} else {
						model.setNewLine(null);
					}

					lineNbr = lineNbr + 1;
					model.setLineNum(lineNbr);
					model.setColorFlag("GREEN");
					compareDiffList.add(model);

				}
			} else if ("BLUE".equals(command.getColorFlag())) {
				String oldLines[] = oldLineBlock.getLines();
				for (int j = 0; j < oldLines.length; j++) {
					ViewCompareDiffModel model = new ViewCompareDiffModel();
					model.setNewLine(null);
					if (j < oldLines.length) {
						model.setOldLine(oldLines[j]);
					} else {
						model.setOldLine(null);
					}
					lineNbr = lineNbr + 1;
					model.setLineNum(lineNbr);
					model.setColorFlag("BLUE");
					compareDiffList.add(model);

				}

			} else if ("RED".equals(command.getColorFlag())) {
				String newLines[] = newLineBlock.getLines();
				for (int j = 0; j < newLines.length; j++) {
					ViewCompareDiffModel model = new ViewCompareDiffModel();
					if (j < newLines.length) {
						model.setNewLine(newLines[j]);
					} else {
						model.setNewLine(null);
					}
					model.setOldLine(null);
					lineNbr = lineNbr + 1;
					model.setLineNum(lineNbr);
					model.setColorFlag("RED");
					compareDiffList.add(model);

				}

			} else if ("NONE".equals(command.getColorFlag())) {
				String oldLines[] = oldLineBlock.getLines();
				String newLines[] = newLineBlock.getLines();
				for (int j = 0; j < oldLines.length; j++) {
					ViewCompareDiffModel model = new ViewCompareDiffModel();
					if (j < newLines.length) {
						model.setNewLine(newLines[j]);
					} else {
						model.setNewLine(null);
					}

					if (j < oldLines.length) {
						model.setOldLine(oldLines[j]);
					} else {
						model.setOldLine(null);
					}

					lineNbr = lineNbr + 1;
					model.setLineNum(lineNbr);
					model.setColorFlag("NONE");
					compareDiffList.add(model);

				}

			}

		}

		return compareDiffList;
	}

}
