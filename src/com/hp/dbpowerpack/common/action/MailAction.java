package com.hp.dbpowerpack.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.hp.dbpowerpack.common.model.ExcelModel;


/**
 * The Class MailAction.
 */
public class MailAction extends DBPPBaseAction {

	/** The Constant logger. */
	private static final Logger LOGGER = Logger.getLogger(MailAction.class);

	/**
	 * Load mail.
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
	 */
	public ActionForward loadMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOGGER.info("Inside MailAction init method");
		final DynaActionForm dynaForm = (DynaActionForm) form;
		ExcelModel excelModel = (ExcelModel) dynaForm.get("excelModel");
		LOGGER.info("excel Model" + excelModel);
		LOGGER.info("excel Model file name" + excelModel.getFileName());
		dynaForm.set("excelModel", excelModel);
		return mapping.findForward("success");
	}

}
