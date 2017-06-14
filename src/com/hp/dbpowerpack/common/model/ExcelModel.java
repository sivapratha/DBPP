package com.hp.dbpowerpack.common.model;

import java.util.List;


/**
 * The Class ExcelModel.
 */
public class ExcelModel {

	/** The file name. */
	private String fileName;

	/** The sheet name. */
	private String sheetName;

	/** The obj lst. */
	private List<Object> objLst;

	/** The object class. */
	private String objectClass;

	/** The is color flag. */
	private boolean isColorFlag;

	/** The is db header. */
	private boolean isDBHeader;

	/** The db name list. */
	private List<String> headerList;

	/** The column count. */
	private int columnCount;

	/** The db name list. */
	private List<String> dbNameList;

	/** The is db name header. */
	private boolean isDbNameHeader;

	/** The field name list. */
	private List<String> fieldNameList;

	/**
	 * Gets the field name list.
	 * 
	 * @return the field name list
	 */
	public List<String> getFieldNameList() {
		return fieldNameList;
	}

	/**
	 * Sets the field name list.
	 * 
	 * @param fieldNameList
	 *            the new field name list
	 */
	public void setFieldNameList(List<String> fieldNameList) {
		this.fieldNameList = fieldNameList;
	}

	/**
	 * Checks if is db name header.
	 * 
	 * @return true, if is db name header
	 */
	public boolean isDbNameHeader() {
		return isDbNameHeader;
	}

	/**
	 * Sets the db name header.
	 * 
	 * @param isDbNameHeader
	 *            the new db name header
	 */
	public void setDbNameHeader(boolean isDbNameHeader) {
		this.isDbNameHeader = isDbNameHeader;
	}

	/**
	 * Gets the db name list.
	 * 
	 * @return the db name list
	 */
	public List<String> getDbNameList() {
		return dbNameList;
	}

	/**
	 * Sets the db name list.
	 * 
	 * @param dbNameList
	 *            the new db name list
	 */
	public void setDbNameList(List<String> dbNameList) {
		this.dbNameList = dbNameList;
	}

	/**
	 * Gets the header list.
	 * 
	 * @return the header list
	 */
	public List<String> getHeaderList() {
		return headerList;
	}

	/**
	 * Sets the header list.
	 * 
	 * @param headerList
	 *            the new header list
	 */
	public void setHeaderList(List<String> headerList) {
		this.headerList = headerList;
	}

	/**
	 * Gets the column count.
	 * 
	 * @return the column count
	 */
	public int getColumnCount() {
		return columnCount;
	}

	/**
	 * Sets the column count.
	 * 
	 * @param columnCount
	 *            the new column count
	 */
	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	/**
	 * Checks if is dB header.
	 * 
	 * @return true, if is dB header
	 */
	public boolean isDBHeader() {
		return isDBHeader;
	}

	/**
	 * Sets the dB header.
	 * 
	 * @param isDBHeader
	 *            the new dB header
	 */
	public void setDBHeader(boolean isDBHeader) {
		this.isDBHeader = isDBHeader;
	}

	/**
	 * Checks if is color flag.
	 * 
	 * @return true, if is color flag
	 */
	public boolean isColorFlag() {
		return isColorFlag;
	}

	/**
	 * Sets the color flag.
	 * 
	 * @param isColorFlag
	 *            the new color flag
	 */
	public void setColorFlag(boolean isColorFlag) {
		this.isColorFlag = isColorFlag;
	}

	/**
	 * Gets the file name.
	 * 
	 * @return the file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the file name.
	 * 
	 * @param fileName
	 *            the new file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Gets the sheet name.
	 * 
	 * @return the sheet name
	 */
	public String getSheetName() {
		return sheetName;
	}

	/**
	 * Sets the sheet name.
	 * 
	 * @param sheetName
	 *            the new sheet name
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	/**
	 * Gets the obj lst.
	 * 
	 * @return the obj lst
	 */
	public List<Object> getObjLst() {
		return objLst;
	}

	/**
	 * Sets the obj lst.
	 * 
	 * @param objLst
	 *            the new obj lst
	 */
	public void setObjLst(List<Object> objLst) {
		this.objLst = objLst;
	}

	/**
	 * Gets the object class.
	 * 
	 * @return the object class
	 */
	public String getObjectClass() {
		return objectClass;
	}

	/**
	 * Sets the object class.
	 * 
	 * @param objectClass
	 *            the new object class
	 */
	public void setObjectClass(String objectClass) {
		this.objectClass = objectClass;
	}

}
