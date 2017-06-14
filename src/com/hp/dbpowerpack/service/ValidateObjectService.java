package com.hp.dbpowerpack.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import com.hp.dbpowerpack.Model.DBConfigDetailsModel;
import com.hp.dbpowerpack.Model.DBObjViewModel;
import com.hp.dbpowerpack.Model.DBObjectDetailModel;
import com.hp.dbpowerpack.Model.NamingStandardModel;
import com.hp.dbpowerpack.Model.TransactionModel;
import com.hp.dbpowerpack.common.dao.DBPPTransactionManager;
import com.hp.dbpowerpack.common.exception.DBPPBusinessException;
import com.hp.dbpowerpack.common.exception.DBPPDaoException;
import com.hp.dbpowerpack.dao.NamingConventionDAO;
import com.hp.dbpowerpack.entities.NamingConvention;
import com.hp.dbpowerpack.entities.NamingConvention.UserPK;


/**
 * The Class ValidateObjectService.
 */
public class ValidateObjectService {

	/** The transaction manager. */
	private DBPPTransactionManager transactionManager;

	/** The naming convention dao. */
	private NamingConventionDAO namingConventionDAO;

	/**
	 * Gets the naming convention dao.
	 *
	 * @return the naming convention dao
	 */
	public NamingConventionDAO getNamingConventionDAO() {
		return namingConventionDAO;
	}

	/**
	 * Sets the naming convention dao.
	 *
	 * @param namingConventionDAO the new naming convention dao
	 */
	public void setNamingConventionDAO(NamingConventionDAO namingConventionDAO) {
		this.namingConventionDAO = namingConventionDAO;
	}

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
	 * Gets the invalid objects.
	 *
	 * @param configModel the config model
	 * @return the invalid objects
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public List<DBObjViewModel> getInvalidObjects(
			DBConfigDetailsModel configModel) throws DBPPBusinessException {

		List<Object> dbObjList = new ArrayList<Object>();

		TransactionModel transModel = new TransactionModel();
		transModel.setServerName(configModel.getServerName());
		transModel.setPortNumber(configModel.getPortNumber());
		transModel.setSid(configModel.getSid());
		transModel.setUsername(configModel.getUserName());
		transModel.setPassword(configModel.getPassWord());
		transModel.setDbName(configModel.getDbName());
		transModel
				.setResultSetClass("com.hp.dbpowerpack.Model.DBObjectDetailModel");

		String[] resultArr = { "setType", "setName" };
		transModel.setResultSetFieldMappings(resultArr);

		transModel
				.setQuery("SELECT object_type,object_name FROM ALL_OBJECTS WHERE OWNER = '"
						+ configModel.getUserName()
						+ "' AND STATUS = 'INVALID'");

		try {
			dbObjList = transactionManager.getData(transModel);
		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}
		List<DBObjViewModel> viewLst = convertViewList(dbObjList);

		return viewLst;
	}

