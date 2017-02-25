package web.user;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;

import entity.User;
import service.StudentService;
import service.UserService;
import web.CommonListener;

/**
 * Servlet implementation class UserLoginServlet
 */
//@WebServlet("/user/login")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for(Cookie cookie : cookies){
			if("userName".equals(cookie.getName())){
				request.setAttribute("userName", cookie.getValue());
			}else if("password".equals(cookie.getName())){
				request.setAttribute("password", cookie.getValue());
				}
			}
		}
		
		request.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String remeberMe = request.getParameter("remeberMe");
		User user = this.userService.findUserByUserName(userName);
		//如果可以从数据库中检索到名称相同的用户且密码相同，则登陆。
		if(user !=null &&password.equals(user.getPassword())){
			if(null !=remeberMe){
				Cookie userNameCookie = new Cookie("userName",userName);
				userNameCookie.setMaxAge(120);
				userNameCookie.setPath(request.getServletContext().getAttribute("ctx")+"user");				
				response.addCookie(userNameCookie);
				Cookie passwordCookie = new Cookie("password",password);
				passwordCookie.setMaxAge(120);
				passwordCookie.setPath(request.getServletContext().getAttribute("ctx")+"user");
				response.addCookie(passwordCookie);
			} else {
				Cookie userNameCookie = new Cookie("userName",userName);
				userNameCookie.setMaxAge(0);
				userNameCookie.setPath(request.getServletContext().getAttribute("ctx")+"user");				
				response.addCookie(userNameCookie);
				Cookie passwordCookie = new Cookie("password",password);
				passwordCookie.setMaxAge(0);
				passwordCookie.setPath(request.getServletContext().getAttribute("ctx")+"user");
				response.addCookie(passwordCookie);
			}
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			//sendRedirect : 重定向，跳转到另一个网页（url）
			String url = response.encodeRedirectURL(request.getServletContext().getAttribute("ctx")+ "/student/student-add");
			response.sendRedirect(url);
		}else{
			request.setAttribute("loginFail","用户名或密码错误");
			//forward : 请求转发，将req，rep交给另一个Servlet处理（url不变）
			request.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
		}
	}

}
