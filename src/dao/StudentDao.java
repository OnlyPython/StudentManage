package dao;

import java.util.List;

import javax.sql.DataSource;

import entity.Student;

public interface StudentDao {
	boolean isEntityExists(String name);
	void saveOrUpdateEntity(Student student);
	
	List<Student> searchByName(String name,int offset,int numPerPage);
	void deleteById(Integer id);
	int getTotalNum(String studentName);
	Student getById(Integer id);
}
