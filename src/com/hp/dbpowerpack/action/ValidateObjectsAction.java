package com.hp.dbpowerpack.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

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
import com.hp.dbpowerpack.Model.NamingStandardModel;
import com.hp.dbpowerpack.common.action.DBPPBaseAction;
import com.hp.dbpowerpack.common.exception.DBPPBusinessException;
import com.hp.dbpowerpack.common.model.ExcelModel;
import com.hp.dbpowerpack.common.model.MailModel;
import com.hp.dbpowerpack.common.util.ExcelUtil;
import com.hp.dbpowerpack.service.DBConfigDetailsService;
import com.hp.dbpowerpack.service.MailService;
import com.hp.dbpowerpack.service.ValidateObjectService;


/**
 * The Class ValidateObjectsAction.
 */
public class ValidateObjectsAction extends DBPPBaseAction {

	/** The Constant logger. */
	private static final Logger LOGGER = Logger
			.getLogger(ValidateObjectsAction.class);

	/**
	 * Inits the.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
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
			dynaForm.set("dbNameList", dbNameList);
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
	}

	/**
	 * Load validate naming standard.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward loadValidateNamingStandard(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("success");
	}

	/**
	 * Load validate obj config.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward loadValidateObjConfig(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");
			ValidateObjectService validateObjectService = getValidateObjService();
			NamingStandardModel model = validateObjectService
					.getNamingStandard(userId);

			if (model != null) {
				dynaForm.set("procedureStartsWith",
						model.getProcedureStartsWith());
				dynaForm.set("functionStartsWith",
						model.getFunctionStartsWith());
				dynaForm.set("packageStartsWith", model.getPackageStartsWith());
				dynaForm.set("tableStartsWith", model.getTableStartsWith());
				dynaForm.set("viewStartsWith", model.getViewStartsWith());
				dynaForm.set("sequenceStartsWith",
						model.getSequenceStartsWith());
				dynaForm.set("triggerStartsWith", model.getTriggerStartsWith());
				dynaForm.set("typeStartsWith", model.getTypeStartsWith());
				dynaForm.set("mviewStartsWith", model.getMviewStartsWith());
				dynaForm.set("dbLinkStartsWith", model.getDbLinkStartsWith());
				dynaForm.set("isExist", true);
			} else {
				dynaForm.set("procedureStartsWith", "");
				dynaForm.set("functionStartsWith", "");
				dynaForm.set("packageStartsWith", "");
				dynaForm.set("tableStartsWith", "");
				dynaForm.set("viewStartsWith", "");
				dynaForm.set("sequenceStartsWith", "");
				dynaForm.set("triggerStartsWith", "");
				dynaForm.set("typeStartsWith", "");
				dynaForm.set("mviewStartsWith", "");
				dynaForm.set("dbLinkStartsWith", "");
				dynaForm.set("isExist", false);
			}

		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
	}

	/**
	 * Save validate obj config.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward saveValidateObjConfig(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");

			NamingStandardModel model = new NamingStandardModel();

			String procedureStartsWith = (String) dynaForm
					.get("procedureStartsWith");
			String functionStartsWith = (String) dynaForm
					.get("functionStartsWith");
			String packageStartsWith = (String) dynaForm
					.get("packageStartsWith");
			String tableStartsWith = (String) dynaForm.get("tableStartsWith");
			String viewStartsWith = (String) dynaForm.get("viewStartsWith");
			String sequenceStartsWith = (String) dynaForm
					.get("sequenceStartsWith");
			String triggerStartsWith = (String) dynaForm
					.get("triggerStartsWith");
			String typeStartsWith = (String) dynaForm.get("typeStartsWith");
			String mviewStartsWith = (String) dynaForm.get("mviewStartsWith");
			String dbLinkStartsWith = (String) dynaForm.get("dbLinkStartsWith");
			Boolean isExist = (Boolean) dynaForm.get("isExist");

			model.setDbLinkStartsWith(dbLinkStartsWith);
			model.setFunctionStartsWith(functionStartsWith);
			model.setMviewStartsWith(mviewStartsWith);
			model.setPackageStartsWith(packageStartsWith);
			model.setProcedureStartsWith(procedureStartsWith);
			model.setSequenceStartsWith(sequenceStartsWith);
			model.setTableStartsWith(tableStartsWith);
			model.setTriggerStartsWith(triggerStartsWith);
			model.setTypeStartsWith(typeStartsWith);
			model.setViewStartsWith(viewStartsWith);
			model.setExist(isExist);

			ValidateObjectService validateObjectService = getValidateObjService();
			validateObjectService.saveNamingStandard(userId, model);
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return null;
	}

	/**
	 * Invalid obj action.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward invalidObjAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");
			DBConfigDetailsService dbConfigDetailsService = getService();
			ValidateObjectService validateObjectService = getValidateObjService();

			String dbName = (String) dynaForm.get("dbName");
			DBConfigDetailsModel dbModel = dbConfigDetailsService.getdbModel(
					userId, dbName);
			List<DBObjViewModel> invalidObjList = validateObjectService
					.getInvalidObjects(dbModel);

			String fileName = "InvalidObject_" + dbName + "_" + userId + ".xls";

			ExcelModel model = new ExcelModel();
			model.setFileName(fileName);
			model.setSheetName("Invalid Objects " + dbName);
			model.setObjLst((List) invalidObjList);
			model.setObjectClass("com.hp.dbpowerpack.Model.DBObjViewModel");

			List<String> headerList = new ArrayList<String>();
			headerList.add("objType");
			headerList.add("objName");

			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("objType");
			fieldNameList.add("objName");
			model.setFieldNameList(fieldNameList);
			model.setDbNameHeader(false);
			model.setColumnCount(0);
			model.setHeaderList(headerList);
			model.setDbNameHeader(false);
			model.setDBHeader(true);

			model.setColorFlag(true);

			dynaForm.set("excelModel", model);

			dynaForm.set("invalidObjList", invalidObjList);
			dynaForm.set("requestType", "InvalidObject");
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");

	}

	/**
	 * Validate naming standard.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward validateNamingStandard(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		response.setContentType("text/html");
		response.setHeader("Content-disposition", null);
		OutputStream fileOut = null;
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");
			DBConfigDetailsService dbConfigDetailsService = getService();
			ValidateObjectService validateObjectService = getValidateObjService();

			String dbName = (String) dynaForm.get("dbName");
			String slctdDbObj = (String) dynaForm.get("slctdDbObj");
			DBConfigDetailsModel dbModel = dbConfigDetailsService.getdbModel(
					userId, dbName);
			List<DBObjViewModel> namingStandardChkList = validateObjectService
					.validateNamingStandard(dbModel, userId, slctdDbObj);
			dynaForm.set("namingStandardChkList", namingStandardChkList);
			dynaForm.set("requestType", "NamingStandard");

			String fileName = "validateNamingStandard_" + dbName + "_" + userId
					+ ".xls";

			ExcelModel model = new ExcelModel();
			model.setFileName(fileName);
			model.setSheetName("validateNamingStandard_" + dbName);
			model.setObjLst((List) namingStandardChkList);
			model.setObjectClass("com.hp.dbpowerpack.Model.DBObjViewModel");

			List<String> headerList = new ArrayList<String>();
			headerList.add("objType");
			headerList.add("objName");
			headerList.add("status");

			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("objType");
			fieldNameList.add("objName");
			fieldNameList.add("status");
			model.setFieldNameList(fieldNameList);
			model.setDbNameHeader(false);
			model.setColumnCount(0);
			model.setHeaderList(headerList);
			model.setDbNameHeader(false);
			model.setDBHeader(true);

			model.setColorFlag(true);

			dynaForm.set("excelModel", model);
			final String htmlContent = generateHtmlContent(namingStandardChkList);
			fileOut = response.getOutputStream();
			final PrintStream printStream = new PrintStream(fileOut);
			printStream.print(htmlContent);
			printStream.close();

		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		} catch (IOException e) {
			request.setAttribute("RESULT_MSG", "Error");
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					request.setAttribute("RESULT_MSG", "Error");
				}
			}
		}
		return null;
	}

	/**
	 * Generate html content.
	 *
	 * @param namingStandardChkList the naming standard chk list
	 * @return the string
	 */
	private String generateHtmlContent(
			List<DBObjViewModel> namingStandardChkList) {
		final StringBuffer htmlContentBuffer = new StringBuffer();
		htmlContentBuffer
				.append("<table  class=\"gridtable\"><tr><th>Object Type</th><th>Object Name</th><th>Object Status</th></tr>");

		for (DBObjViewModel dbObjMdl : namingStandardChkList) {
			htmlContentBuffer.append("<tr><td>");
			htmlContentBuffer.append(dbObjMdl.getObjType());
			htmlContentBuffer.append("</td><td>");
			htmlContentBuffer.append(dbObjMdl.getObjName());
			htmlContentBuffer.append("</td><td>");
			htmlContentBuffer.append(dbObjMdl.getStatus());
			htmlContentBuffer.append("</td></tr>");

		}
		htmlContentBuffer.append("</table>");
		final String htmlContent = htmlContentBuffer.toString();
		return htmlContent;
	}

