package com.hp.dbpowerpack.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.hp.dbpowerpack.Model.UserModel;
import com.hp.dbpowerpack.common.action.DBPPBaseAction;
import com.hp.dbpowerpack.common.exception.DBPPBusinessException;
import com.hp.dbpowerpack.entities.User;
import com.hp.dbpowerpack.service.UserDetailsService;


/**
 * The Class UserAction.
 */
public class UserAction extends DBPPBaseAction {

	/** The Constant logger. */
	private static final Logger LOGGER = Logger.getLogger(UserAction.class);
	
	public ActionForward addUserConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("Inside  UserAction addUserConfig Method");
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;

			dynaForm.set("userId", "");
			dynaForm.set("firstName", "");
			dynaForm.set("lastName", "");
			dynaForm.set("passWord", "");
			dynaForm.set("emailID", "");
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
	}
	
	/**
	 * Register user.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward userConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("Inside UserAction registerUser method");
		final DynaActionForm dynaForm = (DynaActionForm) form;
		UserDetailsService userDetailsService = getService();
		List<User> userConfigList = userDetailsService.getUserDetails();
		dynaForm.set("userConfigList", userConfigList);
		
		return mapping.findForward("success");
	}

	/**
	 * Adds the user.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward addUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("Inside UserAction addUser method");
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;
			final String userId = (String) dynaForm.get("userId");
			final String firstName = (String) dynaForm.get("firstName");
			final String lastName = (String) dynaForm.get("lastName");
			final String password = (String) dynaForm.get("passWord");
			final String emailID = (String) dynaForm.get("emailID");

			HttpSession session = request.getSession(false);
			String createdUser = (String) session.getAttribute("logged-in");

			LOGGER.debug("userName : " + userId + ": password : " + password);

			UserModel user = new UserModel();
			user.setUserid(userId);
			user.setPassword(password);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(emailID);

			UserDetailsService userDetailsService = getService();
			boolean userFlag = userDetailsService.addUser(user, createdUser);

			List<User> userConfigList = userDetailsService.getUserDetails();
			dynaForm.set("userConfigList", userConfigList);
			if (userFlag) {
				request.setAttribute("RESULT_MSG", "Success");
			} else {
				request.setAttribute("RESULT_MSG", "Error");
			}
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
	public UserDetailsService getService() {
		@SuppressWarnings("deprecation")
		UserDetailsService userDetailsService = (UserDetailsService) getWebApplicationContext()
				.getBean("userDetailsService");
		return userDetailsService;
	}
}
