package hello;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class MessageHandlerBean implements MessageHandler {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void saveMessage() {
		Message message = new Message("Hello World with EJB3");
		em.persist(message);
	}

	@Override
	public void showMessage() {
		@SuppressWarnings("unchecked")
		List<Message> messages = em.createQuery("select m from Message m order by m.text asc").getResultList();
		System.out.println(messages.size() + " message(s) found: ");
		
		for (Message message : messages) {
			System.out.println(message);
		}
	}
}
