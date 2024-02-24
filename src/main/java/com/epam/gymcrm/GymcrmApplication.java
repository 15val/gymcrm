package com.epam.gymcrm;

import com.epam.gymcrm.utils.HibernateUtil;
import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


public class GymcrmApplication {

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.close();
	}

}
