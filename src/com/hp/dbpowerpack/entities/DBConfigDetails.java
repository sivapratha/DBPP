package com.hp.dbpowerpack.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The Class DBConfigDetails.
 */
@Entity
@Table(name = "USER_DB_CONFIG")
public class DBConfigDetails {

	/** The user. */
	@OneToOne
	@JoinColumns({
			@JoinColumn(name = "userid", referencedColumnName = "USER_ID"),
			@JoinColumn(name = "dbName", referencedColumnName = "DB_NAME") })
	@EmbeddedId
	private UserPK user;

	/** The server name. */
	@Column(name = "SERVER_NAME")
	private String serverName;

	/** The port number. */
	@Column(name = "PORT_NUMBER")
	private String portNumber;

	/** The sid. */
	@Column(name = "SID")
	private String sid;

	/** The user name. */
	@Column(name = "DB_USER_NAME")
	private String userName;

	/** The pass word. */
	@Column(name = "PASSWORD")
	private String passWord;

	/** The created_ by. */
	@Column(name = "CREATED_BY")
	private String createdBy;

	/** The created_dt. */
	@Column(name = "CREATED_DATE")
	private Date createddt;

	/**
	 * Sets the user.
	 * 
	 * @param user
	 *            the new user
	 */
	public void setUser(UserPK user) {
		this.user = user;
	}

	/**
	 * Gets the user.
	 * 
	 * @return the user
	 */
	public UserPK getUser() {
		if (this.user == null) {
			this.user = new UserPK();
		}
		return user;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the createddt.
	 *
	 * @return the createddt
	 */
	public Date getCreateddt() {
		return createddt;
	}

	/**
	 * Sets the createddt.
	 *
	 * @param createddt the createddt to set
	 */
	public void setCreateddt(Date createddt) {
		this.createddt = createddt;
	}

	/**
	 * Gets the server name.
	 * 
	 * @return the server name
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * Sets the server name.
	 * 
	 * @param serverName
	 *            the new server name
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * Gets the port number.
	 * 
	 * @return the port number
	 */
	public String getPortNumber() {
		return portNumber;
	}

	/**
	 * Sets the port number.
	 * 
	 * @param portNumber
	 *            the new port number
	 */
	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	/**
	 * Gets the sid.
	 * 
	 * @return the sid
	 */
	public String getSid() {
		return sid;
	}

	/**
	 * Sets the sid.
	 * 
	 * @param sid
	 *            the new sid
	 */
	public void setSid(String sid) {
		this.sid = sid;
	}

	/**
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 * 
	 * @param userName
	 *            the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the pass word.
	 * 
	 * @return the pass word
	 */
	public String getPassWord() {
		return passWord;
	}

	/**
	 * Sets the pass word.
	 * 
	 * @param passWord
	 *            the new pass word
	 */
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	/**
	 * Gets the userid.
	 * 
	 * @return the userid
	 */
	public String getUserid() {
		if (this.user != null) {
			return this.user.userid;
		} else {
			return null;
		}

	}

	/**
	 * Gets the db name.
	 * 
	 * @return the db name
	 */
	public String getDbName() {
		if (this.user != null) {
			return this.user.dbName;
		} else {
			return null;
		}

	}

	/**
	 * Sets the db name.
	 * 
	 * @param dbName
	 *            the new db name
	 */
	public void setDbName(String dbName) {
		if (this.user != null) {
			this.user.dbName = dbName;
		} else {
			this.user = new UserPK();
			this.user.dbName = dbName;
		}

	}

	/**
	 * Sets the userid.
	 * 
	 * @param userid
	 *            the new userid
	 */
	public void setUserid(String userid) {
		if (this.user != null) {
			this.user.userid = userid;
		} else {
			this.user = new UserPK();
			this.user.userid = userid;
		}
	}

	/**
	 * The Class UserPK.
	 */
	@Embeddable
	@MappedSuperclass
	public static class UserPK implements Serializable {

		/**
		 * Instantiates a new user pk.
		 */
		protected UserPK() {
			
		}

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -8913157290332961611L;

		/** The db name. */
		@Column(name = "DB_NAME")
		private String dbName;

		/** The userid. */
		@Column(name = "USER_ID")
		private String userid;

		/**
		 * Gets the db name.
		 * 
		 * @return the db name
		 */
		public String getDbName() {
			return dbName;
		}

		/**
		 * Sets the db name.
		 * 
		 * @param dbName
		 *            the new db name
		 */
		public void setDbName(String dbName) {
			this.dbName = dbName;
		}

		/**
		 * Gets the userid.
		 * 
		 * @return the userid
		 */
		public String getUserid() {
			return userid;
		}

		/**
		 * Sets the userid.
		 * 
		 * @param userid
		 *            the new userid
		 */
		public void setUserid(String userid) {
			this.userid = userid;
		}

	}
}
