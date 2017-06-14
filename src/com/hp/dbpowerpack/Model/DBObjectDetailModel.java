package com.hp.dbpowerpack.Model;

import java.sql.Date;


/**
 * The Class DBObjectDetailModel.
 */
public class DBObjectDetailModel implements Comparable<DBObjectDetailModel> {

	/** The owner. */
	private String owner;

	/** The type. */
	private String type;

	/** The name. */
	private String name;

	/** The version. */
	private String version;

	/** The user. */
	private String user;

	/** The sysdate. */
	private Date sysdate;

	/** The db name. */
	private String dbName;

	/** The status. */
	private String status;

	/** The source. */
	private String source;

	/** The line. */
	private Double line;

	/**
	 * Gets the source.
	 * 
	 * @return the source
	 */
	public String getSource() {
		if (this.source == null) {
			this.source = "";
		}
		return source;
	}

	/**
	 * Sets the source.
	 * 
	 * @param source
	 *            the new source
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * Gets the line.
	 * 
	 * @return the line
	 */
	public Double getLine() {
		return line;
	}

	/**
	 * Sets the line.
	 * 
	 * @param line
	 *            the new line
	 */
	public void setLine(Double line) {
		this.line = line;
	}

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
	 * Gets the db name.
	 * 
	 * @return the db name
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * Sets the db name.
	 * 
	 * @param dbName
	 *            the new db name
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * Gets the owner.
	 * 
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Sets the owner.
	 * 
	 * @param owner
	 *            the new owner
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the version.
	 * 
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 * 
	 * @param version
	 *            the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the user.
	 * 
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 * 
	 * @param user
	 *            the new user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Gets the sysdate.
	 * 
	 * @return the sysdate
	 */
	public Date getSysdate() {
		return sysdate;
	}

	/**
	 * Sets the sysdate.
	 * 
	 * @param sysdate
	 *            the new sysdate
	 */
	public void setSysdate(Date sysdate) {
		this.sysdate = sysdate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(DBObjectDetailModel arg0) {
		DBObjectDetailModel dbObjModel = (DBObjectDetailModel) arg0;
		return this.line.compareTo(dbObjModel.line);
	}
}
