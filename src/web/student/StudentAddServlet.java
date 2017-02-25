package web.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import service.StudentService;
import web.CommonListener;
import entity.Student;

/**
 * Servlet implementation class AddStudentServlet
 */
//@WebServlet("/student/student-add")
public class StudentAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private StudentService studentService;

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
		boolean isSuccess = this.studentService.saveOrUpdateStudent(student);
		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		if(isSuccess){
			response.sendRedirect(request.getServletContext().getAttribute("ctx")+"/student/student-list");	
		}else{
			writer.println("保存失败<br/>");
		}
		writer.println("<a href = \"addStudent.html\">添加页面</a>");
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String id_str = request.getParameter("id");
		if(StringUtils.isNotBlank(id_str)){
			Integer id = Integer.valueOf(id_str);
			Student student = this.studentService.getById(id);
			request.setAttribute("student", student);
			request.setAttribute("update_operate", true);
		}
		request.getServletContext().getRequestDispatcher("/WEB-INF/Student-Add.jsp").forward(request, response);
	}

}
