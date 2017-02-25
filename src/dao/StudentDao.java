package dao;

import java.util.List;

import utils.DbSource;
import entity.Student;

public interface StudentDao {
	void setDbSource(DbSource dbSource);
	boolean isEntityExists(String name);
	void saveOrUpdateEntity(Student student);
	
	List<Student> searchByName(String name,int offset,int numPerPage);
	void deleteById(Integer id);
	int getTotalNum(String studentName);
	Student getById(Integer id);
}
