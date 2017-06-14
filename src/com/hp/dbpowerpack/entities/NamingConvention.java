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
 * The Class NamingConvention.
 */
@Entity
@Table(name = "NAMING_CONVENTION")
public class NamingConvention {

	/** The user. */
	@OneToOne
	@JoinColumns({
			@JoinColumn(name = "userId", referencedColumnName = "USER_ID"),
			@JoinColumn(name = "objectType", referencedColumnName = "OBJECT_TYPE") })
	@EmbeddedId
	private UserPK user;

	/** The starts with. */
	@Column(name = "STARTS_WITH")
	private String startsWith;

	/** The created by. */
	@Column(name = "CREATED_BY")
	private String createdBy;

	/** The created date. */
	@Column(name = "CREATED_DATE")
	private Date createdDate;

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
	 * Sets the user.
	 * 
	 * @param user
	 *            the new user
	 */
	public void setUser(UserPK user) {
		this.user = user;
	}

	/**
	 * Gets the starts with.
	 * 
	 * @return the starts with
	 */
	public String getStartsWith() {
		return startsWith;
	}

	/**
	 * Sets the starts with.
	 * 
	 * @param startsWith
	 *            the new starts with
	 */
	public void setStartsWith(String startsWith) {
		this.startsWith = startsWith;
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
	 * Gets the created date.
	 * 
	 * @return the created date
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * Sets the created date.
	 * 
	 * @param createdDate
	 *            the new created date
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserId() {
		if (this.user != null) {
			return this.user.userId;
		} else {
			return null;
		}
	}

	/**
	 * Gets the object type.
	 *
	 * @return the object type
	 */
	public String getObjectType() {
		if (this.user != null) {
			return this.user.objectType;
		} else {
			return null;
		}
	}

	/**
	 * The Class UserPK.
	 */
	@Embeddable
	@MappedSuperclass
	public static class UserPK implements Serializable {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 8134956272541517651L;

		/**
		 * Instantiates a new user pk.
		 */
		protected UserPK() {
			
		}

		/** The user id. */
		@Column(name = "USER_ID")
		private String userId;

		/** The object type. */
		@Column(name = "OBJECT_TYPE")
		private String objectType;

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
		 * Gets the object type.
		 * 
		 * @return the object type
		 */
		public String getObjectType() {
			return objectType;
		}

		/**
		 * Sets the object type.
		 * 
		 * @param objectType
		 *            the new object type
		 */
		public void setObjectType(String objectType) {
			this.objectType = objectType;
		}

	}
}
