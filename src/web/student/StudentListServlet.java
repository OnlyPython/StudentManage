package web.student;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import entity.Student;
import service.StudentService;
import utils.Page;

/**
 * Servlet implementation class StudentListServlet
 */
//@WebServlet("/student/student-list")
@Component("student/student-list")
public class StudentListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource
    private StudentService studentService;
    

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String studentName = request.getParameter("studentName");
		String numPerPage_str = request.getParameter("numPerPage");
		String currentPage_str = request.getParameter("currentPage");
		Page<Student> page = null;
		if(StringUtils.isNotBlank(currentPage_str) && StringUtils.isNotBlank(numPerPage_str)){
			page = this.studentService.searchByName(studentName, Integer.parseInt(currentPage_str), Integer.parseInt(numPerPage_str));
		} else {
			page = this.studentService.searchByName();
		}
		request.setAttribute("page", page);
//		if(studentName==null){
//			List<Student> studentList = this.studentService.studentList();
//			request.setAttribute("students", studentList);
//		}else{			
//			List<Student> studentList = this.studentService.searchByName(studentName);
//			request.setAttribute("students", studentList);
//		}
		request.getServletContext().getRequestDispatcher("/WEB-INF/page/student/Student-List.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
