package com.hp.dbpowerpack.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


/**
 * The Class SecurityFilter.
 */
public class SecurityFilter implements javax.servlet.Filter {

	/** The Constant logger. */
	private static final Logger LOGGER = Logger.getLogger(SecurityFilter.class);

	/**
	 * This method is kept empty, which overrides the base class method.
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// Dummy implementation of the super class method.

	}

	/**
	 * This method will be invoked in chain process based on the order it is
	 * configured in web.xml First it will check if there is valid session
	 * exists / not , if the session is not exists then it will check whether
	 * the request to home page / some intermediate page . if it is not home
	 * page it will redirect to Session time Out Page. If the request for Home
	 * Page and session is not exists / some other request and if the session is
	 * valid then it will check the property file for bypass headers validation.
	 * if bypass field is false , then it will check required header information
	 * in request headers / else it will check in hard coded property file (
	 * this is valid for development environment where the system doesn't has
	 * GAME environment.
	 * 
	 * If the required information not exists in header / property file then it
	 * will return to Portal Login Page.
	 * 
	 * Also this class is responsible for authorization and load user roles and
	 * menus into HTTP SESSION based on plant. if no menu available for the
	 * particular request then method will return an error page to with waring
	 * for load user role information into database.
	 * 
	 * @param req
	 *            - HttpRequest Object
	 * @param res
	 *            - HttpResponse Object
	 * @param chain
	 *            the chain
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ServletException
	 *             the servlet exception
	 * @chain - FilterChain Instance from Servlet Container
	 */
	public void doFilter(final ServletRequest req, final ServletResponse res,
			final FilterChain chain) throws IOException, ServletException {

		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;

		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
			LOGGER.info("New session created..." + session.getId());
			LOGGER.info("USER..." + session.getAttribute("logged-in"));
		} else {
			LOGGER.info("Existing session..." + session.getId());
			LOGGER.info("USER..." + session.getAttribute("logged-in"));
		}
		String userId = (String) request.getSession().getAttribute("logged-in");

		boolean isLogged = true;

		if (userId == null) {
			isLogged = false;
		}

		if (isLogged) {
			LOGGER.info("user logged in");
		} else if (!((request.getServletPath().equals("/login.do"))
				|| (request.getServletPath().equals("/loginAction.do"))
				|| (request.getServletPath().equals("/loadChangePassword.do")) || (request
				.getServletPath().equals("/changePassword.do")))) {
			response.sendError(406);
		}

		request.getSession().setAttribute("logged-in", userId);
		chain.doFilter(request, res);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// Dummy implementation of the super class version.

	}

}
