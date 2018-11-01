package org.tain.hibernate.test;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class AppMain {

	private static User user;
	private static Session session;
	private static SessionFactory sessionFactory;
	
	private static SessionFactory buildSessionFactory() {
		// Creating Configuration Instance & Passing Hibernate Configuration File
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		
		// Since Hibernate Version 4.x, ServiceRegistry is Being Used
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		
		// Creating Hibernate SessionFactory Instance
		sessionFactory = config.buildSessionFactory(serviceRegistry);
		return sessionFactory;
	}
	
	public static void main(String[] args) {
		
		System.out.println("................Hibernate Maven Example..............\n");
		
		try {
			session = buildSessionFactory().openSession();
			session.beginTransaction();
			
			for (int i=101; i <= 105; i++) {
				user = new User();
				user.setUserId(i);
				user.setUserName("Editor " + i);
				user.setCreatedBy("Administrator");
				user.setCreatedDate(new Date());
				
				session.save(user);
			}
			
			System.out.println("\n..............Records Saved Successfully To The Database.............\n");
			
			// Committing The Transactions To The Database
			session.getTransaction().commit();
		} catch (Exception e) {
			if (null != session.getTransaction()) {
				System.out.println("\n................Transaction is Being Rolled Back................\n");
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) { session.close(); }
		}
	}
}
