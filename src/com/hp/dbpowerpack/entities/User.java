package com.hp.dbpowerpack.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The Class User.
 */
@Entity
@Table(name = "USER_DETAILS")
public class User {

	/** The user id. */
	@Id
	@Column(name = "USER_ID")
	private String userId;

	/** The first name. */
	@Column(name = "USER_FIRST_NAME")
	private String firstName;

	/** The last name. */
	@Column(name = "USER_LAST_NAME")
	private String lastName;

	/** The email. */
	@Column(name = "EMAIL_ID")
	private String email;

	/** The password. */
	@Column(name = "PASSWORD")
	private String password;

	/** The created by. */
	@Column(name = "CREATED_BY")
	private String createdBy;

	/** The created dt. */
	@Column(name = "CREATED_DATE")
	private Date createdDt;

	/** The salt. */
	@Column(name = "SALT")
	private String salt;

	/**
	 * Gets the user id.
	 * 
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 * 
	 * @param userId
	 *            the new user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the salt.
	 * 
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * Sets the salt.
	 * 
	 * @param salt
	 *            the new salt
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * Gets the email.
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 * 
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the userid.
	 * 
	 * @return the userid
	 */
	public String getUserid() {
		return userId;
	}

	/**
	 * Sets the userid.
	 * 
	 * @param userid
	 *            the new userid
	 */
	public void setUserid(String userid) {
		this.userId = userid;
	}

	/**
	 * Gets the first name.
	 * 
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 * 
	 * @param firstName
	 *            the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 * 
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 * 
	 * @param lastName
	 *            the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the created by.
	 * 
	 * @return the created by
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 * 
	 * @param createdBy
	 *            the new created by
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the created dt.
	 * 
	 * @return the created dt
	 */
	public Date getCreatedDt() {
		return createdDt;
	}

	/**
	 * Sets the created dt.
	 * 
	 * @param createdDt
	 *            the new created dt
	 */
	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

}