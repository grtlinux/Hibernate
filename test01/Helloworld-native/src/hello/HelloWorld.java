package hello;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import persistence.HibernateUtil;

public class HelloWorld {

	private static final boolean flag;
	
	static {
		flag = true;
	}
	
	//////////////////////////////////////////////////////////
	
	public static void main(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + new Object() {}.getClass().getEnclosingClass().getName());
		
		if (flag) test01(args);
		if (flag) test02(args);
		if (flag) test03(args);
		if (flag) test04(args);
	}
	
	private static void test01(String[] args) throws Exception {
		// First unit of work
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		Message message = new Message("Hello World");
		session.save(message);
		
		tx.commit();
		session.close();
	}
	
	private static void test02(String[] args) throws Exception {
		// Second unit of work
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		@SuppressWarnings("unchecked")
		List<Message> messages = session.createQuery("from Message m order by m.text asc").list();
		System.out.println(messages.size() + " message(s) found: ");
		
		for (Iterator<Message> iter = messages.iterator(); iter.hasNext();) {
			Message loadedMessage = (Message) iter.next();
			System.out.println(loadedMessage.getText());
		}
		
		tx.commit();
		session.close();
	}

	private static void test03(String[] args) throws Exception {
		// Third unit of work
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		Message message = new Message("Hello World!!! be loaded");
		session.save(message);

		Message loadedMessage = (Message) session.get(Message.class, message.getId());
		loadedMessage.setText("Greetings Earthling..");
		loadedMessage.setNextMessage(new Message("Take me to your leader (please)"));
		
		tx.commit();
		session.close();
	}
	
	private static void test04(String[] args) throws Exception {
		// Forth unit of work
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		@SuppressWarnings("unchecked")
		List<Message> messages = session.createQuery("from Message m order by m.text asc").list();
		System.out.println(messages.size() + " message(s) found: ");
		
		for (Iterator<Message> iter = messages.iterator(); iter.hasNext();) {
			Message loadedMessage = (Message) iter.next();
			System.out.println(loadedMessage.getText());
		}
		
		tx.commit();
		session.close();
	}
}
