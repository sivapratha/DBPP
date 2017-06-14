package com.hp.dbpowerpack.common.exception;


/**
 * The Class DBPPConfigException.
 */
public class DBPPConfigException extends DBPPBaseException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7150794840623339989L;

	/**
	 * Constructs a new DBPPBase exception with <code>null</code> as its detail
	 * message.
	 */
	public DBPPConfigException() {
		super();
	}

	/**
	 * Constructs a new DBPPBase exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public DBPPConfigException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new DBPPBase exception with the specified detail message and
	 * cause.
	 * 
	 * @param message
	 *            the detail message
	 * @param cause
	 *            the cause (A <tt>null</tt> value is permitted, and indicates
	 *            that the cause is nonexistent or unknown.)
	 */
	public DBPPConfigException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new DBPPBase exception with the specified cause and a detail
	 * message of <tt>(cause==null ? null : cause.toString())</tt> (which
	 * typically contains the class and detail message of <tt>cause</tt>).
	 * 
	 * @param cause
	 *            the cause (A <tt>null</tt> value is permitted, and indicates
	 *            that the cause is nonexistent or unknown.)
	 */
	public DBPPConfigException(final Throwable cause) {
		super(cause);
	}

}
