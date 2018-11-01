package hello;

import java.io.File;

import javax.naming.InitialContext;

import org.jboss.ejb3.embedded.EJB3StandaloneBootstrap;

public class HelloWorld {

	private static final boolean flag;
	
	static {
		flag = true;
	}
	
	//////////////////////////////////////////////////////////////
	
	public static void main(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + new Object() {}.getClass().getEnclosingClass().getName());

		// Boot the JBoss Microcontainer with EJB3 settings, automatically
		// loads ejb3-interceptors-aop.xml and embedded-jboss-beans.xml
		EJB3StandaloneBootstrap.boot(null);
		
		// Deploy custom stateless beans (datasource, mostly)
		EJB3StandaloneBootstrap.deployXmlResource("META-INF/helloworld-beans.xml");
		
		// Deploy all EJBs found on classpath (slow, scans all)
		//EJB3StandaloneBootstrap.scanClasspath();
		
		// Deploy all EJBs found on classpath (fast, scans only build directory)
		// This is a relative location, matching the substring end of one of java.class.path locations!
		// Print out System.getProperty("java.class.path") to understand this...
		EJB3StandaloneBootstrap.scanClasspath("helloworld-ejb3/build".replaceAll("/", File.separator));
		
		// Create InitialContext from jndi.properties
		InitialContext initialContext = new InitialContext();
		
		// Lookup MessageHandler EJB
		MessageHandler msgHandler = (MessageHandler) initialContext.lookup("MessageHandlerBean/local");
		
		// Call EJB
		msgHandler.saveMessage();
		msgHandler.showMessage();
		
		// Shutdown EJB container
		EJB3StandaloneBootstrap.shutdown();
	}
}
