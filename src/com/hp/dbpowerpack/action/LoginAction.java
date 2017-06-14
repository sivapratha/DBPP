package com.hp.dbpowerpack.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

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
import com.hp.dbpowerpack.entities.User;
import com.hp.dbpowerpack.service.UserDetailsService;


/**
 * The Class LoginAction.
 */
public class LoginAction extends DBPPBaseAction {

	/** The Constant logger. */
	private static final Logger LOGGER = Logger.getLogger(LoginAction.class);

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
		LOGGER.info("Inside LoginAction init method");
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;
			dynaForm.set("userName", null);
			dynaForm.set("passWord", null);
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("login");
	}

	/**
	 * Log out.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward logOut(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(false);
			session.removeAttribute("logged-in");
			session.removeAttribute("logged-user");
			session.invalidate();
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward("success");
	}

	/**
	 * Load home.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward loadHome(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("Inside LoginAction loadHome method");
		return mapping.findForward("success");
	}

	/**
	 * Load change password.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward loadChangePassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.info("Inside LoginAction loadHome method");
		return mapping.findForward("success");
	}

	/**
	 * Login.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("Inside LoginAction login method");
		String forward = "success";
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;
			final String userId = (String) dynaForm.get("userName");
			final String password = (String) dynaForm.get("passWord");
			LOGGER.debug("userName : " + userId + ": password : " + password);

			HttpSession session = request.getSession(false);
			session.setAttribute("logged-in", userId);
			UserDetailsService userDetailsService = getService();
			User user = userDetailsService.authenticateUser(userId, password);
			if (user == null) {
				forward = "fail";
				session.setAttribute("logged-user",null);
				request.setAttribute("RESULT_MSG", "Error");
			}else{
				String firstName = "";
				String lastName = "";
				if(user.getFirstName()!=null){
					firstName = user.getFirstName();
				}
				
				if(user.getLastName()!=null){
					lastName = user.getLastName();
				}
				session.setAttribute("logged-user", firstName + " " + lastName);				
			}
			
			

		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return mapping.findForward(forward);
	}

	/**
	 * Login.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward changePassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("Inside LoginAction login method");
		response.setContentType("text/html");
		response.setHeader("Content-disposition", null);
		OutputStream fileOut = null;
		try {
			final DynaActionForm dynaForm = (DynaActionForm) form;
			final String userId = (String) dynaForm.get("userId");
			final String oldPassWord = (String) dynaForm.get("oldPassWord");
			final String newPassWord = (String) dynaForm.get("newPassWord");
			String responseMsg = "";
			HttpSession session = request.getSession(false);
			session.setAttribute("logged-in", userId);
			UserDetailsService userDetailsService = getService();
			User user = userDetailsService.changePassword(userId, oldPassWord,
					newPassWord);

			if (user != null) {
				request.setAttribute("RESULT_MSG", "Success");
				responseMsg = "Success";
				session.setAttribute("logged-user", user.getFirstName() + " " + user.getLastName());
			} else {
				request.setAttribute("RESULT_MSG", "Error");
				responseMsg = "Error";
				session.setAttribute("logged-user",null);

			}
			fileOut = response.getOutputStream();
			final PrintStream printStream = new PrintStream(fileOut);
			printStream.print(responseMsg);
			printStream.close();
		} catch (DBPPBusinessException e) {
			request.setAttribute("RESULT_MSG", "Error");
		} catch (IOException e) {
			request.setAttribute("RESULT_MSG", "Error");
		}
		return null;
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
