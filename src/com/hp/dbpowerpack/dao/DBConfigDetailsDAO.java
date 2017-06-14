package com.hp.dbpowerpack.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.hp.dbpowerpack.common.dao.HibernateUtil;
import com.hp.dbpowerpack.common.exception.DBPPDaoException;
import com.hp.dbpowerpack.entities.DBConfigDetails;
import com.hp.dbpowerpack.entities.DBConfigDetails.UserPK;


/**
 * The Class DBConfigDetailsDAO.
 */
public class DBConfigDetailsDAO {

	/**
	 * Adds the db config details.
	 *
	 * @param dbConfigDetails the db config details
	 * @return true, if successful
	 * @throws DBPPDaoException the dBPP dao exception
	 */
	public boolean addDBConfigDetails(DBConfigDetails dbConfigDetails)
			throws DBPPDaoException {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction t = null;
		boolean flag = false;
		try {

			t = s.beginTransaction(); // start a new transaction
			s.save(dbConfigDetails);
			t.commit(); // commit transaction
			flag = true;
		} catch (HibernateException ex) {
			if (t != null) {
				t.rollback(); // rollback transaction on exception
			}
			throw new DBPPDaoException(ex);
		} finally {
			s.close();
		}
		return flag;
	}

	/**
	 * Gets the dB config details list.
	 *
	 * @param userId the user id
	 * @return the dB config details list
	 * @throws DBPPDaoException the dBPP dao exception
	 */
	@SuppressWarnings("unchecked")
	public List<DBConfigDetails> getDBConfigDetailsList(String userId)
			throws DBPPDaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<DBConfigDetails> dbConfigDetailsList = null;
		try {
			transaction = session.beginTransaction();
			dbConfigDetailsList = session.createQuery(
					"from DBConfigDetails where user.userid='" + userId + "'")
					.list();

			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new DBPPDaoException(e);
		} finally {
			session.close();
		}

		return dbConfigDetailsList;
	}

	/**
	 * Update city.
	 *
	 * @param dbConfigDetails the db config details
	 * @return true, if successful
	 * @throws DBPPDaoException the dBPP dao exception
	 */
	public boolean updateCity(DBConfigDetails dbConfigDetails)
			throws DBPPDaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		boolean flag = false;
		try {
			transaction = session.beginTransaction();
			DBConfigDetails dbConfigDetailsObj = (DBConfigDetails) session.get(
					DBConfigDetails.class, dbConfigDetails.getUserid());
			dbConfigDetailsObj.setPassWord(dbConfigDetails.getPassWord());
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

	/**
	 * Removes the config.
	 *
	 * @param dbName the db name
	 * @param userId the user id
	 * @throws DBPPDaoException the dBPP dao exception
	 */
	public void removeConfig(String dbName, String userId)
			throws DBPPDaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			DBConfigDetails dbConfigDetails = new DBConfigDetails();
			UserPK user = dbConfigDetails.getUser();
			user.setDbName(dbName);
			user.setUserid(userId);
			transaction = session.beginTransaction();
			DBConfigDetails dbConfigDetailsObj = (DBConfigDetails) session.get(
					DBConfigDetails.class, user);
			session.delete(dbConfigDetailsObj);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new DBPPDaoException(e);
		} finally {
			session.close();
		}
	}

	/**
	 * Gets the dB config detail.
	 *
	 * @param dbName the db name
	 * @param userId the user id
	 * @return the dB config detail
	 * @throws DBPPDaoException the dBPP dao exception
	 */
	public DBConfigDetails getDBConfigDetail(String dbName, String userId)
			throws DBPPDaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		DBConfigDetails dbConfigDetails = null;
		List<DBConfigDetails> dbConfigDetailsList = null;
		try {
			transaction = session.beginTransaction();
			dbConfigDetailsList = session.createQuery(
					"from DBConfigDetails where user.dbName='" + dbName + "'")
					.list();

			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}

			throw new DBPPDaoException(e);
		} finally {
			session.close();
		}

		if (dbConfigDetailsList != null && !dbConfigDetailsList.isEmpty()) {
			dbConfigDetails = dbConfigDetailsList.get(0);
		}
		return dbConfigDetails;
	}

	/**
	 * Gets the db user names.
	 *
	 * @return the db user names
	 * @throws DBPPDaoException the dBPP dao exception
	 */
	@SuppressWarnings("unchecked")
	public List<DBConfigDetails> getdbUserNames() throws DBPPDaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<DBConfigDetails> dbConfigDetailsList = null;
		try {
			transaction = session.beginTransaction();
			dbConfigDetailsList = session.createQuery(
					"select config.userName from DBConfigDetails config")
					.list();

			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new DBPPDaoException(e);
		} finally {
			session.close();
		}

		return dbConfigDetailsList;
	}

	/**
	 * Gets the dB config details list.
	 *
	 * @param userId the user id
	 * @param dbName the db name
	 * @return the dB config details list
	 * @throws DBPPDaoException the dBPP dao exception
	 */
	public DBConfigDetails getDBConfigDetailsList(String userId, String dbName)
			throws DBPPDaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		DBConfigDetails model = null;
		List<DBConfigDetails> dbConfigDetailsList = null;
		try {
			transaction = session.beginTransaction();
			dbConfigDetailsList = session.createQuery(
					"from DBConfigDetails where user.userid='" + userId
							+ "' and user.dbName='" + dbName + "'").list();

			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new DBPPDaoException(e);
		} finally {
			session.close();
		}

		if (dbConfigDetailsList != null && !dbConfigDetailsList.isEmpty()) {
			model = dbConfigDetailsList.get(0);
		}

		return model;
	}
}