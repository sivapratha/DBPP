package com.hp.dbpowerpack.Model;

import java.util.Map;


/**
 * The Class DBObjViewModel.
 */
public class DBObjViewModel implements Comparable<DBObjViewModel> {

	/** The obj type. */
	private String objType;

	/** The obj name. */
	private String objName;

	/** The status. */
	private String status;

	/** The color flag. */
	private String colorFlag;

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		this.status = status;
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

	/** The version map. */
	private Map<String, String> versionMap;

	/**
	 * Gets the obj type.
	 * 
	 * @return the obj type
	 */
	public String getObjType() {
		return objType;
	}

	/**
	 * Sets the obj type.
	 * 
	 * @param objType
	 *            the new obj type
	 */
	public void setObjType(String objType) {
		this.objType = objType;
	}

	/**
	 * Gets the obj name.
	 * 
	 * @return the obj name
	 */
	public String getObjName() {
		return objName;
	}

	/**
	 * Sets the obj name.
	 * 
	 * @param objName
	 *            the new obj name
	 */
	public void setObjName(String objName) {
		this.objName = objName;
	}

	/**
	 * Gets the version map.
	 * 
	 * @return the version map
	 */
	public Map<String, String> getVersionMap() {
		return versionMap;
	}

	/**
	 * Sets the version map.
	 * 
	 * @param versionMap
	 *            the version map
	 */
	public void setVersionMap(Map<String, String> versionMap) {
		this.versionMap = versionMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(DBObjViewModel arg0) {
		DBObjViewModel dbObjModel = (DBObjViewModel) arg0;
		return this.objType.compareTo(dbObjModel.objType);
	}

}
