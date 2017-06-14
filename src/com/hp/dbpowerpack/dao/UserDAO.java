package com.hp.dbpowerpack.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.hp.dbpowerpack.common.dao.HibernateUtil;
import com.hp.dbpowerpack.common.exception.DBPPDaoException;
import com.hp.dbpowerpack.entities.User;


/**
 * The Class UserDAO.
 */
public class UserDAO {

	/**
	 * Register user.
	 *
	 * @param u the u
	 * @return true, if successful
	 * @throws DBPPDaoException the dBPP dao exception
	 */
	public boolean registerUser(User u) throws DBPPDaoException {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Transaction t = null;
		try {
			Session s = sf.openSession();
			t = s.beginTransaction(); // start a new transaction
			s.persist(u);
			t.commit(); // commit transaction
			return true;
		} catch (HibernateException ex) {
			if (t != null)
				t.rollback(); // rollback transaction on exception
			throw new DBPPDaoException(ex);
		}
	}

	/**
	 * Gets the user details.
	 *
	 * @param userId the user id
	 * @return the user details
	 * @throws DBPPDaoException the dBPP dao exception
	 */
	public User getUserDetails(String userId) throws DBPPDaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		User user = null;
		List<User> userList = null;
		try {
			transaction = session.beginTransaction();
			userList = session.createQuery(
					"from User user where user.userId='" + userId + "'").list();

			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new DBPPDaoException(e);
		} finally {
			session.close();
		}

		if (userList != null && userList.size() == 1) {
			user = userList.get(0);
		}

		return user;

	}

	/**
	 * Change password.
	 *
	 * @param user the user
	 * @return true, if successful
	 * @throws DBPPDaoException the dBPP dao exception
	 */
	public boolean changePassword(User user) throws DBPPDaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		boolean flag = false;
		try {
			transaction = session.beginTransaction();
			User userObj = (User) session.get(User.class, user.getUserid());
			userObj.setPassword(user.getPassword());
			userObj.setSalt(user.getSalt());
			transaction.commit();
			flag = true;
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new DBPPDaoException(e);
		} finally {
			session.close();
		}
		return flag;
	}

	public List<User> getUserDetails() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<User> userList = null;
		try {
			transaction = session.beginTransaction();
			userList = session.createQuery(
					"from User user").list();

			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new DBPPDaoException(e);
		} finally {
			session.close();
		}


		return userList;

	}
}