package com.hp.dbpowerpack.service;

import java.util.ArrayList;
import java.util.List;

import com.hp.dbpowerpack.Model.DBConfigDetailsModel;
import com.hp.dbpowerpack.Model.DbJobsModel;
import com.hp.dbpowerpack.Model.TransactionModel;
import com.hp.dbpowerpack.common.dao.DBPPTransactionManager;
import com.hp.dbpowerpack.common.exception.DBPPBusinessException;
import com.hp.dbpowerpack.common.exception.DBPPDaoException;


/**
 * The Class HealthCheckService.
 */
public class HealthCheckService {

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
	 * Gets the table space details.
	 *
	 * @param configModel the config model
	 * @return the table space details
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public List<Object> getTableSpaceDetails(DBConfigDetailsModel configModel)
			throws DBPPBusinessException {

		List<Object> dbObjList = new ArrayList<Object>();

		TransactionModel transModel = new TransactionModel();
		transModel.setServerName(configModel.getServerName());
		transModel.setPortNumber(configModel.getPortNumber());
		transModel.setSid(configModel.getSid());
		transModel.setUsername(configModel.getUserName());
		transModel.setPassword(configModel.getPassWord());
		transModel.setDbName(configModel.getDbName());
		transModel
				.setResultSetClass("com.hp.dbpowerpack.Model.TableSpaceModel");

		String[] resultArr = { "setTableSpaceName", "setSizeMb", "setFreeMb",
				"setFreePerc", "setUsedPerc" };
		transModel.setResultSetFieldMappings(resultArr);

		transModel
				.setQuery("SELECT /* + RULE */  df.tablespace_name \"Tablespace\", "
						+ "df.bytes / (1024 * 1024) \"Size_MB\", "
						+ "SUM(fs.bytes) / (1024 * 1024) \"Free_MB\", "
						+ "Nvl(Round(SUM(fs.bytes) * 100 / df.bytes),1) \"Free_%\","
						+ " Round((df.bytes - SUM(fs.bytes)) * 100 / df.bytes) \"Used_%\" "
						+ "FROM dba_free_space fs, (SELECT tablespace_name,SUM(bytes) bytes FROM dba_data_files GROUP BY tablespace_name) df "
						+ "WHERE fs.tablespace_name (+)  = df.tablespace_name GROUP BY df.tablespace_name,df.bytes "
						+ "UNION ALL SELECT /* + RULE */ df.tablespace_name tspace, fs.bytes / (1024 * 1024), SUM(df.bytes_free) / (1024 * 1024),"
						+ "Nvl(Round((SUM(fs.bytes) - df.bytes_used) * 100 / fs.bytes), 1), Round((SUM(fs.bytes) - df.bytes_free) * 100 / fs.bytes)"
						+ "FROM dba_temp_files fs,(SELECT tablespace_name,bytes_free,bytes_used FROM v$temp_space_header "
						+ "GROUP BY tablespace_name,bytes_free,bytes_used) df WHERE fs.tablespace_name (+)  = df.tablespace_name "
						+ "GROUP BY df.tablespace_name,fs.bytes,df.bytes_free,df.bytes_used ORDER BY 4 DESC");

		try {
			dbObjList = transactionManager.getData(transModel);
		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}

		return dbObjList;
	}

