package com.hp.dbpowerpack.Model;


/**
 * The Class DBTableStructModel.
 */
public class DBTableStructModel {

	/** The table name. */
	String tableName;

	/** The field name. */
	String fieldName;

	/** The data type. */
	String dataType;

	/** The data length. */
	Double dataLength;

	/** The row cnt. */
	Double rowCnt;

	/**
	 * Gets the data length.
	 * 
	 * @return the data length
	 */
	public Double getDataLength() {
		return dataLength;
	}

	/**
	 * Sets the data length.
	 * 
	 * @param dataLength
	 *            the new data length
	 */
	public void setDataLength(Double dataLength) {
		this.dataLength = dataLength;
	}

	/**
	 * Gets the row cnt.
	 * 
	 * @return the row cnt
	 */
	public Double getRowCnt() {
		return rowCnt;
	}

	/**
	 * Sets the row cnt.
	 * 
	 * @param rowCnt
	 *            the new row cnt
	 */
	public void setRowCnt(Double rowCnt) {
		this.rowCnt = rowCnt;
	}

	/**
	 * Gets the table name.
	 * 
	 * @return the table name
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * Sets the table name.
	 * 
	 * @param tableName
	 *            the new table name
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * Gets the field name.
	 * 
	 * @return the field name
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Sets the field name.
	 * 
	 * @param fieldName
	 *            the new field name
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * Gets the data type.
	 * 
	 * @return the data type
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * Sets the data type.
	 * 
	 * @param dataType
	 *            the new data type
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

}
