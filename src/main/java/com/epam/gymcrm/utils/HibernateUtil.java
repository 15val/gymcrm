package com.epam.gymcrm.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class HibernateUtil {
	private static SessionFactory sessionFactory = initSessionFactory();

	private static SessionFactory initSessionFactory(){
		try{
			return new Configuration().configure(new File("src\\main\\resources\\hibernate.cfg.xml")).buildSessionFactory();
		}
		catch (Throwable e){
			System.err.println("Initial SessionFactory creation failed: " + e.getMessage());//log
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory(){
		if(sessionFactory == null){
			sessionFactory = initSessionFactory();
		}
		return sessionFactory;
	}

}
