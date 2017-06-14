package com.hp.dbpowerpack.Model;


/**
 * The Class DBTableNameModel.
 */
public class DBTableNameModel implements Comparable<DBTableNameModel> {

	/** The db table name1. */
	String dbTableName1;

	/** The db table name2. */
	String dbTableName2;

	/** The db field name1. */
	String dbFieldName1;

	/** The db field name2. */
	String dbFieldName2;

	/** The db data type1. */
	String dbDataType1;

	/** The db data type2. */
	String dbDataType2;

	/** The db data length1. */
	String dbDataLength1;

	/** The db data length2. */
	String dbDataLength2;

	/** The object type. */
	String objectType;

	/** The db name. */
	String dbName1;

	/** The db name. */
	String dbName2;

	/**
	 * Gets the db name.
	 * 
	 * @return the db name
	 */
	public String getDbName1() {
		return dbName1;
	}

	/**
	 * Sets the db name.
	 *
	 * @param dbName1 the new db name1
	 */
	public void setDbName1(String dbName1) {
		this.dbName1 = dbName1;
	}

	/**
	 * Gets the db name.
	 * 
	 * @return the db name
	 */
	public String getDbName2() {
		return dbName2;
	}

	/**
	 * Sets the db name.
	 *
	 * @param dbName2 the new db name2
	 */
	public void setDbName2(String dbName2) {
		this.dbName2 = dbName2;
	}

	/**
	 * Gets the object type.
	 * 
	 * @return the object type
	 */
	public String getObjectType() {
		return objectType;
	}

	/**
	 * Sets the object type.
	 * 
	 * @param objectType
	 *            the new object type
	 */
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	/**
	 * Gets the db field name1.
	 * 
	 * @return the db field name1
	 */
	public String getDbFieldName1() {
		return dbFieldName1;
	}

	/**
	 * Sets the db field name1.
	 * 
	 * @param dbFieldName1
	 *            the new db field name1
	 */
	public void setDbFieldName1(String dbFieldName1) {
		this.dbFieldName1 = dbFieldName1;
	}

	/**
	 * Gets the db field name2.
	 * 
	 * @return the db field name2
	 */
	public String getDbFieldName2() {
		return dbFieldName2;
	}

	/**
	 * Sets the db field name2.
	 * 
	 * @param dbFieldName2
	 *            the new db field name2
	 */
	public void setDbFieldName2(String dbFieldName2) {
		this.dbFieldName2 = dbFieldName2;
	}

	/**
	 * Gets the db data type1.
	 * 
	 * @return the db data type1
	 */
	public String getDbDataType1() {
		return dbDataType1;
	}

	/**
	 * Sets the db data type1.
	 * 
	 * @param dbDataType1
	 *            the new db data type1
	 */
	public void setDbDataType1(String dbDataType1) {
		this.dbDataType1 = dbDataType1;
	}

	/**
	 * Gets the db data type2.
	 * 
	 * @return the db data type2
	 */
	public String getDbDataType2() {
		return dbDataType2;
	}

	/**
	 * Sets the db data type2.
	 * 
	 * @param dbDataType2
	 *            the new db data type2
	 */
	public void setDbDataType2(String dbDataType2) {
		this.dbDataType2 = dbDataType2;
	}

	/**
	 * Gets the db data length1.
	 * 
	 * @return the db data length1
	 */
	public String getDbDataLength1() {
		return dbDataLength1;
	}

	/**
	 * Sets the db data length1.
	 * 
	 * @param dbDataLength1
	 *            the new db data length1
	 */
	public void setDbDataLength1(String dbDataLength1) {
		this.dbDataLength1 = dbDataLength1;
	}

	/**
	 * Gets the db data length2.
	 * 
	 * @return the db data length2
	 */
	public String getDbDataLength2() {
		return dbDataLength2;
	}

	/**
	 * Sets the db data length2.
	 * 
	 * @param dbDataLength2
	 *            the new db data length2
	 */
	public void setDbDataLength2(String dbDataLength2) {
		this.dbDataLength2 = dbDataLength2;
	}

	/** The color flag. */
	String colorFlag;

	/**
	 * Gets the db table name1.
	 * 
	 * @return the db table name1
	 */
	public String getDbTableName1() {
		return dbTableName1;
	}

	/**
	 * Sets the db table name1.
	 * 
	 * @param dbTableName1
	 *            the new db table name1
	 */
	public void setDbTableName1(String dbTableName1) {
		this.dbTableName1 = dbTableName1;
	}

	/**
	 * Gets the db table name2.
	 * 
	 * @return the db table name2
	 */
	public String getDbTableName2() {
		return dbTableName2;
	}

	/**
	 * Sets the db table name2.
	 * 
	 * @param dbTableName2
	 *            the new db table name2
	 */
	public void setDbTableName2(String dbTableName2) {
		this.dbTableName2 = dbTableName2;
	}

	/**
	 * Gets the color flag.
	 * 
	 * @return the color flag
	 */
	public String getColorFlag() {
		return colorFlag;
	}

	/**
	 * Sets the color flag.
	 * 
	 * @param colorFlag
	 *            the new color flag
	 */
	public void setColorFlag(String colorFlag) {
		this.colorFlag = colorFlag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(DBTableNameModel dbObjModel) {
		if (this.objectType != null) {
			return this.objectType.compareTo(dbObjModel.objectType);
		} else {
			return 0;
		}
	}

}
