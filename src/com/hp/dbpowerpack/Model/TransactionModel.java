package com.hp.dbpowerpack.Model;

import java.util.List;


/**
 * The Class TransactionModel.
 */
public class TransactionModel {

	/** The server name. */
	private String serverName;

	/** The port number. */
	private String portNumber;

	/** The sid. */
	private String sid;

	/** The username. */
	private String username;

	/** The password. */
	private String password;

	/** The query. */
	private String query;

	/** The db name. */
	private String dbName;

	/** The result set class. */
	private String resultSetClass;

	/** The result set field mappings. */
	private String[] resultSetFieldMappings;

	/** The query list. */
	private List<String> queryList;

	/** The is list query. */
	private boolean isListQuery;

	/** The query type. */
	private String queryType;

	/**
	 * Gets the query type.
	 * 
	 * @return the query type
	 */
	public String getQueryType() {
		return queryType;
	}

	/**
	 * Sets the query type.
	 * 
	 * @param queryType
	 *            the new query type
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	/**
	 * Checks if is list query.
	 * 
	 * @return true, if is list query
	 */
	public boolean isListQuery() {
		return isListQuery;
	}

	/**
	 * Sets the list query.
	 * 
	 * @param isListQuery
	 *            the new list query
	 */
	public void setListQuery(boolean isListQuery) {
		this.isListQuery = isListQuery;
	}

	/**
	 * Gets the query list.
	 * 
	 * @return the query list
	 */
	public List<String> getQueryList() {
		return queryList;
	}

	/**
	 * Sets the query list.
	 * 
	 * @param queryList
	 *            the new query list
	 */
	public void setQueryList(List<String> queryList) {
		this.queryList = queryList;
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
	 * Gets the server name.
	 * 
	 * @return the server name
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * Gets the result set class.
	 * 
	 * @return the result set class
	 */
	public String getResultSetClass() {
		return resultSetClass;
	}

	/**
	 * Sets the result set class.
	 * 
	 * @param resultSetClass
	 *            the new result set class
	 */
	public void setResultSetClass(String resultSetClass) {
		this.resultSetClass = resultSetClass;
	}

	/**
	 * Gets the result set field mappings.
	 * 
	 * @return the result set field mappings
	 */
	public String[] getResultSetFieldMappings() {
		return resultSetFieldMappings;
	}

	/**
	 * Sets the result set field mappings.
	 * 
	 * @param resultSetFieldMappings
	 *            the new result set field mappings
	 */
	public void setResultSetFieldMappings(String[] resultSetFieldMappings) {
		this.resultSetFieldMappings = resultSetFieldMappings;
	}

	/**
	 * Sets the server name.
	 * 
	 * @param serverName
	 *            the new server name
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * Gets the port number.
	 * 
	 * @return the port number
	 */
	public String getPortNumber() {
		return portNumber;
	}

	/**
	 * Sets the port number.
	 * 
	 * @param portNumber
	 *            the new port number
	 */
	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	/**
	 * Gets the sid.
	 * 
	 * @return the sid
	 */
	public String getSid() {
		return sid;
	}

	/**
	 * Sets the sid.
	 * 
	 * @param sid
	 *            the new sid
	 */
	public void setSid(String sid) {
		this.sid = sid;
	}

	/**
	 * Gets the username.
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 * 
	 * @param username
	 *            the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the query.
	 * 
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * Sets the query.
	 * 
	 * @param query
	 *            the new query
	 */
	public void setQuery(String query) {
		this.query = query;
	}
}
