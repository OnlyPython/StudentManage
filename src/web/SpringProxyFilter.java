package web;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;

/**
 * Servlet Filter implementation class SpringProxyFilter
 */
@WebFilter(dispatcherTypes = {DispatcherType.REQUEST }
					, urlPatterns = { "/*" })
public class SpringProxyFilter implements Filter {
	private ApplicationContext app;
    /**
     * Default constructor. 
     */
    public SpringProxyFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request0, ServletResponse response0, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) request0;
		String url = request.getRequestURI();
		ServletContext servletContext = request.getServletContext();
		String rootPath = servletContext.getContextPath();
		url = url.substring(rootPath.length()+1);
		HttpServlet servlet  = app.getBean(url,HttpServlet.class);
		if(servlet != null){
			servlet.service(request0, response0);
		}else{			
			chain.doFilter(request0, response0);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.app = (ApplicationContext) fConfig.getServletContext().getAttribute(CommonListener.APPLICATION_ATTRIBUTE);
	}

}
