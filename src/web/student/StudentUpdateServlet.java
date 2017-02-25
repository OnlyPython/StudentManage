package web.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.StudentService;

import entity.Student;

/**
 * Servlet implementation class AddStudentServlet
 */
@WebServlet("/student/student-update")
public class StudentUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private StudentService studentService = new StudentService();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String age_str =request.getParameter("age");
		int age = Integer.parseInt(age_str);
		String email = request.getParameter("email");
		String id_str = request.getParameter("id");
		Integer id = Integer.parseInt(id_str);
		Student student = new Student(id,name,age,email);
		boolean isSuccess = this.studentService.saveOrUpdateStudent(student);
		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		if(isSuccess){
			response.sendRedirect(getServletContext().getAttribute("ctx")+"/student/student-list");	
		}else{
			writer.println("保存失败<br/>");
		}
		writer.println("<a href = \"addStudent.html\">添加页面</a>");
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String id_str = request.getParameter("id");
		Integer id = Integer.valueOf(id_str);
		Student student = this.studentService.getById(id);
		request.setAttribute("student", student);
		getServletContext().getRequestDispatcher("/WEB-INF/Student-Update.jsp").forward(request, response);
	}

}
