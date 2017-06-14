package com.hp.dbpowerpack.service;

import java.util.ArrayList;
import java.util.List;

import com.hp.dbpowerpack.Model.DBConfigDetailsModel;
import com.hp.dbpowerpack.common.dao.DBPPTransactionManager;
import com.hp.dbpowerpack.common.exception.DBPPBusinessException;
import com.hp.dbpowerpack.common.exception.DBPPConfigException;
import com.hp.dbpowerpack.common.exception.DBPPDaoException;
import com.hp.dbpowerpack.common.util.DBPPEncryptDecryptUtil;
import com.hp.dbpowerpack.dao.DBConfigDetailsDAO;
import com.hp.dbpowerpack.entities.DBConfigDetails;


/**
 * The Class DBConfigDetailsService.
 */
public class DBConfigDetailsService {

	/** The db config details dao. */
	private DBConfigDetailsDAO dbConfigDetailsDAO;

	/** The transaction manager. */
	private DBPPTransactionManager transactionManager;

	/**
	 * Gets the transaction manager.
	 * 
	 * @return the transaction manager
	 */
	public DBPPTransactionManager getTransactionManager() {
		return transactionManager;
	}

	/**
	 * Sets the transaction manager.
	 * 
	 * @param transactionManager
	 *            the new transaction manager
	 */
	public void setTransactionManager(DBPPTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	/**
	 * Gets the db config details dao.
	 * 
	 * @return the db config details dao
	 */
	public DBConfigDetailsDAO getDbConfigDetailsDAO() {
		return dbConfigDetailsDAO;
	}

	/**
	 * Sets the db config details dao.
	 * 
	 * @param dbConfigDetailsDAO
	 *            the new db config details dao
	 */
	public void setDbConfigDetailsDAO(DBConfigDetailsDAO dbConfigDetailsDAO) {
		this.dbConfigDetailsDAO = dbConfigDetailsDAO;
	}

	/**
	 * Gets the dB config detail.
	 *
	 * @param dbName the db name
	 * @param userId the user id
	 * @return the dB config detail
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public DBConfigDetailsModel getDBConfigDetail(String dbName, String userId)
			throws DBPPBusinessException {
		DBConfigDetailsModel dbConfigModel = new DBConfigDetailsModel();
		try {
			DBConfigDetails dbConfigDtl = dbConfigDetailsDAO.getDBConfigDetail(
					dbName, userId);

			if (dbConfigDtl != null) {
				dbConfigModel.setDbName(dbConfigDtl.getDbName());
				dbConfigModel.setServerName(dbConfigDtl.getServerName());
				dbConfigModel.setPortNumber(dbConfigDtl.getPortNumber());
				dbConfigModel.setSid(dbConfigDtl.getSid());
				dbConfigModel.setUserName(dbConfigDtl.getUserName());
				dbConfigModel.setPassWord(DBPPEncryptDecryptUtil
						.decrypt(dbConfigDtl.getPassWord()));
			}

		} catch (DBPPConfigException ex) {
			throw new DBPPBusinessException(ex);
		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}

		return dbConfigModel;
	}

	/**
	 * Gets the db user names.
	 *
	 * @param userId the user id
	 * @return the db user names
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public List<DBConfigDetailsModel> getdbUserNames(String userId)
			throws DBPPBusinessException {
		List<DBConfigDetailsModel> dbConfigList = new ArrayList<DBConfigDetailsModel>();
		try {
			List<DBConfigDetails> dbNameList = dbConfigDetailsDAO
					.getDBConfigDetailsList(userId);
			for (DBConfigDetails config : dbNameList) {
				DBConfigDetailsModel configModl = new DBConfigDetailsModel();
				configModl.setUserName(config.getUserName());
				configModl.setDbName(config.getDbName());
				configModl.setServerName(config.getServerName());
				configModl.setSid(config.getSid());
				configModl.setPortNumber(config.getPortNumber());
				configModl.setPassWord(config.getPassWord());
				dbConfigList.add(configModl);
			}
		} catch (DBPPConfigException ex) {
			throw new DBPPBusinessException(ex);
		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}
		return dbConfigList;
	}

	/**
	 * Adds the db config details.
	 *
	 * @param dbConfigDetails the db config details
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public void addDBConfigDetails(DBConfigDetails dbConfigDetails)
			throws DBPPBusinessException {
		try {
			dbConfigDetailsDAO.addDBConfigDetails(dbConfigDetails);
		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}
	}

	/**
	 * Gets the dB config details list.
	 *
	 * @param userId the user id
	 * @return the dB config details list
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public List<DBConfigDetails> getDBConfigDetailsList(String userId)
			throws DBPPBusinessException {
		List<DBConfigDetails> dbConfigList = null;
		try {
			dbConfigList = dbConfigDetailsDAO.getDBConfigDetailsList(userId);
		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}
		return dbConfigList;
	}

	/**
	 * Removes the config.
	 *
	 * @param removeConfigArr the remove config arr
	 * @param userId the user id
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public void removeConfig(String[] removeConfigArr, String userId)
			throws DBPPBusinessException {

		for (int i = 0; i < removeConfigArr.length; i++) {
			if (removeConfigArr[i] != null && !removeConfigArr[i].equals("")) {
				try {
					dbConfigDetailsDAO.removeConfig(removeConfigArr[i], userId);
				} catch (DBPPDaoException ex) {
					throw new DBPPBusinessException(ex);
				}
			}

		}

	}

	/**
	 * Gets the db model.
	 *
	 * @param userId the user id
	 * @param dbName the db name
	 * @return the db model
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public DBConfigDetailsModel getdbModel(String userId, String dbName)
			throws DBPPBusinessException {
		DBConfigDetailsModel configModl = new DBConfigDetailsModel();
		try {
			DBConfigDetails dbConfigModel = dbConfigDetailsDAO
					.getDBConfigDetailsList(userId, dbName);

			if (dbConfigModel != null) {
				configModl.setUserName(dbConfigModel.getUserName());
				configModl.setDbName(dbConfigModel.getDbName());
				configModl.setServerName(dbConfigModel.getServerName());
				configModl.setSid(dbConfigModel.getSid());
				configModl.setPortNumber(dbConfigModel.getPortNumber());
				configModl.setPassWord(DBPPEncryptDecryptUtil
						.decrypt(dbConfigModel.getPassWord()));

			}
		} catch (DBPPConfigException e) {
			throw new DBPPBusinessException(e);
		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}
		return configModl;
	}

}
