package com.hp.dbpowerpack.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hp.dbpowerpack.Model.DBConfigDetailsModel;
import com.hp.dbpowerpack.Model.DBObjViewModel;
import com.hp.dbpowerpack.Model.DBObjectDetailModel;
import com.hp.dbpowerpack.Model.DBTableNameModel;
import com.hp.dbpowerpack.Model.DBTableStructModel;
import com.hp.dbpowerpack.Model.TableFieldMappingModel;
import com.hp.dbpowerpack.Model.TransactionModel;
import com.hp.dbpowerpack.common.dao.DBPPTransactionManager;
import com.hp.dbpowerpack.common.exception.DBPPBusinessException;
import com.hp.dbpowerpack.common.exception.DBPPDaoException;


/**
 * The Class CompareDBService.
 */
public class CompareDBService {

	/** The Constant logger. */
	private static final Logger LOGGER = Logger
			.getLogger(CompareDBService.class);

	/** The db config details service. */
	private DBConfigDetailsService dbConfigDetailsService;

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
	 * Gets the db config details service.
	 * 
	 * @return the db config details service
	 */
	public DBConfigDetailsService getDbConfigDetailsService() {
		return dbConfigDetailsService;
	}

	/**
	 * Sets the db config details service.
	 * 
	 * @param dbConfigDetailsService
	 *            the new db config details service
	 */
	public void setDbConfigDetailsService(
			DBConfigDetailsService dbConfigDetailsService) {
		this.dbConfigDetailsService = dbConfigDetailsService;
	}

	/**
	 * Compare.
	 * 
	 * @param dbNameList
	 *            the db name list
	 * @param selectedObjects
	 *            the selected objects
	 * @param usedId
	 *            the used id
	 * @return the map
	 * @throws DBPPBusinessException
	 *             the dBPP business exception
	 */
	public Map<String, DBObjViewModel> compare(List<String> dbNameList,
			String selectedObjects, String usedId) throws DBPPBusinessException {

		Map<String, DBObjViewModel> objectMap = new HashMap<String, DBObjViewModel>();
		List<Object> dbObjList = new ArrayList<Object>();

		try {
			for (String dbName : dbNameList) {
				DBConfigDetailsModel configModel = dbConfigDetailsService
						.getDBConfigDetail(dbName, usedId);
				TransactionModel transModel = new TransactionModel();
				transModel.setServerName(configModel.getServerName());
				transModel.setPortNumber(configModel.getPortNumber());
				transModel.setSid(configModel.getSid());
				transModel.setUsername(configModel.getUserName());
				transModel.setPassword(configModel.getPassWord());
				transModel.setDbName(configModel.getDbName());
				transModel
						.setResultSetClass("com.hp.dbpowerpack.Model.DBObjectDetailModel");

				String[] resultArr = { "setOwner", "setType", "setName",
						"setVersion", "setUser", "setDbName" };
				transModel.setResultSetFieldMappings(resultArr);

				transModel
						.setQuery("SELECT A3.OWNER, A3.TYPE, A3.NAME, REGEXP_SUBSTR(A3.TEXT, '[0-9]+') AS VERSION, USER, '"
								+ configModel.getDbName()
								+ "'  as DBName FROM ALL_SOURCE A3 , ( SELECT NAME , MAX(LINE) LINE FROM ALL_SOURCE A2 "
								+ "WHERE LINE < ( SELECT MIN(LINE) FROM ALL_SOURCE A1 WHERE REGEXP_LIKE(TEXT, '\\*/') "
								+ "AND A1.OWNER = A2.OWNER AND A1.TYPE = A2.TYPE AND A1.NAME = A2.NAME  ) "
								+ "AND TYPE IN ( '"
								+ selectedObjects
								+ "') AND OWNER = '"
								+ configModel.getUserName()
								+ "'AND REGEXP_LIKE(TEXT, '^ +[0-9]+') "
								+ "GROUP BY NAME ) A4 "
								+ "WHERE A3.LINE  = A4.LINE AND A3.NAME  = A4.NAME AND A3.OWNER = '"
								+ configModel.getUserName()
								+ "' AND A3.TYPE IN ( '"
								+ selectedObjects
								+ "') " + " Order by A3.TYPE");

				List<Object> dbObjListTemp = new ArrayList<Object>();
				dbObjListTemp = transactionManager.getData(transModel);
				if (!dbObjListTemp.isEmpty()) {
					dbObjList.addAll(dbObjListTemp);
				}

			}

			objectMap = converListToTypeMap(dbObjList, dbNameList.size());

		} catch (DBPPDaoException ex) {
			LOGGER.info("Exception occured" + ex);
			throw new DBPPBusinessException(ex);
		}

		return objectMap;
	}

