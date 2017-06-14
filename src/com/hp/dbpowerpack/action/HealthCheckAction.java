package com.hp.dbpowerpack.action;

import java.io.File;
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

import com.hp.dbpowerpack.Model.BlockContentionModel;
import com.hp.dbpowerpack.Model.DBConfigDetailsModel;
import com.hp.dbpowerpack.Model.DbJobsModel;
import com.hp.dbpowerpack.Model.TableSpaceModel;
import com.hp.dbpowerpack.common.action.DBPPBaseAction;
import com.hp.dbpowerpack.common.exception.DBPPBusinessException;
import com.hp.dbpowerpack.common.model.ExcelModel;
import com.hp.dbpowerpack.common.model.MailModel;
import com.hp.dbpowerpack.common.util.ExcelUtil;
import com.hp.dbpowerpack.service.DBConfigDetailsService;
import com.hp.dbpowerpack.service.HealthCheckService;
import com.hp.dbpowerpack.service.MailService;


/**
 * The Class HealthCheckAction.
 */
public class HealthCheckAction extends DBPPBaseAction {

	/** The Constant logger. */
	private static final Logger LOGGER = Logger
			.getLogger(HealthCheckAction.class);

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
	 * Check table space.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward checkTableSpace(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");
			DBConfigDetailsService dbConfigDetailsService = getService();
			HealthCheckService healthCheckService = getHealthChkService();

			String dbName = (String) dynaForm.get("dbName");
			DBConfigDetailsModel dbModel = dbConfigDetailsService.getdbModel(
					userId, dbName);
			List<TableSpaceModel> tableSpaceDtlList = (List) healthCheckService
					.getTableSpaceDetails(dbModel);

			String fileName = "TableSpace_" + dbName + "_" + userId + ".xls";

			ExcelModel model = new ExcelModel();
			model.setFileName(fileName);
			model.setSheetName("Invalid Objects " + dbName);
			model.setObjLst((List) tableSpaceDtlList);
			model.setObjectClass("com.hp.dbpowerpack.Model.TableSpaceModel");

			List<String> headerList = new ArrayList<String>();
			headerList.add("tableSpaceName");
			headerList.add("sizeMb");
			headerList.add("freeMb");
			headerList.add("freePerc");
			headerList.add("usedPerc");

			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("tableSpaceName");
			fieldNameList.add("sizeMb");
			fieldNameList.add("freeMb");
			fieldNameList.add("freePerc");
			fieldNameList.add("usedPerc");

			model.setFieldNameList(fieldNameList);
			model.setDbNameHeader(false);
			model.setColumnCount(0);
			model.setHeaderList(headerList);
			model.setDbNameHeader(false);
			model.setDBHeader(true);

			model.setColorFlag(false);

			dynaForm.set("excelModel", model);
			dynaForm.set("tableSpaceDtlList", tableSpaceDtlList);
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
	}

