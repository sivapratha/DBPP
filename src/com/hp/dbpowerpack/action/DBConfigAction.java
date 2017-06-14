package com.hp.dbpowerpack.action;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.hp.dbpowerpack.common.action.DBPPBaseAction;
import com.hp.dbpowerpack.common.exception.DBPPBusinessException;
import com.hp.dbpowerpack.common.util.DBPPEncryptDecryptUtil;
import com.hp.dbpowerpack.entities.DBConfigDetails;
import com.hp.dbpowerpack.service.DBConfigDetailsService;


/**
 * The Class DBConfigAction.
 */
public class DBConfigAction extends DBPPBaseAction {

	/** The Constant logger. */
	private static final Logger LOGGER = Logger.getLogger(DBConfigAction.class);

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
		LOGGER.info("Inside  DBConfigAction init Method");
		return mapping.findForward("success");
	}

	/**
	 * Adds the config.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward addConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("Inside  DBConfigAction addConfig Method");
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			dynaForm.set("dbName", "");
			dynaForm.set("serverName", "");
			dynaForm.set("portNumber", "");
			dynaForm.set("sid", "");
			dynaForm.set("userName", "");
			dynaForm.set("passWord", "");
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
	}

	/**
	 * Removes the db config.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward removeDBConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("Inside  DBConfigAction removeDBConfig Method");
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;
			final String removeConfig = (String) dynaForm.get("removeConfig");

			final String[] removeConfigArr = removeConfig.split(",");
			LOGGER.info("removeConfig -->" + removeConfig);

			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");

			DBConfigDetailsService dbConfigDetailsService = getService();
			dbConfigDetailsService.removeConfig(removeConfigArr, userId);
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
	 * Adds the db config.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward addDBConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("Inside LoginAction addDBConfig method");
		String forward = "success";
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;
			final String dbName = (String) dynaForm.get("dbName");
			final String serverName = (String) dynaForm.get("serverName");
			final String portNumber = (String) dynaForm.get("portNumber");
			final String sid = (String) dynaForm.get("sid");
			final String userName = (String) dynaForm.get("userName");
			final String password = (String) dynaForm.get("passWord");
			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");
			LOGGER.info("dbName : " + dbName + ": serverName : " + serverName
					+ "portNumber : " + portNumber + ": sid : " + sid
					+ "userName : " + userName + ": password : " + password);

			DBConfigDetails dbConfigDetails = new DBConfigDetails();
			dbConfigDetails.setDbName(dbName);
			dbConfigDetails.setUserid(userId);
			dbConfigDetails.setServerName(serverName);
			dbConfigDetails.setPortNumber(portNumber);
			dbConfigDetails.setSid(sid);
			dbConfigDetails.setUserName(userName);
			dbConfigDetails.setCreatedBy(userId);
			dbConfigDetails.setCreateddt(new Date(System.currentTimeMillis()));

			String encryptPassword = DBPPEncryptDecryptUtil.encrypt(password);
			dbConfigDetails.setPassWord(encryptPassword);
			DBConfigDetailsService dbConfigDetailsService = getService();

			dbConfigDetailsService.addDBConfigDetails(dbConfigDetails);

		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
			forward = "error";
		}
		return mapping.findForward(forward);
	}

	/**
	 * View db config.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward viewDBConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("Inside LoginAction login method");
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;
			DBConfigDetailsService dbConfigDetailsService = getService();
			HttpSession session = request.getSession(false);
			String userId = (String) session.getAttribute("logged-in");

			List<DBConfigDetails> dbConfigDetailsList = dbConfigDetailsService
					.getDBConfigDetailsList(userId);
			LOGGER.info("dbConfigDetailsList.size" + dbConfigDetailsList.size());
			dynaForm.set("dbConfigList", dbConfigDetailsList);
			dynaForm.set("dbName", "");
			dynaForm.set("serverName", "");
			dynaForm.set("portNumber", "");
			dynaForm.set("sid", "");
			dynaForm.set("userName", "");
			dynaForm.set("passWord", "");
			dynaForm.set("removeConfig", "");
			dynaForm.set("isSelected", false);
			dynaForm.set("selectAll", false);
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");

	}
}