	/**
	 * Conver list to type map.
	 * 
	 * @param dbObjList
	 *            the db obj list
	 * @param DBListCnt
	 *            the dB list cnt
	 * @return the map
	 */
	private Map<String, DBObjViewModel> converListToTypeMap(
			List<Object> dbObjList, int DBListCnt) {

		Map<String, DBObjViewModel> objectMap = new HashMap<String, DBObjViewModel>();
		if (dbObjList != null && !dbObjList.isEmpty()) {
			for (Object obj : dbObjList) {
				DBObjectDetailModel objModl = (DBObjectDetailModel) obj;
				if (objectMap.containsKey(objModl.getType() + "#"
						+ objModl.getName())) {
					DBObjViewModel dbObjViewModel = (DBObjViewModel) objectMap
							.get(objModl.getType() + "#" + objModl.getName());
					Map<String, String> versionMap = dbObjViewModel
							.getVersionMap();
					if (!"Red".equals(dbObjViewModel.getColorFlag())) {
						String version = objModl.getVersion();
						for (String vers : versionMap.values()) {
							if (version != null && !version.equals(vers)) {
								dbObjViewModel.setColorFlag("Red");
								break;
							}
						}
					}
					if (versionMap != null
							&& !versionMap.containsKey(objModl.getDbName())) {
						versionMap.put(objModl.getDbName(),
								objModl.getVersion());
					}
					dbObjViewModel.setVersionMap(versionMap);
				} else {
					DBObjViewModel dbObjViewModel = new DBObjViewModel();
					dbObjViewModel.setObjName(objModl.getName());
					dbObjViewModel.setObjType(objModl.getType());
					Map<String, String> versionMap = new HashMap<String, String>();
					versionMap.put(objModl.getDbName(), objModl.getVersion());
					dbObjViewModel.setVersionMap(versionMap);
					objectMap.put(objModl.getType() + "#" + objModl.getName(),
							dbObjViewModel);
				}
			}
		}

		if (!objectMap.isEmpty()) {
			for (DBObjViewModel dbObjViewModel : objectMap.values()) {
				Map<String, String> versionMap = dbObjViewModel.getVersionMap();
				if (DBListCnt != versionMap.size()) {
					dbObjViewModel.setColorFlag("Red");
				}
			}
		}

		return objectMap;
	}

	/**
	 * Compare struct.
	 * 
	 * @param dbNameList
	 *            the db name list
	 * @param structCompType
	 *            the struct comp type
	 * @param usedId
	 *            the used id
	 * @return the map
	 * @throws DBPPBusinessException
	 *             the dBPP business exception
	 */
	public List<DBTableNameModel> compareStruct(List<String> dbNameList,
			String structCompType, String usedId) throws DBPPBusinessException {

		List<Object> db1List = new ArrayList<Object>();
		List<Object> db2List = new ArrayList<Object>();
		List<DBTableNameModel> dbTblNameMdlLst = new ArrayList<DBTableNameModel>();
		int i = 0;
		try {
			for (String dbName : dbNameList) {
				DBConfigDetailsModel configModel = dbConfigDetailsService
						.getDBConfigDetail(dbName, usedId);
				TransactionModel transModel = new TransactionModel();
				transModel.setServerName(configModel.getServerName());
				transModel.setPortNumber(configModel.getPortNumber());
				transModel.setSid(configModel.getSid());
				transModel.setUsername(configModel.getUserName());
				transModel.setPassword(configModel.getPassWord());
				transModel.setDbName(configModel.getDbName());
				transModel.setResultSetClass("java.lang.String");

				transModel
						.setQuery("select distinct a.object_name from all_objects a, user_tab_cols b where a.object_name = b.table_name and a.owner='"
								+ configModel.getUserName()
								+ "'and a.object_type = '"
								+ structCompType
								+ "' order by a.object_name asc");

				// transModel.setQuery("select a.table_name,column_name,data_type,data_length,ROW_NUMBER() OVER ( PARTITION BY a.table_name ORDER BY b.column_id) ROWC "
				// +" from user_tables a, user_tab_cols b where a.table_name = b.table_name order by a.table_name asc");

				List<Object> dbObjListTemp = new ArrayList<Object>();
				dbObjListTemp = transactionManager.getData(transModel);
				if (i == 0) {
					db1List = dbObjListTemp;
					i++;
				} else {
					db2List = dbObjListTemp;
				}

			}

			// Compare table is restricted only for two schema. This is for the
			// user
			// readability.
			dbTblNameMdlLst = compareTableStruct(db1List, db2List, dbNameList,
					structCompType, usedId);
		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}

		return dbTblNameMdlLst;

	}

