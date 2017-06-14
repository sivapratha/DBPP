package com.hp.dbpowerpack.common.dao;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;


/**
 * The Class HibernateUtil.
 */
public class HibernateUtil {

	/** The Constant logger. */
	private static final Logger LOGGER = Logger.getLogger(HibernateUtil.class);

	/** The Constant sessionFactory. */
	private static final SessionFactory SESSION_FACTORY;

	static {
		try {
			SESSION_FACTORY = new AnnotationConfiguration().configure()
					.buildSessionFactory();
		} catch (Throwable ex) {
			LOGGER.error("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Gets the session factory.
	 * 
	 * @return the session factory
	 */
	public static SessionFactory getSessionFactory() {
		return SESSION_FACTORY;
	}
}