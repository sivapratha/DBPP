package com.hp.dbpowerpack.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.hp.dbpowerpack.Model.DBConfigDetailsModel;
import com.hp.dbpowerpack.Model.DBObjViewModel;
import com.hp.dbpowerpack.Model.DBObjectDetailModel;
import com.hp.dbpowerpack.Model.DBTableNameModel;
import com.hp.dbpowerpack.Model.ObjCodeViewModel;
import com.hp.dbpowerpack.common.action.DBPPBaseAction;
import com.hp.dbpowerpack.common.exception.DBPPBusinessException;
import com.hp.dbpowerpack.common.model.ExcelModel;
import com.hp.dbpowerpack.common.model.MailModel;
import com.hp.dbpowerpack.common.model.ViewCompareDiffModel;
import com.hp.dbpowerpack.common.util.DBPPCompareUtil;
import com.hp.dbpowerpack.common.util.ExcelUtil;
import com.hp.dbpowerpack.service.CompareDBService;
import com.hp.dbpowerpack.service.DBConfigDetailsService;
import com.hp.dbpowerpack.service.MailService;

/**
 * The Class CompareDBAction.
 */
public class CompareDBAction extends DBPPBaseAction {

	/** The Constant logger. */
	private static final Logger LOGGER = Logger
			.getLogger(CompareDBAction.class);

	/**
	 * Inits the.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the action forward
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");
			@SuppressWarnings("deprecation")
			DBConfigDetailsService dbConfigDetailsService = (DBConfigDetailsService) getWebApplicationContext()
					.getBean("dbConfigDetailsService");
			List<DBConfigDetailsModel> dbNameList = dbConfigDetailsService
					.getdbUserNames(userId);
			dynaForm.set("isSelected", false);
			dynaForm.set("selectAll", false);
			dynaForm.set("compareType", "");
			dynaForm.set("slctdDbObj", "");
			dynaForm.set("dbNameList", dbNameList);
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}

		return mapping.findForward("success");
	}

	/**
	 * Load.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the action forward
	 */
	public ActionForward load(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");
			@SuppressWarnings("deprecation")
			DBConfigDetailsService dbConfigDetailsService = (DBConfigDetailsService) getWebApplicationContext()
					.getBean("dbConfigDetailsService");
			List<DBConfigDetailsModel> dbNameList = dbConfigDetailsService
					.getdbUserNames(userId);
			String selectedObjects = (String) dynaForm.get("slctdDbObj");
			LOGGER.info("selected slctdDbObj " + selectedObjects);
			String selectedDb = (String) dynaForm.get("selectedDb");
			LOGGER.info("selected list" + selectedDb);
			String[] selectedDBArr = selectedDb.split(",");
			List<String> selectedDbList = new ArrayList<String>();
			for (int i = 0; i < selectedDBArr.length; i++) {
				selectedDbList.add(selectedDBArr[i]);
			}

			dynaForm.set("selectedDb", selectedDb);
			dynaForm.set("slctdDbObj", selectedObjects);
			dynaForm.set("dbNameList", dbNameList);
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
	}

	/**
	 * Object content compare load.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the action forward
	 */
	public ActionForward objectContentCompareLoad(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;
			String selectedDb = (String) dynaForm.get("selectedDb");
			String selectedObjects = (String) dynaForm.get("slctdDbObj");
			String structCompType = (String) dynaForm.get("structCompType");
			dynaForm.set("slctdDbObj", selectedObjects);
			dynaForm.set("selectedDb", selectedDb);
			dynaForm.set("structCompType", structCompType);
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
	}

	/**
	 * Object cont comp rslt.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the action forward
	 */
	public ActionForward objectContCompRslt(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;
			List<DBObjViewModel> dbObjTblNameList = (List<DBObjViewModel>) dynaForm
					.get("dbObjTblNameList");

			List<String> dbNameList = (List<String>) dynaForm.get("dbNameList");
			String structCompType = (String) dynaForm.get("structCompType");
			LOGGER.info("dbObjTblNameList size->" + dbObjTblNameList.size());
			LOGGER.info("dbNameList size->" + dbNameList.size());
			LOGGER.info("structCompType->" + structCompType);
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
	}

