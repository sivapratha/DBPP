package com.hp.dbpowerpack.Model;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class TableFieldMappingModel.
 */
public class TableFieldMappingModel {

	/** The table name. */
	private String tableName;

	/** The field list. */
	private List<DBTableStructModel> fieldList;

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
	 * Gets the field list.
	 * 
	 * @return the field list
	 */
	public List<DBTableStructModel> getFieldList() {
		if (fieldList == null) {
			fieldList = new ArrayList<DBTableStructModel>();
		}
		return fieldList;
	}

	/**
	 * Sets the field list.
	 * 
	 * @param fieldList
	 *            the new field list
	 */
	public void setFieldList(List<DBTableStructModel> fieldList) {
		this.fieldList = fieldList;
	}
}
