package com.hp.dbpowerpack.common.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.hp.dbpowerpack.common.exception.DBPPDaoException;


/**
 * The Class DBPPConnection.
 */
public class DBPPConnection {

	/**
	 * Gets the connection.
	 *
	 * @param serverName the server name
	 * @param portNumber the port number
	 * @param sid the sidadAD
	 * @param userName the user name
	 * @param password the password
	 * @return the connection
	 * @throws DBPPDaoException the dBPP dao exception
	 */
	public Connection getConnection(final String serverName,
			final String portNumber, final String sid, final String userName,
			final String password) throws DBPPDaoException {
		Connection myConnection = null;
		// Load the JDBC driver
		String driverName = "oracle.jdbc.driver.OracleDriver";

		try {
			Class.forName(driverName);

			String url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber
					+ "/" + sid;

			myConnection = DriverManager.getConnection(url, userName, password);
		} catch (ClassNotFoundException e) {
			throw new DBPPDaoException(e);
		} catch (SQLException e) {
			throw new DBPPDaoException(e);
		}
		return myConnection;
	}

	/**
	 * Close connection.
	 *
	 * @param connection the connection
	 * @return true, if successful
	 * @throws DBPPDaoException the dBPP dao exception
	 */
	public boolean closeConnection(Connection connection)
			throws DBPPDaoException {
		boolean connectionClosedFlag = false;
		try {
			connection.close();
			connectionClosedFlag = true;
		} catch (SQLException ex) {
			throw new DBPPDaoException(ex);
		}
		return connectionClosedFlag;
	}
}