	/**
	 * Poor readability results.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward poorReadabilityResults(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		response.setContentType("text/html");
		response.setHeader("Content-disposition", null);
		OutputStream fileOut = null;
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");
			DBConfigDetailsService dbConfigDetailsService = getService();
			ValidateObjectService validateObjectService = getValidateObjService();

			String dbName = (String) dynaForm.get("dbName");
			String length = (String) dynaForm.get("length");
			String slctdDbObj = (String) dynaForm.get("slctdDbObj");
			DBConfigDetailsModel dbModel = dbConfigDetailsService.getdbModel(
					userId, dbName);
			List<DBObjViewModel> poorReadabilityList = validateObjectService
					.getPoorReadabilityObjects(dbModel, slctdDbObj, length);

			String fileName = "PoorReadability_" + dbName + "_" + userId
					+ ".xls";

			ExcelModel model = new ExcelModel();
			model.setFileName(fileName);
			model.setSheetName("PoorReadability_" + dbName);
			model.setObjLst((List) poorReadabilityList);
			model.setObjectClass("com.hp.dbpowerpack.Model.DBObjViewModel");

			List<String> headerList = new ArrayList<String>();
			headerList.add("objType");
			headerList.add("objName");

			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("objType");
			fieldNameList.add("objName");
			model.setFieldNameList(fieldNameList);
			model.setDbNameHeader(false);
			model.setColumnCount(0);
			model.setHeaderList(headerList);
			model.setDbNameHeader(false);
			model.setDBHeader(true);

			model.setColorFlag(true);

			dynaForm.set("excelModel", model);

			dynaForm.set("poorReadabilityList", poorReadabilityList);
			dynaForm.set("requestType", "PoorReadability");
			final String htmlContent = generateHtmlContentForPoorReadability(poorReadabilityList);
			fileOut = response.getOutputStream();
			final PrintStream printStream = new PrintStream(fileOut);
			printStream.print(htmlContent);
			printStream.close();

		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		} catch (IOException e) {
			request.setAttribute("RESULT_MSG", "Error");
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					request.setAttribute("RESULT_MSG", "Error");
				}
			}
		}
		return null;
	}

	/**
	 * Generate html content for poor readability.
	 *
	 * @param namingStandardChkList the naming standard chk list
	 * @return the string
	 */
	private String generateHtmlContentForPoorReadability(
			List<DBObjViewModel> namingStandardChkList) {
		final StringBuffer htmlContentBuffer = new StringBuffer();
		htmlContentBuffer
				.append("<table  class=\"gridtable\"><tr><th>Object Type</th><th>Object Name</th></tr>");

		for (DBObjViewModel dbObjMdl : namingStandardChkList) {
			htmlContentBuffer.append("<tr><td>");
			htmlContentBuffer.append(dbObjMdl.getObjType());
			htmlContentBuffer.append("</td><td>");
			htmlContentBuffer.append(dbObjMdl.getObjName());
			htmlContentBuffer.append("</td></tr>");

		}
		htmlContentBuffer.append("</table>");
		final String htmlContent = htmlContentBuffer.toString();
		return htmlContent;
	}