	/**
	 * Compare version.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the action forward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward compareVersion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("Inside  CompareDBAction init Method");
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			String selectedObjects = (String) dynaForm.get("slctdDbObj");
			LOGGER.info("selected slctdDbObj " + selectedObjects);

			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");

			String selectedDb = (String) dynaForm.get("selectedDb");
			LOGGER.info("selected list" + selectedDb);
			String[] selectedDBArr = selectedDb.split(",");
			List<String> dbNameList = new ArrayList<String>();
			for (int i = 0; i < selectedDBArr.length; i++) {
				dbNameList.add(selectedDBArr[i]);
			}

			CompareDBService compareDBService = getService();

			List<DBObjViewModel> dbObjViewList = new ArrayList<DBObjViewModel>();
			Map<String, DBObjViewModel> objectMap = compareDBService.compare(
					dbNameList, selectedObjects, userId);
			dbObjViewList.addAll(objectMap.values());
			Collections.sort(dbObjViewList);

			String fileName = "VersionComp" + userId + ".xls";
			// create WorkbookSettings object

			ExcelModel model = new ExcelModel();
			model.setFileName(fileName);
			model.setSheetName("Version comaparison");
			model.setObjLst((List) dbObjViewList);
			model.setObjectClass("com.hp.dbpowerpack.Model.DBObjViewModel");

			List<String> headerList = new ArrayList<String>();
			headerList.add("objType");
			headerList.add("objName");
			headerList.addAll(dbNameList);

			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("objType");
			fieldNameList.add("objName");
			fieldNameList.add("versionMap");
			model.setFieldNameList(fieldNameList);
			model.setDbNameHeader(false);
			model.setColumnCount(0);
			model.setHeaderList(headerList);
			model.setDbNameList(dbNameList);
			model.setDbNameHeader(false);
			model.setDBHeader(true);

			model.setColorFlag(true);

			dynaForm.set("excelModel", model);
			dynaForm.set("actionName", "viewCompare");

			dynaForm.set("dbObjViewList", dbObjViewList);
			dynaForm.set("dbNameList", dbNameList);
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
	}

	/**
	 * Gets the service.
	 * 
	 * @return the service
	 */
	public CompareDBService getService() {
		@SuppressWarnings("deprecation")
		CompareDBService compareDBService = (CompareDBService) getWebApplicationContext()
				.getBean("compareDBService");
		return compareDBService;
	}

	/**
	 * Compare table struct.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the action forward
	 */
	public ActionForward compareTableStruct(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");

			String selectedDb = (String) dynaForm.get("selectedDb");
			LOGGER.info("selected list" + selectedDb);
			String[] selectedDBArr = selectedDb.split(",");
			List<String> dbNameList = new ArrayList<String>();
			for (int i = 0; i < selectedDBArr.length; i++) {
				dbNameList.add(selectedDBArr[i]);
			}

			String slctdDbObj = (String) dynaForm.get("slctdDbObj");

			CompareDBService compareDBService = getService();

			List<DBTableNameModel> objTblLst = compareDBService.compareStruct(
					dbNameList, slctdDbObj, userId);

			String fileName = "TableComp" + userId + ".xls";
			// create WorkbookSettings object

			ExcelModel model = new ExcelModel();
			model.setFileName(fileName);
			model.setSheetName("Table comaparison");
			model.setObjLst((List) objTblLst);
			model.setObjectClass("com.hp.dbpowerpack.Model.DBTableNameModel");
			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("dbTableName1");
			fieldNameList.add("dbTableName2");
			model.setFieldNameList(fieldNameList);
			model.setHeaderList(dbNameList);
			model.setDbNameList(dbNameList);
			model.setColorFlag(true);
			model.setDbNameHeader(false);
			model.setDBHeader(true);
			dynaForm.set("excelModel", model);
			dynaForm.set("actionName", "viewTableCompare");
			dynaForm.set("dbObjTblNameList", objTblLst);
			Collections.sort(objTblLst);
			dynaForm.set("dbNameList", dbNameList);
			dynaForm.set("selectedDb", selectedDb);
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
	}

