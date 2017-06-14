package com.hp.dbpowerpack.common.util;

import java.util.List;

import com.hp.dbpowerpack.common.model.ViewCompareDiffModel;
import com.hp.dbpowerpack.common.util.compare.DBPPCompare;


/**
 * The Class DBPPCompareUtil.
 */
public class DBPPCompareUtil {

	/**
	 * Compare two string arrays.
	 *
	 * @param oldLines the old lines
	 * @param newLines the new lines
	 * @return the list
	 */
	public static List<ViewCompareDiffModel> compare(String[] oldLines,
			String[] newLines) {
		DBPPCompare dbppCompare = new DBPPCompare();
		List<ViewCompareDiffModel> compareDiffList = dbppCompare.compare(
				oldLines, newLines);
		return compareDiffList;

	}
}
