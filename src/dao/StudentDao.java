package dao;

import java.util.List;

import entity.Student;
import utils.TransactionalAspect;

public interface StudentDao {
	void setDbSource(TransactionalAspect dbSource);
	boolean isEntityExists(String name);
	void saveOrUpdateEntity(Student student);
	
	List<Student> searchByName(String name,int offset,int numPerPage);
	void deleteById(Integer id);
	int getTotalNum(String studentName);
	Student getById(Integer id);
}
