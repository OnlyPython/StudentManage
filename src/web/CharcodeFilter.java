package web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class CharcodeFilter
 */
@WebFilter(dispatcherTypes = {DispatcherType.REQUEST }
					, urlPatterns = { "/*" })
public class CharcodeFilter implements Filter {


	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		if(req.getMethod().equalsIgnoreCase("get")){
			req = new EncodingRequest(req);
		}else{
			req.setCharacterEncoding("utf-8");
		}
		resp.setCharacterEncoding("utf-8");
		chain.doFilter(req,resp);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}
	private static class EncodingRequest extends HttpServletRequestWrapper{
		public EncodingRequest(HttpServletRequest request){
			super(request);
		}
		public String getParameter(String name){
			String param = super.getParameter(name);
			if(param != null){				
				try{
					param =new String(param.getBytes("iso-8859-1"),"utf-8");
				}catch(UnsupportedEncodingException e){
					e.printStackTrace();
				}
			}
			return param;
		}
		public String[] getParameterValues(String name){
			String[] params = super.getParameterValues(name);
			if(params != null){				
				try{
					for(int i = 0;i<params.length;i++){
						params[i] = new String(params[i].getBytes("iso-8859-1"),"utf-8");
					}
				}catch(UnsupportedEncodingException e){
					e.printStackTrace();
				}
			}
			return params ;
		}
	}

}