	/**
	 * Convert view list.
	 *
	 * @param dbObjList the db obj list
	 * @return the list
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	private List<DBObjViewModel> convertViewList(List<Object> dbObjList)
			throws DBPPBusinessException {
		List<DBObjViewModel> viewLst = new ArrayList<DBObjViewModel>();
		for (Object obj : dbObjList) {
			DBObjectDetailModel objMdl = (DBObjectDetailModel) obj;

			DBObjViewModel viewModel = new DBObjViewModel();
			viewModel.setObjName(objMdl.getName());
			viewModel.setObjType(objMdl.getType());
			viewModel.setStatus(objMdl.getStatus());
			viewLst.add(viewModel);

		}

		return viewLst;
	}

	/**
	 * Save naming standard.
	 *
	 * @param userId the user id
	 * @param model the model
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public void saveNamingStandard(String userId, NamingStandardModel model)
			throws DBPPBusinessException {

		NamingConvention nameConvention = new NamingConvention();
		UserPK user = nameConvention.getUser();
		user.setUserId(userId);
		nameConvention.setCreatedBy(userId);
		nameConvention.setCreatedDate(new Date(System.currentTimeMillis()));

		try {

			// Procedure
			if (model.getProcedureStartsWith() != null) {
				user.setObjectType("PROCEDURE");
				nameConvention.setUser(user);
				nameConvention.setStartsWith(model.getProcedureStartsWith());
				if (model.isExist()) {
					namingConventionDAO.updateNamingConvention(nameConvention);
				} else {
					namingConventionDAO.addNamingConvention(nameConvention);
				}
			}

			// FUNCTION
			if (model.getFunctionStartsWith() != null) {
				user.setObjectType("FUNCTION");
				nameConvention.setUser(user);
				nameConvention.setStartsWith(model.getFunctionStartsWith());
				if (model.isExist()) {
					namingConventionDAO.updateNamingConvention(nameConvention);
				} else {
					namingConventionDAO.addNamingConvention(nameConvention);
				}
			}

			// PACKAGE
			if (model.getPackageStartsWith() != null) {
				user.setObjectType("PACKAGE");
				nameConvention.setUser(user);
				nameConvention.setStartsWith(model.getPackageStartsWith());
				if (model.isExist()) {
					namingConventionDAO.updateNamingConvention(nameConvention);
				} else {
					namingConventionDAO.addNamingConvention(nameConvention);
				}
			}

			// TABLE
			if (model.getTableStartsWith() != null) {
				user.setObjectType("TABLE");
				nameConvention.setUser(user);
				nameConvention.setStartsWith(model.getTableStartsWith());
				if (model.isExist()) {
					namingConventionDAO.updateNamingConvention(nameConvention);
				} else {
					namingConventionDAO.addNamingConvention(nameConvention);
				}
			}

			// VIEW
			if (model.getViewStartsWith() != null) {
				user.setObjectType("VIEW");
				nameConvention.setUser(user);
				nameConvention.setStartsWith(model.getViewStartsWith());
				if (model.isExist()) {
					namingConventionDAO.updateNamingConvention(nameConvention);
				} else {
					namingConventionDAO.addNamingConvention(nameConvention);
				}
			}

			// SEQUENCE
			if (model.getSequenceStartsWith() != null) {
				user.setObjectType("SEQUENCE");
				nameConvention.setUser(user);
				nameConvention.setStartsWith(model.getSequenceStartsWith());
				if (model.isExist()) {
					namingConventionDAO.updateNamingConvention(nameConvention);
				} else {
					namingConventionDAO.addNamingConvention(nameConvention);
				}
			}

			// TRIGGER
			if (model.getTriggerStartsWith() != null) {
				user.setObjectType("TRIGGER");
				nameConvention.setUser(user);
				nameConvention.setStartsWith(model.getTriggerStartsWith());
				if (model.isExist()) {
					namingConventionDAO.updateNamingConvention(nameConvention);
				} else {
					namingConventionDAO.addNamingConvention(nameConvention);
				}
			}

			// TYPE
			if (model.getTypeStartsWith() != null) {
				user.setObjectType("TYPE");
				nameConvention.setUser(user);
				nameConvention.setStartsWith(model.getTypeStartsWith());
				if (model.isExist()) {
					namingConventionDAO.updateNamingConvention(nameConvention);
				} else {
					namingConventionDAO.addNamingConvention(nameConvention);
				}
			}

			// METERIALIZED VIEW
			if (model.getMviewStartsWith() != null) {
				user.setObjectType("METERIALIZED VIEW");
				nameConvention.setUser(user);
				nameConvention.setStartsWith(model.getMviewStartsWith());
				if (model.isExist()) {
					namingConventionDAO.updateNamingConvention(nameConvention);
				} else {
					namingConventionDAO.addNamingConvention(nameConvention);
				}
			}

			// DB LINK
			if (model.getDbLinkStartsWith() != null) {
				user.setObjectType("DB LINK");
				nameConvention.setUser(user);
				nameConvention.setStartsWith(model.getDbLinkStartsWith());
				if (model.isExist()) {
					namingConventionDAO.updateNamingConvention(nameConvention);
				} else {
					namingConventionDAO.addNamingConvention(nameConvention);
				}
			}

		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}

	}

	/**
	 * Gets the naming standard.
	 *
	 * @param userId the user id
	 * @return the naming standard
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public NamingStandardModel getNamingStandard(String userId)
			throws DBPPBusinessException {
		List<NamingConvention> namingConventionLst = null;
		try {
			namingConventionLst = namingConventionDAO
					.getNamingConventionDetails(userId, null);
		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}

		NamingStandardModel namingStandardModel = null;

		if (namingConventionLst != null && !namingConventionLst.isEmpty()) {
			namingStandardModel = new NamingStandardModel();

			for (NamingConvention namingConven : namingConventionLst) {
				if ("PROCEDURE".equals(namingConven.getUser().getObjectType())) {
					namingStandardModel.setProcedureStartsWith(namingConven
							.getStartsWith());
				} else if ("FUNCTION".equals(namingConven.getUser()
						.getObjectType())) {
					namingStandardModel.setFunctionStartsWith(namingConven
							.getStartsWith());
				} else if ("PACKAGE".equals(namingConven.getUser()
						.getObjectType())) {
					namingStandardModel.setPackageStartsWith(namingConven
							.getStartsWith());
				} else if ("TABLE".equals(namingConven.getUser()
						.getObjectType())) {
					namingStandardModel.setTableStartsWith(namingConven
							.getStartsWith());
				} else if ("VIEW"
						.equals(namingConven.getUser().getObjectType())) {
					namingStandardModel.setViewStartsWith(namingConven
							.getStartsWith());
				} else if ("SEQUENCE".equals(namingConven.getUser()
						.getObjectType())) {
					namingStandardModel.setSequenceStartsWith(namingConven
							.getStartsWith());
				} else if ("TRIGGER".equals(namingConven.getUser()
						.getObjectType())) {
					namingStandardModel.setTriggerStartsWith(namingConven
							.getStartsWith());
				} else if ("TYPE"
						.equals(namingConven.getUser().getObjectType())) {
					namingStandardModel.setTypeStartsWith(namingConven
							.getStartsWith());
				} else if ("METERIALIZED VIEW".equals(namingConven.getUser()
						.getObjectType())) {
					namingStandardModel.setMviewStartsWith(namingConven
							.getStartsWith());
				} else if ("DB LINK".equals(namingConven.getUser()
						.getObjectType())) {
					namingStandardModel.setDbLinkStartsWith(namingConven
							.getStartsWith());
				}
			}
		}
		return namingStandardModel;
	}

	/**
	 * Validate naming standard.
	 *
	 * @param configModel the config model
	 * @param userId the user id
	 * @param slctdDbObj the slctd db obj
	 * @return the list
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public List<DBObjViewModel> validateNamingStandard(
			DBConfigDetailsModel configModel, String userId, String slctdDbObj)
			throws DBPPBusinessException {

		List<Object> dbObjList = new ArrayList<Object>();
		List<String> queryList = new ArrayList<String>();
		List<NamingConvention> namingConventionLst = null;
		try {
			namingConventionLst = namingConventionDAO
					.getNamingConventionDetails(userId, slctdDbObj);
		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}
		TransactionModel transModel = new TransactionModel();
		transModel.setServerName(configModel.getServerName());
		transModel.setPortNumber(configModel.getPortNumber());
		transModel.setSid(configModel.getSid());
		transModel.setUsername(configModel.getUserName());
		transModel.setPassword(configModel.getPassWord());
		transModel.setDbName(configModel.getDbName());
		transModel
				.setResultSetClass("com.hp.dbpowerpack.Model.DBObjectDetailModel");

		String[] resultArr = { "setType", "setName", "setStatus" };
		transModel.setResultSetFieldMappings(resultArr);

		for (NamingConvention nameConvention : namingConventionLst) {
			queryList
					.add("select OBJECT_TYPE , OBJECT_NAME , STATUS from all_objects where owner = '"
							+ configModel.getUserName()
							+ "' and object_type = '"
							+ nameConvention.getObjectType()
							+ "' and object_name not like '"
							+ nameConvention.getStartsWith() + "%'");
		}
		List<Object> dbObjListTemp = new ArrayList<Object>();
		transModel.setListQuery(true);
		transModel.setQueryList(queryList);
		try {
			dbObjListTemp = transactionManager.getData(transModel);
		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}
		for (Object obj : dbObjListTemp) {
			dbObjList.addAll((List) obj);
		}

		List<DBObjViewModel> viewLst = convertViewList(dbObjList);

		return viewLst;
	}

	/**
	 * Gets the poor readability objects.
	 *
	 * @param configModel the config model
	 * @param slctdDbObj the slctd db obj
	 * @param length the length
	 * @return the poor readability objects
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public List<DBObjViewModel> getPoorReadabilityObjects(
			DBConfigDetailsModel configModel, String slctdDbObj, String length)
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
				.setResultSetClass("com.hp.dbpowerpack.Model.DBObjectDetailModel");

		String[] resultArr = { "setType", "setName" };
		transModel.setResultSetFieldMappings(resultArr);

		transModel.setQuery("select name, type from all_source where owner = '"
				+ configModel.getUserName() + "'and length(text) > " + length
				+ " and type in ('" + slctdDbObj + "') "
				+ " group by name, type");

		try {
			dbObjList = transactionManager.getData(transModel);
		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}
		List<DBObjViewModel> viewLst = convertViewList(dbObjList);

		return viewLst;
	}

	/**
	 * Gets the rollback check.
	 *
	 * @param configModel the config model
	 * @return the rollback check
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public List<DBObjViewModel> getRollbackCheck(
			DBConfigDetailsModel configModel) throws DBPPBusinessException {

		List<Object> dbObjList = new ArrayList<Object>();

		TransactionModel transModel = new TransactionModel();
		transModel.setServerName(configModel.getServerName());
		transModel.setPortNumber(configModel.getPortNumber());
		transModel.setSid(configModel.getSid());
		transModel.setUsername(configModel.getUserName());
		transModel.setPassword(configModel.getPassWord());
		transModel.setDbName(configModel.getDbName());
		transModel
				.setResultSetClass("com.hp.dbpowerpack.Model.DBObjectDetailModel");

		String[] resultArr = { "setType", "setName" };
		transModel.setResultSetFieldMappings(resultArr);

		transModel
				.setQuery("select object_type, object_name from all_OBJECTS where owner = '"
						+ configModel.getUserName()
						+ "' AND object_TYPE = 'PROCEDURE' "
						+ "AND status = 'VALID' "
						+ "minus "
						+ "SELECT type, name  FROM all_source b where owner  = '"
						+ configModel.getUserName()
						+ "' and type = 'PROCEDURE' "
						+ "AND  LINE > ( select  max( LINE) from all_source where owner  = '"
						+ configModel.getUserName()
						+ "' "
						+ "and type = 'PROCEDURE' AND  upper (trim(text)) like 'EXCEPTION%' and name = b.name group by name) "
						+ "and upper (trim(text))  like 'ROLLBACK%' group by type,name");

		try {
			dbObjList = transactionManager.getData(transModel);
		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}
		List<DBObjViewModel> viewLst = convertViewList(dbObjList);

		return viewLst;
	}

	/**
	 * Gets the poor version management.
	 *
	 * @param configModel the config model
	 * @return the poor version management
	 */
	public List<DBObjViewModel> getPoorVersionManagement(
			DBConfigDetailsModel configModel) {

		List<Object> dbObjList = new ArrayList<Object>();

		TransactionModel transModel = new TransactionModel();
		transModel.setServerName(configModel.getServerName());
		transModel.setPortNumber(configModel.getPortNumber());
		transModel.setSid(configModel.getSid());
		transModel.setUsername(configModel.getUserName());
		transModel.setPassword(configModel.getPassWord());
		transModel.setDbName(configModel.getDbName());
		transModel
				.setResultSetClass("com.hp.dbpowerpack.Model.DBObjectDetailModel");

		String[] resultArr = { "setType", "setName", "setStatus" };
		transModel.setResultSetFieldMappings(resultArr);

		transModel
				.setQuery("select object_type, object_name , status from all_objects where owner = '"
						+ configModel.getUserName()
						+ "' and (object_type, object_name) in (select object_type, object_name  from all_objects where OWNER = '"
						+ configModel.getUserName()
						+ "' and object_type in ( 'PROCEDURE', 'FUNCTION', 'TRIGGER' , 'PACKAGE' , 'PACKAGE BODY') minus "
						+ "SELECT  a3.TYPE,a3.NAME FROM all_source a3, (SELECT   NAME, MAX (line) line FROM all_source a2 "
						+ "WHERE line < (SELECT MIN (line) FROM all_source a1 WHERE REGEXP_LIKE (text, '\\*/') "
						+ "AND a1.owner = a2.owner AND a1.TYPE = a2.TYPE AND a1.NAME = a2.NAME) "
						+ "AND TYPE IN ( 'PROCEDURE', 'FUNCTION', 'TRIGGER' , 'PACKAGE' , 'PACKAGE BODY') "
						+ "AND owner = '"
						+ configModel.getUserName()
						+ "' AND REGEXP_LIKE (text, '^ +[0-9]+') GROUP BY NAME) a4 WHERE a3.line = a4.line AND a3.NAME = a4.NAME "
						+ "AND a3.owner = '"
						+ configModel.getUserName()
						+ "' AND a3.TYPE IN ( 'PROCEDURE', 'FUNCTION', 'TRIGGER' , 'PACKAGE' , 'PACKAGE BODY') ) order by object_type , object_name ");

		try {
			dbObjList = transactionManager.getData(transModel);
		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}
		List<DBObjViewModel> viewLst = convertViewList(dbObjList);

		return viewLst;
	}

}