	/**
	 * Block contention.
	 *
	 * @param configModel the config model
	 * @return the list
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public List blockContention(DBConfigDetailsModel configModel)
			throws DBPPBusinessException {

		List<Object> dbObjList = new ArrayList<Object>();

		TransactionModel transModel = new TransactionModel();
		transModel.setServerName(configModel.getServerName());
		transModel.setPortNumber(configModel.getPortNumber());
		transModel.setSid(configModel.getSid());
		transModel.setUsername(configModel.getUserName());
		transModel.setPassword(configModel.getPassWord());
		transModel.setDbName(configModel.getDbName());
		transModel
				.setResultSetClass("com.hp.dbpowerpack.Model.BlockContentionModel");

		String[] resultArr = { "setWaitClass", "setTotalWaits", "setTotalTime" };
		transModel.setResultSetFieldMappings(resultArr);

		transModel
				.setQuery("SELECT class,SUM(COUNT) total_waits, SUM(TIME) total_time FROM v$waitstat GROUP BY class");

		try {
			dbObjList = transactionManager.getData(transModel);
		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}

		return dbObjList;
	}

	/**
	 * Partitioned table.
	 *
	 * @param configModel the config model
	 * @return the list
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public List partitionedTable(DBConfigDetailsModel configModel)
			throws DBPPBusinessException {

		List<Object> dbObjList = new ArrayList<Object>();

		TransactionModel transModel = new TransactionModel();
		transModel.setServerName(configModel.getServerName());
		transModel.setPortNumber(configModel.getPortNumber());
		transModel.setSid(configModel.getSid());
		transModel.setUsername(configModel.getUserName());
		transModel.setPassword(configModel.getPassWord());
		transModel.setDbName(configModel.getDbName());
		transModel.setResultSetClass("java.lang.String");

		transModel
				.setQuery("SELECT table_name FROM user_tables WHERE partitioned='YES'");

		try {
			dbObjList = transactionManager.getData(transModel);
		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}
		return dbObjList;
	}

	/**
	 * Available jobs.
	 *
	 * @param dbModel the db model
	 * @return the list
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public List<DbJobsModel> availableJobs(DBConfigDetailsModel dbModel)
			throws DBPPBusinessException {

		List<DbJobsModel> dbObjModelList = new ArrayList<DbJobsModel>();

		TransactionModel transModel = new TransactionModel();
		transModel.setServerName(dbModel.getServerName());
		transModel.setPortNumber(dbModel.getPortNumber());
		transModel.setSid(dbModel.getSid());
		transModel.setUsername(dbModel.getUserName());
		transModel.setPassword(dbModel.getPassWord());
		transModel.setDbName(dbModel.getDbName());
		transModel.setResultSetClass("com.hp.dbpowerpack.Model.DbJobsModel");

		String[] resultArr = { "setLogUser", "setPrivUser", "setLastDate",
				"setLastSec", "setThisDate", "setThisSec", "setBroken",
				"setWhat" };
		transModel.setResultSetFieldMappings(resultArr);

		transModel
				.setQuery("select log_user , priv_user, last_Date, last_sec, this_Date, this_sec, broken , what from dba_jobs");

		try {
			dbObjModelList = (List<DbJobsModel>) transactionManager
					.getData(transModel);
		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}

		return dbObjModelList;
	}

	/**
	 * Jobs broken.
	 *
	 * @param dbModel the db model
	 * @return the list
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public List<DbJobsModel> jobsBroken(DBConfigDetailsModel dbModel)
			throws DBPPBusinessException {

		List<DbJobsModel> dbObjModelList = new ArrayList<DbJobsModel>();

		TransactionModel transModel = new TransactionModel();
		transModel.setServerName(dbModel.getServerName());
		transModel.setPortNumber(dbModel.getPortNumber());
		transModel.setSid(dbModel.getSid());
		transModel.setUsername(dbModel.getUserName());
		transModel.setPassword(dbModel.getPassWord());
		transModel.setDbName(dbModel.getDbName());
		transModel.setResultSetClass("com.hp.dbpowerpack.Model.DbJobsModel");

		String[] resultArr = { "setLogUser", "setPrivUser", "setLastDate",
				"setLastSec", "setThisDate", "setThisSec", "setWhat" };
		transModel.setResultSetFieldMappings(resultArr);

		transModel
				.setQuery("select log_user , priv_user, last_Date, last_sec, this_Date, this_sec,   what   from dba_jobs where broken = 'Y'");

		try {
			dbObjModelList = (List<DbJobsModel>) transactionManager
					.getData(transModel);
		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}

		return dbObjModelList;
	}

	/**
	 * Jobs running.
	 *
	 * @param dbModel the db model
	 * @return the list
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public List<DbJobsModel> jobsRunning(DBConfigDetailsModel dbModel)
			throws DBPPBusinessException {

		List<DbJobsModel> dbObjModelList = new ArrayList<DbJobsModel>();

		TransactionModel transModel = new TransactionModel();
		transModel.setServerName(dbModel.getServerName());
		transModel.setPortNumber(dbModel.getPortNumber());
		transModel.setSid(dbModel.getSid());
		transModel.setUsername(dbModel.getUserName());
		transModel.setPassword(dbModel.getPassWord());
		transModel.setDbName(dbModel.getDbName());
		transModel.setResultSetClass("com.hp.dbpowerpack.Model.DbJobsModel");

		String[] resultArr = { "setJob", "setFailures", "setLastDate",
				"setLastSec", "setThisDate", "setThisSec" };
		transModel.setResultSetFieldMappings(resultArr);

		transModel
				.setQuery("select job, failures , last_Date, last_sec , this_Date, this_sec from dba_jobs_running");

		try {
			dbObjModelList = (List<DbJobsModel>) transactionManager
					.getData(transModel);
		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}
		return dbObjModelList;
	}

}
