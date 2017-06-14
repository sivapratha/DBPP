package com.hp.dbpowerpack.common.util;

import java.util.TimerTask;

import javax.mail.Transport;

import org.apache.log4j.Logger;

import com.hp.dbpowerpack.common.model.MailTaskModel;



/**
 * The Class PossMailTimerTask.
 */
public class DBPPMailTimerTask extends TimerTask {
	
	/** The Constant logger. */
	private static final Logger LOGGER = Logger.getLogger(DBPPMailTimerTask.class);


	/** The mail modelobj. */
	MailTaskModel mailModelobj;

	/**
	 * Gets the mail modelobj.
	 *
	 * @return the mail modelobj
	 */
	public MailTaskModel getMailModelobj() {
		return mailModelobj;
	}

	/**
	 * Sets the mail modelobj.
	 *
	 * @param mailModelobj the new mail modelobj
	 */
	public void setMailModelobj(MailTaskModel mailModelobj) {
		this.mailModelobj = mailModelobj;
	}

	/**
	 * Instantiates a new poss mail timer task.
	 *
	 * @param mailModel the mail model
	 */
	public DBPPMailTimerTask(MailTaskModel mailModel) {
		mailModelobj = mailModel;
	
	}

	/** The num warning beeps. */
	int numWarningBeeps = 3;

	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	public void run() {
		LOGGER.info("New Thread");
		if (mailModelobj.getRetryCount() < 3 && mailModelobj.isRetryFlag()) {
			try {
				Transport.send(mailModelobj.getMsg());
				LOGGER.info("Number of Retry"
						+ mailModelobj.getRetryCount());

				Transport.send(mailModelobj.getMsg());
				LOGGER.info("This line is retry flag"
						+ mailModelobj.isRetryFlag());
				mailModelobj.setRetryFlag(false);
			} catch (Exception e) {
				LOGGER.info("Exception occured");
				mailModelobj.setRetryFlag(true);
				int count = mailModelobj.getRetryCount();
				mailModelobj.setRetryCount(count + 1);

			} finally {
				if (mailModelobj.getRetryCount() == 3) {
					mailModelobj.getTimer().cancel();// Not necessary because we
														// call System.exit
				}
			}
			LOGGER.info("Beep!%n");
			numWarningBeeps--;
		} else {
			LOGGER.info("Time's up!%n");

			// System.exit(0); //Stops the AWT thread (and everything else)
		}
	}

}
