package com.hp.dbpowerpack.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.MappingDispatchActionSupport;


/**
 * The Class DBPPBaseAction.
 */
@SuppressWarnings("deprecation")
public abstract class DBPPBaseAction extends MappingDispatchActionSupport {

	/**
	 * The method overrides the base class method. Process the specified HTTP
	 * request, and create the corresponding HTTP response. Return an
	 * <code>ActionForward</code> instance describing where and how control
	 * should be forwarded.
	 * 
	 * This method dispatches the request to other methods of
	 * <code>PossBaseAction</code> using the 'parameter' attribute of
	 * <code>ActionMapping</code>.
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
	 * @throws Exception
	 *             the exception
	 * @see org.apache.struts.actions.MappingDispatchAction#execute(org.apache.struts
	 *      .action.ActionMapping, org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward executeAction(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		// final HttpSession session = request.getSession(false);

		forward = executeParent(mapping, form, request, response);
		return forward;
	}

	/**
	 * This method will call the <code>super</code> class execute method to
	 * process request.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            ActionForm bean for this request
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are processing
	 * @return Return an <code>ActionForward</code> instance describing where
	 *         and how control should be forwarded.
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward executeParent(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		return super.execute(mapping, form, request, response);
	}

}