	// Compare table is restricted only for two schema. This is for the user
	// readability.
	/**
	 * Compare table struct.
	 * 
	 * @param db1Lst
	 *            the db1 lst
	 * @param db2Lst
	 *            the db2 lst
	 * @param dbNameList
	 *            the db name list
	 * @param structCompType
	 *            the struct comp type
	 * @param usedId
	 *            the used id
	 * @return the map
	 */
	private List<DBTableNameModel> compareTableStruct(List<Object> db1Lst,
			List<Object> db2Lst, List<String> dbNameList,
			String structCompType, String usedId) {

		List<DBTableNameModel> dbTblNameMdlLst = new ArrayList<DBTableNameModel>();
		List<String> tblNameCompList = new ArrayList<String>();

		for (Object obj1 : db1Lst) {
			DBTableNameModel dbObj = new DBTableNameModel();
			String tbl1 = (String) obj1;
			dbObj.setDbTableName1(tbl1);
			boolean flag = false;
			for (Object obj2 : db2Lst) {
				String tbl2 = (String) obj2;
				if (tbl1.equals(tbl2)) {
					flag = true;
					tblNameCompList.add(tbl2);
					break;
				}
			}

			if (!flag) {
				dbObj.setDbTableName2(null);
				dbObj.setColorFlag("Red");
				dbTblNameMdlLst.add(dbObj);
			}

		}

		db2Lst.removeAll(tblNameCompList);
		for (Object obj2 : db2Lst) {
			String tbl2 = (String) obj2;
			DBTableNameModel dbObj = new DBTableNameModel();
			dbObj.setDbTableName1(null);
			dbObj.setDbTableName2(tbl2);
			dbObj.setColorFlag("Red");
			dbTblNameMdlLst.add(dbObj);
		}

		List<DBTableNameModel> tableCompModel = compareStructTblFields(
				dbNameList, structCompType, usedId);

		dbTblNameMdlLst.addAll(tableCompModel);

		return dbTblNameMdlLst;
	}

	/**
	 * Compare struct fields.
	 * 
	 * @param dbNameList
	 *            the db name list
	 * @param structCompType
	 *            the struct comp type
	 * @param usedId
	 *            the used id
	 * @return the list
	 * @throws DBPPBusinessException
	 *             the dBPP business exception
	 */
	public List<DBTableNameModel> compareStructTblFields(
			List<String> dbNameList, String structCompType, String usedId)
			throws DBPPBusinessException {

		Map<String, TableFieldMappingModel> tableFieldMap1 = new HashMap<String, TableFieldMappingModel>();
		Map<String, TableFieldMappingModel> tableFieldMap2 = new HashMap<String, TableFieldMappingModel>();
		List<DBTableNameModel> tblNameCompList = new ArrayList<DBTableNameModel>();
		int i = 0;

		try {

			for (String dbName : dbNameList) {
				DBConfigDetailsModel configModel = dbConfigDetailsService
						.getDBConfigDetail(dbName, usedId);
				TransactionModel transModel = new TransactionModel();
				transModel.setServerName(configModel.getServerName());
				transModel.setPortNumber(configModel.getPortNumber());
				transModel.setSid(configModel.getSid());
				transModel.setUsername(configModel.getUserName());
				transModel.setPassword(configModel.getPassWord());
				transModel.setDbName(configModel.getDbName());
				transModel
						.setResultSetClass("com.hp.dbpowerpack.Model.DBTableStructModel");

				String[] resultArr = { "setTableName", "setFieldName",
						"setDataType", "setDataLength", "setRowCnt" };
				transModel.setResultSetFieldMappings(resultArr);

				transModel
						.setQuery("select a.object_name,column_name,data_type,data_length,ROW_NUMBER() OVER ( PARTITION BY a.object_name ORDER BY b.column_id) "
								+ "ROWC from all_objects a, user_tab_cols b where a.object_name = b.table_name and a.owner='"
								+ configModel.getUserName()
								+ "'and a.object_type = '"
								+ structCompType
								+ "' order by a.object_name asc");

				// transModel.setQuery("select a.table_name,column_name,data_type,data_length,ROW_NUMBER() OVER ( PARTITION BY a.table_name ORDER BY b.column_id) ROWC "
				// +" from user_tables a, user_tab_cols b where a.table_name = b.table_name order by a.table_name asc");

				List<Object> dbObjListTemp = new ArrayList<Object>();
				dbObjListTemp = transactionManager.getData(transModel);
				Map<String, TableFieldMappingModel> tableFieldMap = new HashMap<String, TableFieldMappingModel>();

				for (Object obj : dbObjListTemp) {
					DBTableStructModel model = (DBTableStructModel) obj;
					if (tableFieldMap.containsKey(model.getTableName())) {
						TableFieldMappingModel fieldModel = tableFieldMap
								.get(model.getTableName());
						fieldModel.getFieldList().add(model);
						tableFieldMap.put(model.getTableName(), fieldModel);
					} else {
						TableFieldMappingModel fieldModel = new TableFieldMappingModel();
						fieldModel.setTableName(model.getTableName());
						fieldModel.getFieldList().add(model);
						tableFieldMap.put(model.getTableName(), fieldModel);
					}

				}

				if (i == 0) {
					tableFieldMap1 = tableFieldMap;
					i++;
				} else {
					tableFieldMap2 = tableFieldMap;
				}

			}

			// Compare table is restricted only for two schema. This is for the
			// user
			// readability.
			tblNameCompList = compareColumnStructMap(tableFieldMap1,
					tableFieldMap2);

		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}

		return tblNameCompList;

	}

