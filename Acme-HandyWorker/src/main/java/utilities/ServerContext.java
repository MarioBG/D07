package utilities;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoaderListener;

public class ServerContext implements ServletContextListener {
	
	static volatile ContextLoaderListener springContext = new ContextLoaderListener();

	public void contextInitialized(ServletContextEvent sce) {
		springContext.contextInitialized(sce);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		springContext.contextDestroyed(sce);
	}

}