	/**
	 * Block contention.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward blockContention(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");
			DBConfigDetailsService dbConfigDetailsService = getService();
			HealthCheckService healthCheckService = getHealthChkService();

			String dbName = (String) dynaForm.get("dbName");
			DBConfigDetailsModel dbModel = dbConfigDetailsService.getdbModel(
					userId, dbName);
			List<BlockContentionModel> blockContentionMdlLst = (List<BlockContentionModel>) healthCheckService
					.blockContention(dbModel);

			String fileName = "BlockContention_" + dbName + "_" + userId
					+ ".xls";

			ExcelModel model = new ExcelModel();
			model.setFileName(fileName);
			model.setSheetName("BlockContention_" + dbName);
			model.setObjLst((List) blockContentionMdlLst);
			model.setObjectClass("com.hp.dbpowerpack.Model.BlockContentionModel");

			List<String> headerList = new ArrayList<String>();
			headerList.add("waitClass");
			headerList.add("totalWaits");
			headerList.add("totalTime");

			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("waitClass");
			fieldNameList.add("totalWaits");
			fieldNameList.add("totalTime");

			model.setFieldNameList(fieldNameList);
			model.setDbNameHeader(false);
			model.setColumnCount(0);
			model.setHeaderList(headerList);
			model.setDbNameHeader(false);
			model.setDBHeader(true);

			model.setColorFlag(false);

			dynaForm.set("excelModel", model);

			dynaForm.set("blockContentionList", blockContentionMdlLst);
			dynaForm.set("displayFlag", "BlockContention");
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
	}

	/**
	 * Partitioned table.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward partitionedTable(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");

			DBConfigDetailsService dbConfigDetailsService = getService();
			HealthCheckService healthCheckService = getHealthChkService();

			String dbName = (String) dynaForm.get("dbName");
			DBConfigDetailsModel dbModel = dbConfigDetailsService.getdbModel(
					userId, dbName);
			List<String> partitionedTableList = (List<String>) healthCheckService
					.partitionedTable(dbModel);

			String fileName = "PartitionedTable_" + dbName + "_" + userId
					+ ".xls";

			ExcelModel model = new ExcelModel();
			model.setFileName(fileName);
			model.setSheetName("PartitionedTable_" + dbName);
			model.setObjLst((List) partitionedTableList);
			model.setObjectClass("java.lang.String");

			List<String> headerList = new ArrayList<String>();
			headerList.add("Partitioned Table");

			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("java.lang.String");

			model.setFieldNameList(fieldNameList);
			model.setDbNameHeader(false);
			model.setColumnCount(0);
			model.setHeaderList(headerList);
			model.setDbNameHeader(false);
			model.setDBHeader(true);

			model.setColorFlag(false);

			dynaForm.set("excelModel", model);

			dynaForm.set("partitionedTableList", partitionedTableList);
			dynaForm.set("displayFlag", "PartitionedTable");
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
	}

	/**
	 * Available jobs.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward availableJobs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");

			DBConfigDetailsService dbConfigDetailsService = getService();
			HealthCheckService healthCheckService = getHealthChkService();

			String dbName = (String) dynaForm.get("dbName");
			DBConfigDetailsModel dbModel = dbConfigDetailsService.getdbModel(
					userId, dbName);
			List<DbJobsModel> availableJobsList = (List<DbJobsModel>) healthCheckService
					.availableJobs(dbModel);

			String fileName = "AvailableJobs_" + dbName + "_" + userId + ".xls";

			ExcelModel model = new ExcelModel();
			model.setFileName(fileName);
			model.setSheetName("AvailableJobs_" + dbName);
			model.setObjLst((List) availableJobsList);
			model.setObjectClass("com.hp.dbpowerpack.Model.DbJobsModel");

			List<String> headerList = new ArrayList<String>();
			headerList.add("logUser");
			headerList.add("privUser");
			headerList.add("lastDate");
			headerList.add("lastSec");
			headerList.add("thisDate");
			headerList.add("thisSec");
			headerList.add("broken");
			headerList.add("what");

			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("logUser");
			fieldNameList.add("privUser");
			fieldNameList.add("lastDate");
			fieldNameList.add("lastSec");
			fieldNameList.add("thisDate");
			fieldNameList.add("thisSec");
			fieldNameList.add("broken");
			fieldNameList.add("what");

			model.setFieldNameList(fieldNameList);
			model.setDbNameHeader(false);
			model.setColumnCount(0);
			model.setHeaderList(headerList);
			model.setDbNameHeader(false);
			model.setDBHeader(true);

			model.setColorFlag(false);

			dynaForm.set("excelModel", model);

			dynaForm.set("availableJobsList", availableJobsList);
			dynaForm.set("displayFlag", "AvailableJobs");
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
	}

	/**
	 * Jobs broken.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward jobsBroken(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");

			DBConfigDetailsService dbConfigDetailsService = getService();
			HealthCheckService healthCheckService = getHealthChkService();

			String dbName = (String) dynaForm.get("dbName");
			DBConfigDetailsModel dbModel = dbConfigDetailsService.getdbModel(
					userId, dbName);
			List<DbJobsModel> jobsBrokenList = (List<DbJobsModel>) healthCheckService
					.jobsBroken(dbModel);

			String fileName = "JobsBroken_" + dbName + "_" + userId + ".xls";

			ExcelModel model = new ExcelModel();
			model.setFileName(fileName);
			model.setSheetName("JobsBroken_" + dbName);
			model.setObjLst((List) jobsBrokenList);
			model.setObjectClass("com.hp.dbpowerpack.Model.DbJobsModel");

			List<String> headerList = new ArrayList<String>();
			headerList.add("logUser");
			headerList.add("privUser");
			headerList.add("lastDate");
			headerList.add("lastSec");
			headerList.add("thisDate");
			headerList.add("thisSec");
			headerList.add("what");

			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("logUser");
			fieldNameList.add("privUser");
			fieldNameList.add("lastDate");
			fieldNameList.add("lastSec");
			fieldNameList.add("thisDate");
			fieldNameList.add("thisSec");
			fieldNameList.add("what");

			model.setFieldNameList(fieldNameList);
			model.setDbNameHeader(false);
			model.setColumnCount(0);
			model.setHeaderList(headerList);
			model.setDbNameHeader(false);
			model.setDBHeader(true);

			model.setColorFlag(false);

			dynaForm.set("excelModel", model);

			dynaForm.set("jobsBrokenList", jobsBrokenList);
			dynaForm.set("displayFlag", "JobsBroken");
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
	}

	/**
	 * Jobs running.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward jobsRunning(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");

			DBConfigDetailsService dbConfigDetailsService = getService();
			HealthCheckService healthCheckService = getHealthChkService();

			String dbName = (String) dynaForm.get("dbName");
			DBConfigDetailsModel dbModel = dbConfigDetailsService.getdbModel(
					userId, dbName);
			List<DbJobsModel> jobsRunningList = (List<DbJobsModel>) healthCheckService
					.jobsRunning(dbModel);

			String fileName = "JobsRunning_" + dbName + "_" + userId + ".xls";

			ExcelModel model = new ExcelModel();
			model.setFileName(fileName);
			model.setSheetName("JobsRunning_" + dbName);
			model.setObjLst((List) jobsRunningList);
			model.setObjectClass("com.hp.dbpowerpack.Model.DbJobsModel");

			List<String> headerList = new ArrayList<String>();
			headerList.add("job");
			headerList.add("failures");
			headerList.add("lastDate");
			headerList.add("lastSec");
			headerList.add("thisDate");
			headerList.add("thisSec");

			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("job");
			fieldNameList.add("failures");
			fieldNameList.add("lastDate");
			fieldNameList.add("lastSec");
			fieldNameList.add("thisDate");
			fieldNameList.add("thisSec");

			model.setFieldNameList(fieldNameList);
			model.setDbNameHeader(false);
			model.setColumnCount(0);
			model.setHeaderList(headerList);
			model.setDbNameHeader(false);
			model.setDBHeader(true);

			model.setColorFlag(false);

			dynaForm.set("excelModel", model);

			dynaForm.set("jobsRunningList", jobsRunningList);
			dynaForm.set("displayFlag", "JobsRunning");
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
	public HealthCheckService getHealthChkService() {
		@SuppressWarnings("deprecation")
		HealthCheckService healthCheckService = (HealthCheckService) getWebApplicationContext()
				.getBean("healthCheckService");

		return healthCheckService;
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