	/**
	 * Compare column struct map.
	 * 
	 * @param tableFieldMap1
	 *            the table field map1
	 * @param tableFieldMap2
	 *            the table field map2
	 * @return the list
	 */
	private List<DBTableNameModel> compareColumnStructMap(
			Map<String, TableFieldMappingModel> tableFieldMap1,
			Map<String, TableFieldMappingModel> tableFieldMap2) {

		List<DBTableStructModel> tblNameCompList = new ArrayList<DBTableStructModel>();
		List<DBTableNameModel> dbTblCompMdlLst = new ArrayList<DBTableNameModel>();

		for (String key : tableFieldMap1.keySet()) {
			if (tableFieldMap2.containsKey(key)) {
				TableFieldMappingModel model1 = tableFieldMap1.get(key);
				TableFieldMappingModel model2 = tableFieldMap2.get(key);

				List<DBTableStructModel> db1List = model1.getFieldList();
				List<DBTableStructModel> db2List = model2.getFieldList();
				DBTableNameModel tblObj = new DBTableNameModel();
				tblObj.setDbTableName1(model1.getTableName());
				tblObj.setDbTableName2(model2.getTableName());
				boolean flagSame = true;
				for (Object obj1 : db1List) {
					DBTableStructModel tbl1 = (DBTableStructModel) obj1;
					for (Object obj2 : db2List) {
						DBTableStructModel tbl2 = (DBTableStructModel) obj2;
						if ((tbl1.getTableName() != null && tbl1.getTableName()
								.equals(tbl2.getTableName()))
								&& (tbl1.getFieldName() != null && tbl1
										.getFieldName().equals(
												tbl2.getFieldName()))) {
							if (!((tbl1.getDataType() != null && tbl1
									.getDataType().equals(tbl2.getDataType())) && (tbl1
									.getDataLength() != null && tbl1
									.getDataLength().doubleValue() == tbl2
									.getDataLength().doubleValue()))) {
								flagSame = false;
							}

							tblNameCompList.add(tbl2);
							break;
						}
					}
				}

				db2List.removeAll(tblNameCompList);

				if (db2List != null && !db2List.isEmpty()) {
					flagSame = false;
				}

				if (!flagSame) {
					tblObj.setColorFlag("Green");
				}

				dbTblCompMdlLst.add(tblObj);

			}
		}

		return dbTblCompMdlLst;
	}

	/**
	 * Compare struct fields.
	 * 
	 * @param dbNameList
	 *            the db name list
	 * @param tableName
	 *            the table name
	 * @param usedId
	 *            the used id
	 * @return the list
	 * @throws DBPPBusinessException
	 *             the dBPP business exception
	 */
	public List<DBTableNameModel> compareStructFields(List<String> dbNameList,
			String tableName, String usedId) throws DBPPBusinessException {

		List<Object> db1List = new ArrayList<Object>();
		List<Object> db2List = new ArrayList<Object>();
		List<DBTableNameModel> dbTableNameLst = new ArrayList<DBTableNameModel>();
		int i = 0;

		try {

			for (String dbName : dbNameList) {
				DBConfigDetailsModel configModel = dbConfigDetailsService
						.getDBConfigDetail(dbName, usedId);
				TransactionModel transModel = new TransactionModel();
				transModel.setServerName(configModel.getServerName());
				transModel.setPortNumber(configModel.getPortNumber());
				transModel.setSid(configModel.getSid());
				transModel.setUsername(configModel.getUserName());
				transModel.setPassword(configModel.getPassWord());
				transModel.setDbName(configModel.getDbName());
				transModel
						.setResultSetClass("com.hp.dbpowerpack.Model.DBTableStructModel");

				String[] resultArr = { "setTableName", "setFieldName",
						"setDataType", "setDataLength", "setRowCnt" };
				transModel.setResultSetFieldMappings(resultArr);

				transModel
						.setQuery("select table_name,column_name,data_type,data_length,ROW_NUMBER() OVER ( PARTITION BY table_name ORDER BY column_id) ROWC "
								+ " from user_tab_cols  where table_name='"
								+ tableName + "' order by table_name asc");

				List<Object> dbObjListTemp = new ArrayList<Object>();
				dbObjListTemp = transactionManager.getData(transModel);
				if (i == 0) {
					db1List = dbObjListTemp;
					i++;
				} else {
					db2List = dbObjListTemp;
				}

			}

			// Compare table is restricted only for two schema. This is for the
			// user
			// readability.
			dbTableNameLst = compareColumnStruct(db1List, db2List);
		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}
		return dbTableNameLst;

	}

