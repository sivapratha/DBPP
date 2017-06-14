package com.hp.dbpowerpack.common.model;

import java.util.Timer;

import javax.mail.Message;


/**
 * The Class MailTaskModel.
 */
public class MailTaskModel {

	/** The msg. */
	Message msg;

	/** The retry flag. */
	boolean retryFlag;

	/** The retry count. */
	int retryCount;

	/** The timer. */
	Timer timer;

	/**
	 * Gets the timer.
	 * 
	 * @return the timer
	 */
	public Timer getTimer() {
		return timer;
	}

	/**
	 * Sets the timer.
	 * 
	 * @param timer
	 *            the new timer
	 */
	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	/**
	 * Gets the msg.
	 * 
	 * @return the msg
	 */
	public Message getMsg() {
		return msg;
	}

	/**
	 * Sets the msg.
	 * 
	 * @param msg
	 *            the new msg
	 */
	public void setMsg(Message msg) {
		this.msg = msg;
	}

	/**
	 * Checks if is retry flag.
	 * 
	 * @return true, if is retry flag
	 */
	public boolean isRetryFlag() {
		return retryFlag;
	}

	/**
	 * Sets the retry flag.
	 * 
	 * @param retryFlag
	 *            the new retry flag
	 */
	public void setRetryFlag(boolean retryFlag) {
		this.retryFlag = retryFlag;
	}

	/**
	 * Gets the retry count.
	 * 
	 * @return the retry count
	 */
	public int getRetryCount() {
		return retryCount;
	}

	/**
	 * Sets the retry count.
	 * 
	 * @param retryCount
	 *            the new retry count
	 */
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
}
