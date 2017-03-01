package web.student;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import dao.DaoException;
import entity.Student;
import service.ServiceException;
import service.StudentService;

/**
 * Servlet implementation class AddStudentServlet
 */
//@WebServlet("/student/student-add")
@Component("student/student-add")
public class StudentAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource
    private StudentService studentService;
    
//    private static Logger logger = LoggerFactory.getLogger(StudentAddServlet.class);
    
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id_str = request.getParameter("id");
		Integer id = StringUtils.isBlank(id_str) ? null : Integer.valueOf(id_str);
		String name = request.getParameter("name");
		String age_str =request.getParameter("age");
		int age = Integer.parseInt(age_str);
		String email = request.getParameter("email");
		
		Student student = new Student(id,name,age,email);
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		try {
			this.studentService.saveOrUpdateStudent(student);
			response.sendRedirect(request.getServletContext().getAttribute("ctx")+"/student/student-list");	
		} catch (ServiceException | DaoException e) {
//			logger.error("Servlet 发生异常！", e);
			writer.println("保存失败<br/>");
			writer.println("<a href = \"student-add\">添加页面</a>");
		}
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String id_str = request.getParameter("id");
		// 如果请求中传id，更新学生信息。
		// 如果不传id，新增学生信息。
		if(StringUtils.isNotBlank(id_str)){
			Integer id = Integer.valueOf(id_str);
			Student student = this.studentService.getById(id);
			request.setAttribute("student", student);
			// 让页面知道现在的操作是更新
			request.setAttribute("update_operate", true);
		}else{
			request.setAttribute("update_operate", false);
		}
		request.getServletContext().getRequestDispatcher("/WEB-INF/page/student/Student-Add.jsp").forward(request, response);
	}

}