	/**
	 * Compare column struct.
	 * 
	 * @param db1List
	 *            the db1 list
	 * @param db2List
	 *            the db2 list
	 * @return the list
	 */
	private List<DBTableNameModel> compareColumnStruct(List<Object> db1List,
			List<Object> db2List) {

		List<DBTableNameModel> dbTblNameMdlLst = new ArrayList<DBTableNameModel>();
		List<DBTableStructModel> tblNameCompList = new ArrayList<DBTableStructModel>();

		for (Object obj1 : db1List) {
			DBTableNameModel dbObj = new DBTableNameModel();
			DBTableStructModel tbl1 = (DBTableStructModel) obj1;
			dbObj.setDbTableName1(tbl1.getTableName());
			dbObj.setDbFieldName1(tbl1.getFieldName());
			dbObj.setDbDataType1(tbl1.getDataType());
			if (tbl1.getDataLength() != null) {
				dbObj.setDbDataLength1(tbl1.getDataLength().toString());
			} else {
				dbObj.setDbDataLength1("");
			}

			boolean flag = false;
			boolean flagAvailable = false;
			for (Object obj2 : db2List) {
				DBTableStructModel tbl2 = (DBTableStructModel) obj2;
				if ((tbl1.getTableName() != null && tbl1.getTableName().equals(
						tbl2.getTableName()))
						&& (tbl1.getFieldName() != null && tbl1.getFieldName()
								.equals(tbl2.getFieldName()))) {
					flagAvailable= true;
					dbObj.setDbTableName2(tbl2.getTableName());
					dbObj.setDbFieldName2(tbl2.getFieldName());
					dbObj.setDbDataType2(tbl2.getDataType());
					if (tbl1.getDataLength() != null) {
						dbObj.setDbDataLength2(tbl2.getDataLength().toString());
					} else {
						dbObj.setDbDataLength2("");
					}

					if ((tbl1.getDataType() != null && tbl1.getDataType()
							.equals(tbl2.getDataType()))
							&& (tbl1.getDataLength() != null && tbl1
									.getDataLength().equals(
											tbl2.getDataLength()))) {
						flag = true;

					}

					tblNameCompList.add(tbl2);
					break;
				}
			}

			if (!flag) {
				if(flagAvailable){
					dbObj.setColorFlag("Green");
				}else{
					dbObj.setColorFlag("Red");
				}
				
			}
			dbTblNameMdlLst.add(dbObj);
		}

		db2List.removeAll(tblNameCompList);

		for (Object obj2 : db2List) {
			DBTableStructModel tbl2 = (DBTableStructModel) obj2;
			DBTableNameModel dbObj = new DBTableNameModel();
			dbObj.setDbTableName1(null);
			dbObj.setDbFieldName1(null);
			dbObj.setDbDataType1(null);
			dbObj.setDbDataLength1(null);
			dbObj.setDbTableName2(tbl2.getTableName());
			dbObj.setDbFieldName2(tbl2.getFieldName());
			dbObj.setDbDataType2(tbl2.getDataType());
			if (tbl2.getDataLength() != null) {
				dbObj.setDbDataLength2(tbl2.getDataLength().toString());
			} else {
				dbObj.setDbDataLength2("");
			}
			dbObj.setColorFlag("Red");
			dbTblNameMdlLst.add(dbObj);
		}

		return dbTblNameMdlLst;
	}

