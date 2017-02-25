package dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import entity.Student;

public class StudentDaoTest {

	@Test
	public void test() {
		StudentDao studentDao = new StudentDaoMysqlImpl();
		List<Student> studentList = studentDao.searchByName("云", 2, 1);
		System.out.println(studentList);
	}
	@Test
	public void test2() {
		StudentDao studentDao = new StudentDaoMysqlImpl();
		int i= studentDao.getTotalNum("云");
		assertEquals(4,i);
	}

}
