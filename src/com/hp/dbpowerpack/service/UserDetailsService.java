package com.hp.dbpowerpack.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.hp.dbpowerpack.Model.UserModel;
import com.hp.dbpowerpack.common.exception.DBPPBusinessException;
import com.hp.dbpowerpack.common.exception.DBPPConfigException;
import com.hp.dbpowerpack.common.exception.DBPPDaoException;
import com.hp.dbpowerpack.common.util.DBPPEncryptDecryptUtil;
import com.hp.dbpowerpack.dao.UserDAO;
import com.hp.dbpowerpack.entities.User;

/**
 * The Class UserDetailsService.
 */
public class UserDetailsService {

	/** The user dao. */
	private UserDAO userDao;

	/**
	 * Gets the user dao.
	 * 
	 * @return the user dao
	 */
	public UserDAO getUserDao() {
		return userDao;
	}

	/**
	 * Sets the user dao.
	 * 
	 * @param userDao
	 *            the new user dao
	 */
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	/**
	 * Adds the user.
	 *
	 * @param userMdl the user mdl
	 * @param createdUser the created user
	 * @return true, if successful
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public boolean addUser(UserModel userMdl, String createdUser)
			throws DBPPBusinessException {

		boolean successFlag = false;
		User user = new User();

		user.setUserid(userMdl.getUserid());
		user.setFirstName(userMdl.getFirstName());
		user.setLastName(userMdl.getLastName());
		user.setEmail(userMdl.getEmail());
		user.setCreatedBy(createdUser);
		user.setCreatedDt(new Date(System.currentTimeMillis()));

		try {

			boolean isExist = isUserExist(userMdl.getUserid());

			if (!isExist) {
				Map<String, String> pwdMap = DBPPEncryptDecryptUtil
						.hashPassword(userMdl.getPassword());
				user.setPassword((String) pwdMap.get("PASSWORD"));
				user.setSalt((String) pwdMap.get("SALT"));

				successFlag = userDao.registerUser(user);
			}
		} catch (DBPPDaoException e) {
			throw new DBPPBusinessException(e);
		} catch (DBPPConfigException e) {
			throw new DBPPBusinessException(e);
		}
		return successFlag;

	}

	/**
	 * Authenticate user.
	 *
	 * @param userId the user id
	 * @param password the password
	 * @return the user
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public User authenticateUser(String userId, String password)
			throws DBPPBusinessException {
		User user = null;
		try {

			user = userDao.getUserDetails(userId);
			boolean isAuthenticated = false;
			if (user != null) {
				isAuthenticated = DBPPEncryptDecryptUtil.authenticate(password,
						user.getPassword(), user.getSalt());
			}

			if (!isAuthenticated) {
				user = null;
			}
		} catch (DBPPDaoException e) {
			throw new DBPPBusinessException(e);
		}
		return user;
	}

	/**
	 * Checks if is user exist.
	 *
	 * @param userId the user id
	 * @return true, if is user exist
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public boolean isUserExist(String userId) throws DBPPBusinessException {
		boolean isExist = false;
		try {
			User user = userDao.getUserDetails(userId);
			if (user != null) {
				isExist = true;
			}
		} catch (DBPPDaoException e) {
			throw new DBPPBusinessException(e);
		}
		return isExist;
	}

	/**
	 * Change password.
	 *
	 * @param userId the user id
	 * @param oldPassWord the old pass word
	 * @param newPassWord the new pass word
	 * @return the user
	 * @throws DBPPBusinessException the dBPP business exception
	 */
	public User changePassword(String userId, String oldPassWord,
			String newPassWord) throws DBPPBusinessException {
		User user = null;
		try {
			user = authenticateUser(userId, oldPassWord);
			if (user != null) {
				Map<String, String> pwdMap = DBPPEncryptDecryptUtil
						.hashPassword(newPassWord);
				user.setPassword((String) pwdMap.get("PASSWORD"));
				user.setSalt((String) pwdMap.get("SALT"));

				boolean successFlag = userDao.changePassword(user);
				if (!successFlag) {
					user = null;
				}
			}
		} catch (DBPPDaoException e) {
			throw new DBPPBusinessException(e);
		}
		return user;
	}

	public List<User> getUserDetails() {
		List<User> userLst = userDao.getUserDetails();
		return userLst;
	}

}