	/**
	 * Compare struct.
	 * 
	 * @param dbNameList
	 *            the db name list
	 * @param selectedObjects
	 *            the selected objects
	 * @param usedId
	 *            the used id
	 * @return the map
	 * @throws DBPPBusinessException
	 *             the dBPP business exception
	 */
	public List<DBTableNameModel> compareObjects(List<String> dbNameList,
			String selectedObjects, String usedId) throws DBPPBusinessException {

		List<Object> db1List = new ArrayList<Object>();
		List<Object> db2List = new ArrayList<Object>();
		List<DBTableNameModel> dbTblNameMdlLst = new ArrayList<DBTableNameModel>();
		int i = 0;
		try {

			for (String dbName : dbNameList) {
				DBConfigDetailsModel configModel = dbConfigDetailsService
						.getDBConfigDetail(dbName, usedId);
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
						.setQuery("select object_type,object_name from all_objects where owner='"
								+ configModel.getUserName()
								+ "'and object_type in ('"
								+ selectedObjects
								+ "') order by object_name asc");

				// transModel.setQuery("select a.table_name,column_name,data_type,data_length,ROW_NUMBER() OVER ( PARTITION BY a.table_name ORDER BY b.column_id) ROWC "
				// +" from user_tables a, user_tab_cols b where a.table_name = b.table_name order by a.table_name asc");

				List<Object> dbObjListTemp = new ArrayList<Object>();
				dbObjListTemp = transactionManager.getData(transModel);
				if (i == 0) {
					db1List = dbObjListTemp;
					i++;
				} else {
					db2List = dbObjListTemp;
				}

			}

			// Compare table is restricted only for two schema. This is for the
			// user
			// readability.
			dbTblNameMdlLst = compareObject(db1List, db2List, dbNameList);
		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}

		return dbTblNameMdlLst;

	}

	// Compare table is restricted only for two schema. This is for the user
	// readability.
	/**
	 * Compare table struct.
	 * 
	 * @param db1Lst
	 *            the db1 lst
	 * @param db2Lst
	 *            the db2 lst
	 * @param dbNameList
	 *            the db name list
	 * @return the map
	 */
	public List<DBTableNameModel> compareObject(List<Object> db1Lst,
			List<Object> db2Lst, List<String> dbNameList) {

		List<DBTableNameModel> dbTblNameMdlLst = new ArrayList<DBTableNameModel>();
		List<DBObjectDetailModel> tblNameCompList = new ArrayList<DBObjectDetailModel>();

		for (Object obj1 : db1Lst) {
			DBTableNameModel dbObj = new DBTableNameModel();
			DBObjectDetailModel tbl1 = (DBObjectDetailModel) obj1;
			dbObj.setDbTableName1(tbl1.getName());
			dbObj.setObjectType(tbl1.getType());
			boolean flag = false;
			for (Object obj2 : db2Lst) {
				DBObjectDetailModel tbl2 = (DBObjectDetailModel) obj2;
				if (((tbl1.getName() != null) && tbl1.getName().equals(
						tbl2.getName()))
						&& ((tbl1.getType() != null) && tbl1.getType().equals(
								tbl2.getType()))) {
					flag = true;
					tblNameCompList.add(tbl2);
					dbObj.setDbTableName2(tbl2.getName());
					break;
				}
			}

			if (!flag) {
				dbObj.setDbTableName2(null);
				dbObj.setColorFlag("Red");
			}
			dbTblNameMdlLst.add(dbObj);
		}

		db2Lst.removeAll(tblNameCompList);
		for (Object obj2 : db2Lst) {
			DBObjectDetailModel tbl2 = (DBObjectDetailModel) obj2;
			DBTableNameModel dbObj = new DBTableNameModel();
			dbObj.setDbTableName1(null);
			dbObj.setDbTableName2(tbl2.getName());
			dbObj.setObjectType(tbl2.getType());
			dbObj.setColorFlag("Red");
			dbTblNameMdlLst.add(dbObj);
		}

		return dbTblNameMdlLst;
	}

