package com.hp.dbpowerpack.common.util;

import java.io.File;
import java.util.Properties;
import java.util.Timer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.hp.dbpowerpack.common.model.MailModel;
import com.hp.dbpowerpack.common.model.MailTaskModel;


/**
 * The Class MailUtil.
 */
public class MailUtil {

	/** The Constant logger. */
	private static final Logger LOGGER = Logger.getLogger(MailUtil.class);

	/**
	 * Send email with attachment.
	 * 
	 * @param model
	 *            the model
	 * @return true, if successful
	 */
	public static boolean sendEmailWithAttachment(MailModel model) {

		boolean returnValue = false;
		try {

			/* send it */
			postMail(model);
			LOGGER.info("SecurityMail:sendEmailList - mail sent to "
					+ model.getTo());
			returnValue = true;
		} catch (MessagingException e) {
			LOGGER.error("Exception occured" + e);
		}
		return returnValue;
	}

	/**
	 * Post mail.
	 * 
	 * @param model
	 *            the model
	 * @throws MessagingException
	 *             the messaging exception
	 */
	public static void postMail(MailModel model) throws MessagingException {
		boolean debug = false;

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp3.hp.com");
		/***** TEST ONLY *******/

		// create some properties and get the default Session
		Session session = Session.getInstance(props, null);
		session.setDebug(debug);

		// create a message
		MimeMessage msg = new MimeMessage(session);

		// set the from and to address
		InternetAddress addressFrom = new InternetAddress(
				"DBPowerPack@hp.com");
		msg.setFrom(addressFrom);

		
		if(model.getTo()!=null){
			 String[] toArr = model.getTo().split(";");
			 InternetAddress[] addressTo = new InternetAddress[toArr.length];
			 for(int i=0; i<toArr.length; i++){
				 addressTo[i] = new InternetAddress(toArr[i]);
			 }
			 msg.setRecipients(Message.RecipientType.TO, addressTo);
		}
		
		
		

		if (model.getCc() != null && !"".equals(model.getCc())) {
			if(model.getCc()!=null){
				 String[] ccArr = model.getCc().split(";");
				 InternetAddress[] addressCc = new InternetAddress[ccArr.length];
				 for(int i=0; i<ccArr.length; i++){
					 addressCc[i] = new InternetAddress(ccArr[i]);
				 }
				 msg.setRecipients(Message.RecipientType.CC, addressCc);
			}
			
		}

		// Setting the Subject and Content Type
		msg.setSubject(model.getSubject());
		// Create the message part
		BodyPart messageBodyPart = new MimeBodyPart();

		// Fill the message
		messageBodyPart.setText(model.getBodyContent());

		// Create a multipar message
		Multipart multipart = new MimeMultipart();

		// Set text message part
		multipart.addBodyPart(messageBodyPart);

		// Part two is attachment
		messageBodyPart = new MimeBodyPart();
		File file = model.getAttachment();
		DataSource source = new FileDataSource(file);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(file.getName());
		multipart.addBodyPart(messageBodyPart);

		// Send the complete message parts
		msg.setContent(multipart);

		try {

			// Ship it!
			Transport.send(msg);
		} catch (MessagingException e) {
			LOGGER.error("Exception occured" + e);
			MailTaskModel mailModel = new MailTaskModel();
			mailModel.setMsg(msg);
			mailModel.setRetryCount(0);
			mailModel.setRetryFlag(true);
			timerTask(mailModel);

		}
	}

	/**
	 * Timer task.
	 * 
	 * @param mailModel
	 *            the mail model
	 */
	private static void timerTask(final MailTaskModel mailModel) {

		Timer timer = new Timer();
		mailModel.setTimer(timer);
		// 2nd retry when there is an exception
		timer.schedule(new DBPPMailTimerTask(mailModel), 0, // initial delay
				1 * 1000);
	}
}
