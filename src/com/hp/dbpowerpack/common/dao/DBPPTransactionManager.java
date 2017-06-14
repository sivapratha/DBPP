package com.hp.dbpowerpack.common.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.hp.dbpowerpack.Model.DBObjectDetailModel;
import com.hp.dbpowerpack.Model.TransactionModel;
import com.hp.dbpowerpack.common.exception.DBPPDaoException;


/**
 * The Class DBPPTransactionManager.
 */
public class DBPPTransactionManager {

	/** The Constant logger. */
	private static final Logger LOGGER = Logger
			.getLogger(DBPPTransactionManager.class);

	/** The db pp connection. */
	private DBPPConnection dbPPConnection;

	/**
	 * Gets the db pp connection.
	 * 
	 * @return the db pp connection
	 */
	public DBPPConnection getDbPPConnection() {
		return dbPPConnection;
	}

	/**
	 * Sets the db pp connection.
	 * 
	 * @param dbPPConnection
	 *            the new db pp connection
	 */
	public void setDbPPConnection(DBPPConnection dbPPConnection) {
		this.dbPPConnection = dbPPConnection;
	}

	/**
	 * Gets the data.
	 *
	 * @param transactionModel the transaction model
	 * @return the data
	 * @throws DBPPDaoException the dBPP dao exception
	 */
	public List getData(TransactionModel transactionModel)
			throws DBPPDaoException {
		List<Object> dbObjList = new ArrayList<Object>();

		PreparedStatement  statement = null;
		Connection conn = null;

		try {
			conn = dbPPConnection.getConnection(
					transactionModel.getServerName(),
					transactionModel.getPortNumber(),
					transactionModel.getSid(), transactionModel.getUsername(),
					transactionModel.getPassword());
			conn.setAutoCommit(false);

			

			if (transactionModel.isListQuery()) {
				List<Object> dbObjListTemp = new ArrayList<Object>();
				for (String query : transactionModel.getQueryList()) {
					Date startDate = new Date(System.currentTimeMillis());
					LOGGER.info("Query-->" + query);
					// create a Statement object
					statement = conn.prepareStatement(query);					
					dbObjListTemp = queryData(transactionModel, statement);
					Date endDate = new Date(System.currentTimeMillis());
					LOGGER.info("queryData differnce"
							+ (endDate.getTime() - startDate.getTime()));
					dbObjList.add(dbObjListTemp);
				}

			} else {
				statement = conn.prepareStatement(transactionModel.getQuery());	
				dbObjList = queryData(transactionModel, statement);
			}

		} catch (SQLException e) {
			throw new DBPPDaoException(e);
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}

				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				throw new DBPPDaoException(e);
			}
		}

		return dbObjList;
	}

	/**
	 * Query data.
	 *
	 * @param transactionModel the transaction model
	 * @param statement the statement
	 * @param query the query
	 * @return the list
	 * @throws DBPPDaoException the dBPP dao exception
	 */
	private List<Object> queryData(TransactionModel transactionModel,
			PreparedStatement  statement) throws DBPPDaoException {
		ResultSet result = null;
		List<Object> dbObjList = new ArrayList<Object>();
		try {

			long startDate = System.currentTimeMillis();
			result = statement.executeQuery();

			long endDate = System.currentTimeMillis();
			LOGGER.info("executeQuery differnce" + (endDate - startDate));

			if ("ObjectSource".equals(transactionModel.getQueryType())) {
				dbObjList = getResultSetObjectSource(result, transactionModel);
			} else {
				dbObjList = getResultSet(result, transactionModel);
			}

		} catch (SQLException e) {
			throw new DBPPDaoException(e);
		}
		return dbObjList;
	}

	/**
	 * Gets the result set object source.
	 *
	 * @param result the result
	 * @param transactionModel the transaction model
	 * @return the result set object source
	 * @throws DBPPDaoException the dBPP dao exception
	 */
	private List<Object> getResultSetObjectSource(ResultSet result,
			TransactionModel transactionModel) throws DBPPDaoException {
		List<Object> dbObjList = new ArrayList<Object>();
		try {
			long startDate = System.currentTimeMillis();
			while (result.next()) {
				DBObjectDetailModel objModel = new DBObjectDetailModel();
				if (result.getObject(1) != null) {
					objModel.setType(result.getObject(1).toString());
				} else {
					objModel.setType("");
				}

				if (result.getObject(2) != null) {
					objModel.setName(result.getObject(2).toString());
				} else {
					objModel.setName("");
				}

				if (result.getObject(3) != null) {
					objModel.setLine(new Double(result.getObject(3).toString()));
				} else {
					objModel.setLine(null);
				}
				if (result.getObject(4) != null) {
					objModel.setSource(result.getObject(4).toString());
				} else {
					objModel.setSource("");
				}
				dbObjList.add(objModel);

			}
			long endDate = System.currentTimeMillis();
			LOGGER.info("tranfer result set->" + (endDate - startDate));
		} catch (SQLException e) {
			throw new DBPPDaoException(e);
		}
		return dbObjList;
	}

	/**
	 * Gets the result set.
	 *
	 * @param result the result
	 * @param transactionModel the transaction model
	 * @return the result set
	 * @throws DBPPDaoException the dBPP dao exception
	 */
	private List<Object> getResultSet(ResultSet result,
			TransactionModel transactionModel) throws DBPPDaoException {

		List<Object> dbObjList = new ArrayList<Object>();
		Class<?> transferObjectClass = null;
		Object transferObject = null;
		String[] resultArr = transactionModel.getResultSetFieldMappings();

		try {

			if ("java.lang.String".equals(transactionModel.getResultSetClass())) {
				while (result.next()) {
					for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
						Object obj = result.getObject(i);
						dbObjList.add(obj);
					}
				}

			} else {
				final ClassLoader loader = Thread.currentThread()
						.getContextClassLoader();
				if (loader == null) {
					transferObjectClass = Class.forName(transactionModel
							.getResultSetClass());
				} else {
					transferObjectClass = Class.forName(
							transactionModel.getResultSetClass(), true, loader);
				}
				long startDate = System.currentTimeMillis();
				while (result.next()) {
					transferObject = transferObjectClass.newInstance();
					for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
						final int columnType = result.getMetaData()
								.getColumnType(i);
						Object obj = null;
						Object objTemp = result.getObject(i);
						String parametClasstype = "java.lang.String";
						if (columnType == java.sql.Types.NUMERIC) {
							parametClasstype = "java.lang.Double";
							if (objTemp != null) {
								obj = new Double(objTemp.toString());
							}

						} else if (columnType == java.sql.Types.TIMESTAMP) {
							parametClasstype = "java.util.Date";
							if (objTemp != null) {
								obj = (Date) objTemp;
							}

						} else {
							if (objTemp != null) {
								obj = objTemp.toString();
							}

						}

						// logger.info("columnName->"+columnName+"<-columnType->"+columnType);
						Class<?> parameterClass = null;
						if (loader == null) {
							parameterClass = Class.forName(parametClasstype);
						} else {
							parameterClass = Class.forName(parametClasstype,
									true, loader);
						}

						transferObjectClass.getMethod(resultArr[i - 1],
								new Class[] { parameterClass }).invoke(
								transferObject, new Object[] { obj });
					}

					dbObjList.add(transferObject);
				}
				long endDate = System.currentTimeMillis();
				LOGGER.info("setMethod differnce" + (endDate - startDate));

			}
		} catch (SQLException sq) {
			throw new DBPPDaoException(sq);
		} catch (IllegalArgumentException e) {
			throw new DBPPDaoException(e);
		} catch (SecurityException e) {
			throw new DBPPDaoException(e);
		} catch (IllegalAccessException e) {
			throw new DBPPDaoException(e);
		} catch (InvocationTargetException e) {
			throw new DBPPDaoException(e);
		} catch (NoSuchMethodException e) {
			throw new DBPPDaoException(e);
		} catch (ClassNotFoundException e) {
			throw new DBPPDaoException(e);
		} catch (InstantiationException e) {
			throw new DBPPDaoException(e);
		}

		return dbObjList;
	}
}