	/**
	 * Compare object source.
	 * 
	 * @param dbNameList
	 *            the db name list
	 * @param selectedObjects
	 *            the selected objects
	 * @param userId
	 *            the user id
	 * @return the list
	 * @throws DBPPBusinessException
	 *             the dBPP business exception
	 */
	public Map<String, Object> compareObjectSource(List<String> dbNameList,
			String selectedObjects, String userId) throws DBPPBusinessException {

		Map<String, Object> sourceMap = new HashMap<String, Object>();
		List<Object> db1List = new ArrayList<Object>();
		List<Object> db2List = new ArrayList<Object>();
		List<Object> db1SourceList = new ArrayList<Object>();
		List<Object> db2SourceList = new ArrayList<Object>();
		String db1 = null;
		String db2 = null;
		try {

			int i = 0;
			for (String dbName : dbNameList) {
				List<String> queryList = new ArrayList<String>();
				DBConfigDetailsModel configModel = dbConfigDetailsService
						.getDBConfigDetail(dbName, userId);
				TransactionModel transModel = new TransactionModel();
				transModel.setServerName(configModel.getServerName());
				transModel.setPortNumber(configModel.getPortNumber());
				transModel.setSid(configModel.getSid());
				transModel.setUsername(configModel.getUserName());
				transModel.setPassword(configModel.getPassWord());
				transModel.setDbName(configModel.getDbName());
				transModel
						.setResultSetClass("com.hp.dbpowerpack.Model.DBObjectDetailModel");

				String[] resultArr = { "setType", "setName", "setLine",
						"setSource", "setDbName" };
				transModel.setResultSetFieldMappings(resultArr);

				queryList.add("select type,name , 0 lineNum, '' srcTxt, '"
						+ configModel.getDbName()
						+ "'  as DBName  FROM all_source where owner  = '"
						+ configModel.getUserName() + "' and type in ('"
						+ selectedObjects + "') group by type,name "
						+ " order by type,name");

				queryList.add("SELECT type, name,line,text , '"
						+ configModel.getDbName()
						+ "'  as DBName  FROM all_source where owner  = '"
						+ configModel.getUserName() + "'" + " and type in ('"
						+ selectedObjects + "') order by type,name,line");
				transModel.setListQuery(true);
				transModel.setQueryList(queryList);
				transModel.setQueryType("ObjectSource");

				// transModel.setQuery("select a.table_name,column_name,data_type,data_length,ROW_NUMBER() OVER ( PARTITION BY a.table_name ORDER BY b.column_id) ROWC "
				// +" from user_tables a, user_tab_cols b where a.table_name = b.table_name order by a.table_name asc");

				List<Object> dbObjListTemp = new ArrayList<Object>();
				dbObjListTemp = transactionManager.getData(transModel);
				if (i == 0) {
					db1List = (List) dbObjListTemp.get(0);
					db1SourceList = (List) dbObjListTemp.get(1);
					db1 = dbName;
					i++;
				} else {
					db2List = (List) dbObjListTemp.get(0);
					db2SourceList = (List) dbObjListTemp.get(1);
					db2 = dbName;
					break;
				}

			}

			// Compare table is restricted only for two schema. This is for the
			// user
			// readability.
			Map<String, Object> objectSrcMap = new HashMap<String, Object>();
			objectSrcMap.put(db1, db1SourceList);
			objectSrcMap.put(db2, db2SourceList);
			sourceMap = compareObjectSource(db1List, db2List, objectSrcMap,
					db1, db2);

		} catch (DBPPDaoException ex) {
			throw new DBPPBusinessException(ex);
		}
		return sourceMap;

	}

	/**
	 * Compare object source.
	 * 
	 * @param db1Lst
	 *            the db1 lst
	 * @param db2Lst
	 *            the db2 lst
	 * @param objectSourceMap
	 *            the object source map
	 * @param db1
	 *            the db1
	 * @param db2
	 *            the db2
	 * @return the list
	 */
	private Map<String, Object> compareObjectSource(List<Object> db1Lst,
			List<Object> db2Lst, Map<String, Object> objectSourceMap,
			String db1, String db2) {

		Map<String, Object> sourceMap = new HashMap<String, Object>();
		List<DBTableNameModel> dbobjModlFinal = new ArrayList<DBTableNameModel>();
		List<DBObjectDetailModel> dbObjSameLst = new ArrayList<DBObjectDetailModel>();
		List<DBTableNameModel> availableObjectLst = new ArrayList<DBTableNameModel>();

		for (Object obj1 : db1Lst) {
			DBTableNameModel dbObj = new DBTableNameModel();
			DBObjectDetailModel tbl1 = (DBObjectDetailModel) obj1;
			dbObj.setDbTableName1(tbl1.getName());
			dbObj.setObjectType(tbl1.getType());
			dbObj.setDbName1(tbl1.getDbName());
			boolean flag = false;
			for (Object obj2 : db2Lst) {
				DBObjectDetailModel tbl2 = (DBObjectDetailModel) obj2;
				if (((tbl1.getName() != null) && tbl1.getName().equals(
						tbl2.getName()))
						&& ((tbl1.getType() != null) && tbl1.getType().equals(
								tbl2.getType()))) {
					flag = true;
					dbObjSameLst.add(tbl2);
					dbObj.setDbTableName2(tbl2.getName());
					dbObj.setDbName2(tbl2.getDbName());
					break;
				}
			}

			if (!flag) {
				dbObj.setDbTableName2(null);
				dbObj.setDbName2(null);
				dbObj.setColorFlag("Red");
				dbobjModlFinal.add(dbObj);
			} else {
				availableObjectLst.add(dbObj);
			}

		}

		db2Lst.removeAll(dbObjSameLst);
		for (Object obj2 : db2Lst) {
			DBObjectDetailModel tbl2 = (DBObjectDetailModel) obj2;
			DBTableNameModel dbObj = new DBTableNameModel();
			dbObj.setDbTableName1(null);
			dbObj.setDbTableName2(tbl2.getName());
			dbObj.setObjectType(tbl2.getType());
			dbObj.setDbName2(tbl2.getDbName());
			dbObj.setColorFlag("Red");
			dbobjModlFinal.add(dbObj);
		}

		Map<String, Object> objectMap = compareAvailableObject(objectSourceMap,
				availableObjectLst, db1, db2);
		dbobjModlFinal.addAll((List) objectMap.get("AvailableObjLstCompare"));

		sourceMap.put("ObjectList", dbobjModlFinal);
		sourceMap.put(db1, objectMap.get(db1));
		sourceMap.put(db2, objectMap.get(db2));
		return sourceMap;
	}

