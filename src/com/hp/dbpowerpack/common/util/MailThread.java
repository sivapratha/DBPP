package com.hp.dbpowerpack.common.util;

import com.hp.dbpowerpack.common.model.MailModel;


/**
 * The Class MailThread.
 */
public class MailThread extends Thread {

	/** The mail model. */
	private MailModel mailModel;

	/**
	 * Instantiates a new mail thread.
	 * 
	 * @param mailModel
	 *            the mail model
	 */
	public MailThread(MailModel mailModel) {
		super();
		this.mailModel = mailModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		MailUtil.sendEmailWithAttachment(mailModel);
	}
}
