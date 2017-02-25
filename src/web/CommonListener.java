package web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Application Lifecycle Listener implementation class CommonListener
 *
 */
@WebListener
public class CommonListener implements ServletContextListener {
	public static final String APPLICATION_ATTRIBUTE = "app";
	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    		//在servletContext中加入服务器url路径，这样会将整个项目的url路径统一
    		ServletContext servletContext =arg0.getServletContext();
    		servletContext.setAttribute("ctx", servletContext.getContextPath());
    		//将Spring容器放入servletContext中
    		ApplicationContext app = new ClassPathXmlApplicationContext("application.xml");
    		servletContext.setAttribute(APPLICATION_ATTRIBUTE, app);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