	/**
	 * Compare available object.
	 * 
	 * @param objectSourceMap
	 *            the object source map
	 * @param availableObjectLst
	 *            the available object lst
	 * @param db1
	 *            the db1
	 * @param db2
	 *            the db2
	 * @return the list
	 */
	private Map<String, Object> compareAvailableObject(
			Map<String, Object> objectSourceMap,
			List<DBTableNameModel> availableObjectLst, String db1, String db2) {
		Map<String, Object> sourceMap = new HashMap<String, Object>();
		List<DBTableNameModel> dbobjModlTemp = new ArrayList<DBTableNameModel>();
		Map<String, List<DBObjectDetailModel>> dbSource1Map = convertListToMap((List) objectSourceMap
				.get(db1));
		Map<String, List<DBObjectDetailModel>> dbSource2Map = convertListToMap((List) objectSourceMap
				.get(db2));
		for (DBTableNameModel dbTblModel : availableObjectLst) {
			List<DBObjectDetailModel> dbSource1TempLst = dbSource1Map
					.get(dbTblModel.getObjectType() + "#"
							+ dbTblModel.getDbTableName1());
			List<DBObjectDetailModel> dbSource2TempLst = dbSource2Map
					.get(dbTblModel.getObjectType() + "#"
							+ dbTblModel.getDbTableName1());
			boolean isSameFlag = false;
			for (DBObjectDetailModel objMdl1 : dbSource1TempLst) {
				for (DBObjectDetailModel objMdl2 : dbSource2TempLst) {
					if (objMdl1.getLine().equals(objMdl2.getLine())) {
						if (!objMdl1.getSource().equals(objMdl2.getSource())) {
							isSameFlag = true;
							break;
						}
					}
				}
			}

			if (isSameFlag) {
				dbTblModel.setColorFlag("Green");
			}
			dbTblModel.setDbName1(db1);
			dbTblModel.setDbName2(db2);
			dbobjModlTemp.add(dbTblModel);
		}
		sourceMap.put(db1, dbSource1Map);
		sourceMap.put(db2, dbSource2Map);
		sourceMap.put("AvailableObjLstCompare", dbobjModlTemp);
		return sourceMap;
	}

	/**
	 * Convert list to map.
	 * 
	 * @param dbSourceList
	 *            the db source list
	 * @return the map
	 */
	private Map<String, List<DBObjectDetailModel>> convertListToMap(
			List<Object> dbSourceList) {
		Map<String, List<DBObjectDetailModel>> dbSourceMap = new HashMap<String, List<DBObjectDetailModel>>();

		for (Object obj : dbSourceList) {
			DBObjectDetailModel dbObj = (DBObjectDetailModel) obj;
			if (dbSourceMap.get(dbObj.getType() + "#" + dbObj.getName()) != null) {
				List<DBObjectDetailModel> objLst = dbSourceMap.get(dbObj
						.getType() + "#" + dbObj.getName());
				objLst.add(dbObj);
				Collections.sort(objLst);
				dbSourceMap.put((dbObj.getType() + "#" + dbObj.getName()),
						objLst);
			} else {
				List<DBObjectDetailModel> objLst = new ArrayList<DBObjectDetailModel>();
				objLst.add(dbObj);
				Collections.sort(objLst);
				dbSourceMap.put((dbObj.getType() + "#" + dbObj.getName()),
						objLst);
			}
		}

		return dbSourceMap;
	}
}
