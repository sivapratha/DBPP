package com.hp.dbpowerpack.service;

import com.hp.dbpowerpack.common.model.MailModel;
import com.hp.dbpowerpack.common.util.MailThread;


/**
 * The Class MailService.
 */
public class MailService {

	/**
	 * Send email.
	 * 
	 * @param model
	 *            the model
	 */
	public void sendEmail(MailModel model) {
		Thread thread = new MailThread(model);
		thread.start();
	}

}
