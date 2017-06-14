package com.hp.dbpowerpack.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.hp.dbpowerpack.common.dao.HibernateUtil;
import com.hp.dbpowerpack.common.exception.DBPPDaoException;
import com.hp.dbpowerpack.entities.NamingConvention;


/**
 * The Class NamingConventionDAO.
 */
public class NamingConventionDAO {

	/**
	 * Register NamingConvention.
	 *
	 * @param nameConvention the name convention
	 * @return true, if successful
	 * @throws DBPPDaoException the dBPP dao exception
	 */
	public boolean addNamingConvention(NamingConvention nameConvention)
			throws DBPPDaoException {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Transaction t = null;
		try {
			Session s = sf.openSession();
			t = s.beginTransaction(); // start a new transaction
			s.persist(nameConvention);
			t.commit(); // commit transaction
			return true;
		} catch (HibernateException ex) {
			if (t != null)
				t.rollback(); // rollback transaction on exception
			throw new DBPPDaoException(ex);
		}
	}

	/**
	 * Gets the NamingConvention details.
	 *
	 * @param userId the user id
	 * @param slctdDbObj the slctd db obj
	 * @return the NamingConvention details
	 * @throws DBPPDaoException the dBPP dao exception
	 */
	public List<NamingConvention> getNamingConventionDetails(String userId,
			String slctdDbObj) throws DBPPDaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<NamingConvention> NamingConventionList = null;
		try {
			transaction = session.beginTransaction();
			if (slctdDbObj != null) {
				NamingConventionList = session.createQuery(
						"from NamingConvention where user.userId='" + userId
								+ "' and user.objectType in ('" + slctdDbObj
								+ "')").list();
			} else {
				NamingConventionList = session.createQuery(
						"from NamingConvention where user.userId='" + userId
								+ "'").list();
			}

			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new DBPPDaoException(e);
		} finally {
			session.close();
		}

		return NamingConventionList;

	}

	/**
	 * Update naming convention.
	 *
	 * @param nameConvention the name convention
	 * @return true, if successful
	 * @throws DBPPDaoException the dBPP dao exception
	 */
	public boolean updateNamingConvention(NamingConvention nameConvention)
			throws DBPPDaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		boolean flag = false;
		try {
			transaction = session.beginTransaction();
			NamingConvention namingConventionObj = (NamingConvention) session
					.get(NamingConvention.class, nameConvention.getUser());
			namingConventionObj.setStartsWith(nameConvention.getStartsWith());
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

}