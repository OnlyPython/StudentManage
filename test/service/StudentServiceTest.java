package service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import entity.Student;
import utils.Page;

public class StudentServiceTest {

	@Test
	public void test() {
		StudentService ss = new StudentService();
		Page<Student> page = ss.searchByName("", 2, 5);
		assertEquals(5,page.getList().size());
		System.out.println(page.getList());
	}
	@Test
	public void test2() throws Exception{
		StudentService ss = new StudentService();
		System.out.println(ss.getStudentDao().getClass());
		assertNotNull(ss.getStudentDao());
	}
	@Test
	public void test3() {
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath:application.xml");
		StudentService studentService = app.getBean("studentService",StudentService.class);
		Student student = studentService.getById(2);
		assertEquals(student.getAge(),18);
	}
}
