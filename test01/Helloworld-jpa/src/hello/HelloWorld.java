package hello;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class HelloWorld {

	private static final boolean flag;
	private static EntityManagerFactory emf;
	static {
		flag = true;
	}
	
	/////////////////////////////////////////////////////////
	
	public static void main(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + new Object() {}.getClass().getEnclosingClass().getName());
		
		emf = Persistence.createEntityManagerFactory("helloworld");

		if (flag) test01(args);
		if (flag) test02(args);
		
		emf.close();
	}
	
	private static void test01(String[] args) throws Exception {
		// First unit of work
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Message message = new Message("Hello World with JPA");
		em.persist(message);
		
		tx.commit();
		em.clear();
	}
	
	private static void test02(String[] args) throws Exception {
		// Second unit of work
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		@SuppressWarnings("unchecked")
		List<Message> messages = em.createQuery("select m from Message m order by m.text asc").getResultList();
		System.out.println(messages.size() + " message(s) found: ");
		
		for (Message m : messages) {
			System.out.println(m.getText());
		}
		
		tx.commit();
		em.close();
	}
}