	/**
	 * Poor readability.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward poorReadability(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;
			dynaForm.set("requestType", "PoorReadability");
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
	}

	/**
	 * Rollback check.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward rollbackCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");
			DBConfigDetailsService dbConfigDetailsService = getService();
			ValidateObjectService validateObjectService = getValidateObjService();

			String dbName = (String) dynaForm.get("dbName");
			DBConfigDetailsModel dbModel = dbConfigDetailsService.getdbModel(
					userId, dbName);
			List<DBObjViewModel> rollBackCheckList = validateObjectService
					.getRollbackCheck(dbModel);

			String fileName = "RollbackCheck_" + dbName + "_" + userId + ".xls";

			ExcelModel model = new ExcelModel();
			model.setFileName(fileName);
			model.setSheetName("RollbackCheck_" + dbName);
			model.setObjLst((List) rollBackCheckList);
			model.setObjectClass("com.hp.dbpowerpack.Model.DBObjViewModel");

			List<String> headerList = new ArrayList<String>();
			headerList.add("objType");
			headerList.add("objName");

			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("objType");
			fieldNameList.add("objName");
			model.setFieldNameList(fieldNameList);
			model.setDbNameHeader(false);
			model.setColumnCount(0);
			model.setHeaderList(headerList);
			model.setDbNameHeader(false);
			model.setDBHeader(true);

			model.setColorFlag(true);

			dynaForm.set("excelModel", model);

			dynaForm.set("rollBackCheckList", rollBackCheckList);
			dynaForm.set("requestType", "RollbackCheck");
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
	}

	/**
	 * Poor version management.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward poorVersionManagement(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");
			DBConfigDetailsService dbConfigDetailsService = getService();
			ValidateObjectService validateObjectService = getValidateObjService();

			String dbName = (String) dynaForm.get("dbName");
			DBConfigDetailsModel dbModel = dbConfigDetailsService.getdbModel(
					userId, dbName);
			List<DBObjViewModel> poorVersionMngmntLst = validateObjectService
					.getPoorVersionManagement(dbModel);

			String fileName = "PoorVersionMngmnt_" + dbName + "_" + userId
					+ ".xls";

			ExcelModel model = new ExcelModel();
			model.setFileName(fileName);
			model.setSheetName("PoorVersionMngmnt_" + dbName);
			model.setObjLst((List) poorVersionMngmntLst);
			model.setObjectClass("com.hp.dbpowerpack.Model.DBObjViewModel");

			List<String> headerList = new ArrayList<String>();
			headerList.add("objType");
			headerList.add("objName");
			headerList.add("status");

			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("objType");
			fieldNameList.add("objName");
			fieldNameList.add("status");
			model.setFieldNameList(fieldNameList);
			model.setDbNameHeader(false);
			model.setColumnCount(0);
			model.setHeaderList(headerList);
			model.setDbNameHeader(false);
			model.setDBHeader(true);

			model.setColorFlag(true);

			dynaForm.set("excelModel", model);

			dynaForm.set("poorVersionMngmntLst", poorVersionMngmntLst);
			dynaForm.set("requestType", "PoorVersionMngmnt");
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
	public DBConfigDetailsService getService() {
		@SuppressWarnings("deprecation")
		DBConfigDetailsService dbConfigDetailsService = (DBConfigDetailsService) getWebApplicationContext()
				.getBean("dbConfigDetailsService");

		return dbConfigDetailsService;
	}

	/**
	 * Gets the health chk service.
	 * 
	 * @return the health chk service
	 */
	public ValidateObjectService getValidateObjService() {
		@SuppressWarnings("deprecation")
		ValidateObjectService validateObjectService = (ValidateObjectService) getWebApplicationContext()
				.getBean("validateObjectService");

		return validateObjectService;
	}

	/**
	 * Compare table excel.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward excelReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
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
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward sendMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("Inside MailActionMailAction init method");
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
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
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