	/**
	 * Compare fields struct.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the action forward
	 */
	public ActionForward compareFieldsStruct(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");

			String selectedDb = (String) dynaForm.get("selectedDb");
			LOGGER.info("selected list" + selectedDb);
			String[] selectedDBArr = selectedDb.split(",");
			List<String> dbNameList = new ArrayList<String>();
			for (int i = 0; i < selectedDBArr.length; i++) {
				dbNameList.add(selectedDBArr[i]);
			}

			String tableName = (String) dynaForm.get("tableName");

			CompareDBService compareDBService = getService();
			List<DBTableNameModel> dbFieldsList = compareDBService
					.compareStructFields(dbNameList, tableName, userId);
			Collections.sort(dbFieldsList);

			String fileName = tableName + "_" + userId + ".xls";
			// create WorkbookSettings object

			ExcelModel model = new ExcelModel();
			model.setFileName(fileName);
			model.setSheetName("Field comaparison");
			model.setObjLst((List) dbFieldsList);
			model.setObjectClass("com.hp.dbpowerpack.Model.DBTableNameModel");
			List<String> headerList = new ArrayList<String>();
			headerList.add("FieldName");
			headerList.add("DataType");
			headerList.add("DataLength");
			headerList.add("FieldName");
			headerList.add("DataType");
			headerList.add("DataLength");
			model.setDbNameHeader(true);
			model.setDBHeader(true);
			model.setColumnCount(2);
			model.setHeaderList(headerList);
			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("dbFieldName1");
			fieldNameList.add("dbDataType1");
			fieldNameList.add("dbDataLength1");
			fieldNameList.add("dbFieldName2");
			fieldNameList.add("dbDataType2");
			fieldNameList.add("dbDataLength2");
			model.setFieldNameList(fieldNameList);
			model.setDbNameList(dbNameList);

			model.setColorFlag(true);

			dynaForm.set("excelModel", model);
			dynaForm.set("actionName", "viewFieldsCompare");

			dynaForm.set("dbFieldsList", dbFieldsList);
			dynaForm.set("dbNameList", dbNameList);
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
	}

	/**
	 * Compare object.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the action forward
	 */
	public ActionForward compareObject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("Inside  CompareDBAction init Method");
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			String selectedObjects = (String) dynaForm.get("slctdDbObj");
			LOGGER.info("selected slctdDbObj " + selectedObjects);
			String structCompType = (String) dynaForm.get("structCompType");

			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");

			String selectedDb = (String) dynaForm.get("selectedDb");
			LOGGER.info("selected list" + selectedDb);
			String[] selectedDBArr = selectedDb.split(",");
			List<String> dbNameList = new ArrayList<String>();
			for (int i = 0; i < selectedDBArr.length; i++) {
				dbNameList.add(selectedDBArr[i]);
			}

			CompareDBService compareDBService = getService();

			List<DBTableNameModel> dbObjViewList = new ArrayList<DBTableNameModel>();
			dbObjViewList = compareDBService.compareObjects(dbNameList,
					selectedObjects, userId);
			Collections.sort(dbObjViewList);
			dynaForm.set("dbObjTblNameList", dbObjViewList);

			String fileName = "objectComp" + userId + ".xls";
			// create WorkbookSettings object

			ExcelModel model = new ExcelModel();
			model.setFileName(fileName);
			model.setSheetName("Version comaparison");
			model.setObjLst((List) dbObjViewList);
			model.setObjectClass("com.hp.dbpowerpack.Model.DBTableNameModel");
			model.setDbNameList(dbNameList);
			List<String> headerList = new ArrayList<String>();
			headerList.add("objectType");
			headerList.addAll(dbNameList);
			model.setDbNameHeader(false);
			model.setDBHeader(true);
			model.setColumnCount(0);
			model.setHeaderList(headerList);

			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("objectType");
			fieldNameList.add("dbTableName1");
			fieldNameList.add("dbTableName2");
			model.setFieldNameList(fieldNameList);

			model.setColorFlag(true);

			dynaForm.set("excelModel", model);
			dynaForm.set("actionName", "viewTableCompare");

