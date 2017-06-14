package com.hp.dbpowerpack.common.model;

import java.io.File;


/**
 * The Class MailModel.
 */
public class MailModel {

	/** The from. */
	private String from;

	/** The to. */
	private String to;

	/** The cc. */
	private String cc;

	/** The subject. */
	private String subject;

	/** The body content. */
	private String bodyContent;

	/** The attachment. */
	private File attachment;

	/**
	 * Gets the from.
	 * 
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * Sets the from.
	 * 
	 * @param from
	 *            the new from
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * Gets the to.
	 * 
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * Sets the to.
	 * 
	 * @param to
	 *            the new to
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * Gets the cc.
	 * 
	 * @return the cc
	 */
	public String getCc() {
		return cc;
	}

	/**
	 * Sets the cc.
	 * 
	 * @param cc
	 *            the new cc
	 */
	public void setCc(String cc) {
		this.cc = cc;
	}

	/**
	 * Gets the subject.
	 * 
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the subject.
	 * 
	 * @param subject
	 *            the new subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Gets the body content.
	 * 
	 * @return the body content
	 */
	public String getBodyContent() {
		return bodyContent;
	}

	/**
	 * Sets the body content.
	 * 
	 * @param bodyContent
	 *            the new body content
	 */
	public void setBodyContent(String bodyContent) {
		this.bodyContent = bodyContent;
	}

	/**
	 * Gets the attachment.
	 * 
	 * @return the attachment
	 */
	public File getAttachment() {
		return attachment;
	}

	/**
	 * Sets the attachment.
	 * 
	 * @param attachment
	 *            the new attachment
	 */
	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}
}