			dynaForm.set("dbNameList", dbNameList);
			dynaForm.set("structCompType", structCompType);
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
	}

	/**
	 * Compare table excel.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the action forward
	 */
	public ActionForward compareTableExcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			ExcelModel model = (ExcelModel) dynaForm.get("excelModel");

			ExcelUtil.generateExcelReport(response, model);
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return null;
	}

	/**
	 * Send mail.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the action forward
	 */
	public ActionForward sendMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("Inside MailActionMailAction init method");
		String actionForward = "success";
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;
			final String to = (String) dynaForm.get("to");
			final String cc = (String) dynaForm.get("cc");
			final String subject = (String) dynaForm.get("subject");
			final String bodyContent = (String) dynaForm.get("bodyContent");
			final ExcelModel excelModel = (ExcelModel) dynaForm
					.get("excelModel");

			LOGGER.info("excel Model" + excelModel);
			LOGGER.info("excel Model file name" + excelModel.getFileName());

			MailModel model = new MailModel();
			model.setTo(to);
			model.setCc(cc);
			model.setSubject(subject);
			model.setBodyContent(bodyContent);

			File file = ExcelUtil.generateExcelReport(excelModel);

			model.setAttachment(file);

			MailService mailService = getMailService();
			mailService.sendEmail(model);
			actionForward = (String) dynaForm.get("actionName");
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward(actionForward);
	}

	/**
	 * Object content compare.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the action forward
	 */
	public ActionForward objectContentCompare(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;
			String structCompType = (String) dynaForm.get("structCompType");
			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");

			String selectedDb = (String) dynaForm.get("selectedDb");
			LOGGER.info("selected list" + selectedDb);
			String[] selectedDBArr = selectedDb.split(",");
			List<String> dbNameList = new ArrayList<String>();
			for (int i = 0; i < selectedDBArr.length; i++) {
				dbNameList.add(selectedDBArr[i]);
			}

			String selectedObjects = (String) dynaForm.get("slctdDbObj");
			LOGGER.info("selected slctdDbObj " + selectedObjects);
			CompareDBService compareDBService = getService();

			Map<String, Object> objectMap = compareDBService
					.compareObjectSource(dbNameList, selectedObjects, userId);

			List<DBTableNameModel> objTblLst = (List) objectMap
					.get("ObjectList");
			objectMap.remove("ObjectList");
			Map<String, Object> dbMap = new HashMap<String, Object>();
			dbMap.putAll(objectMap);
			dynaForm.set("dbMap", dbMap);
			Collections.sort(objTblLst);
			String fileName = "TableComp" + userId + ".xls";
			// create WorkbookSettings object

			ExcelModel model = new ExcelModel();
			model.setFileName(fileName);
			model.setSheetName("Table comaparison");
			model.setObjLst((List) objTblLst);
			model.setObjectClass("com.hp.dbpowerpack.Model.DBTableNameModel");
			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("dbTableName1");
			fieldNameList.add("dbTableName2");
			model.setFieldNameList(fieldNameList);
			model.setHeaderList(dbNameList);
			model.setDbNameList(dbNameList);
			model.setColorFlag(true);
			model.setDbNameHeader(false);
			model.setDBHeader(true);
			dynaForm.set("excelModel", model);
			dynaForm.set("actionName", "viewTableCompare");
			dynaForm.set("dbObjTblNameList", objTblLst);
			Collections.sort(objTblLst);
			dynaForm.set("dbNameList", dbNameList);
			dynaForm.set("selectedDb", selectedDb);
			dynaForm.set("structCompType", structCompType);

			LOGGER.info("dbObjTblNameList size->" + objTblLst.size());
			LOGGER.info("dbNameList size->" + dbNameList.size());
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
	}

	/**
	 * View code comparison.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the action forward
	 */
	public ActionForward viewCodeComparison(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");

			String tableName1 = (String) dynaForm.get("tableName1");
			String tableName2 = (String) dynaForm.get("tableName2");
			String ObjectType = (String) dynaForm.get("ObjectType");
			LOGGER.info("-tableName1-" + tableName1 + "-tableName2-"
					+ tableName2 + "-ObjectType-" + ObjectType);
			String selectedDb = (String) dynaForm.get("selectedDb");
			LOGGER.info("selectedDb-" + selectedDb);
			String[] selectedDBArr = selectedDb.split(",");
			List<String> dbNameList = new ArrayList<String>();
			for (int i = 0; i < selectedDBArr.length; i++) {
				dbNameList.add(selectedDBArr[i]);
			}
			Map<String, Object> dbMap = (Map) dynaForm.get("dbMap");
			Map<String, List<DBObjectDetailModel>> dbSource1Map = (Map) dbMap
					.get(dbNameList.get(0));
			Map<String, List<DBObjectDetailModel>> dbSource2Map = (Map) dbMap
					.get(dbNameList.get(1));

			List<DBObjectDetailModel> db1ObjLst = dbSource1Map.get(ObjectType
					+ "#" + tableName1);
			List<DBObjectDetailModel> db2ObjLst = dbSource2Map.get(ObjectType
					+ "#" + tableName2);

			// List<ObjCodeViewModel> combinedLst =
			// combinedLst(db1ObjLst,db2ObjLst);
			String[] db1SrcArr = convertToArr(db1ObjLst);
			String[] db2SrcArr = convertToArr(db2ObjLst);

			List<ViewCompareDiffModel> compareDiffModelList = DBPPCompareUtil
					.compare(db1SrcArr, db2SrcArr);
			String fileName = tableName1 + "_" + userId + ".xls";
			// create WorkbookSettings object

			ExcelModel model = new ExcelModel();
			model.setFileName(fileName);
			model.setSheetName("Field comaparison");
			model.setObjLst((List) compareDiffModelList);
			model.setObjectClass("com.hp.dbpowerpack.common.model.ViewCompareDiffModel");
			model.setDbNameHeader(true);
			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("oldLine");
			fieldNameList.add("newLine");
			model.setFieldNameList(fieldNameList);
			model.setDbNameList(dbNameList);

			model.setColorFlag(true);

			dynaForm.set("excelModel", model);
			dynaForm.set("actionName", "viewFieldsCompare");

			dynaForm.set("combinedLst", compareDiffModelList);
			if (tableName1 != null) {
				dynaForm.set("tableName", tableName1);
			} else {
				dynaForm.set("tableName", tableName2);
			}

			dynaForm.set("dbNameList", dbNameList);
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
	}

	/**
	 * Convert to arr.
	 * 
	 * @param dbObjLst
	 *            the db obj lst
	 * @return the string[]
	 */
	private String[] convertToArr(List<DBObjectDetailModel> dbObjLst) {
		List<String> strLst = new ArrayList<String>();

		if (dbObjLst != null) {
			for (DBObjectDetailModel dbMdl : dbObjLst) {
				strLst.add(dbMdl.getSource());
			}
		}

		String[] SourceArray = new String[strLst.size() + 1];
		return strLst.toArray(SourceArray);
	}

	/**
	 * Combined lst.
	 * 
	 * @param db1ObjLst
	 *            the db1 obj lst
	 * @param db2ObjLst
	 *            the db2 obj lst
	 * @return the list
	 */
	private List<ObjCodeViewModel> combinedLst(
			List<DBObjectDetailModel> db1ObjLst,
			List<DBObjectDetailModel> db2ObjLst) {
		List<DBObjectDetailModel> db2ObjLstTemp = new ArrayList<DBObjectDetailModel>();
		if (db2ObjLst != null && !db2ObjLst.isEmpty()) {
			db2ObjLstTemp.addAll(db2ObjLst);
		}

		List<ObjCodeViewModel> combinedLst = new ArrayList<ObjCodeViewModel>();
		List<DBObjectDetailModel> db2MatchingLSt = new ArrayList<DBObjectDetailModel>();
		for (DBObjectDetailModel dbMdl1 : db1ObjLst) {
			ObjCodeViewModel objCodeViewModl = new ObjCodeViewModel();
			objCodeViewModl.setDb1Code(dbMdl1.getSource());
			boolean flag = false;
			for (DBObjectDetailModel dbMdl2 : db2ObjLstTemp) {
				if ((dbMdl1.getLine()).equals(dbMdl2.getLine())) {
					objCodeViewModl.setDb2Code(dbMdl2.getSource());
					if (((dbMdl1.getSource() != null)
							&& (dbMdl2.getSource() != null) && !(dbMdl1
							.getSource()).equals(dbMdl2.getSource()))) {
						objCodeViewModl.setColorFlag("Green");
					}
					db2MatchingLSt.add(dbMdl2);
					flag = true;
					break;
				}
			}

			if (!flag) {
				objCodeViewModl.setDb2Code("");
			}
			combinedLst.add(objCodeViewModl);
		}

		// add db2 list
		db2ObjLstTemp.removeAll(db2MatchingLSt);
		for (DBObjectDetailModel dbMdl2 : db2ObjLstTemp) {
			ObjCodeViewModel objCodeViewModl = new ObjCodeViewModel();
			objCodeViewModl.setDb1Code("");
			objCodeViewModl.setDb2Code(dbMdl2.getSource());
			objCodeViewModl.setColorFlag("Green");
			combinedLst.add(objCodeViewModl);
		}

		return combinedLst;
	}

	/**
	 * Gets the mail service.
	 * 
	 * @return the mail service
	 */
	public MailService getMailService() {
		@SuppressWarnings("deprecation")
		MailService mailService = (MailService) getWebApplicationContext()
				.getBean("mailService");
		return mailService;
	}
}
