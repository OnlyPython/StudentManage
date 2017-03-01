package service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.StudentDao;
import entity.Student;
import suport.NoTX;
import suport.OpenTX;
import utils.Page;

@Service
@OpenTX
public class StudentService {
	@Resource
	private StudentDao studentDao;
	
	public StudentDao getStudentDao() {
		return studentDao;
	}
	
	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	public void saveOrUpdateStudent(Student student){
		if(student.getId()==null&&studentDao.isEntityExists(student.getName())){
			throw new ServiceException("该名学生（姓名）已存在！");
		}else{
			this.studentDao.saveOrUpdateEntity(student);
		}
	}
	@NoTX
	public Page<Student> searchByName(){
		int currentPage = Page.DEFAULT_CURRENT_PAGE;
		int numPerPage = Page.DEFAULT_NUM_PER_PAGE;
		return this.searchByName(null, currentPage, numPerPage);
	}
	@NoTX
	public Page<Student> searchByName(String studentName,int currentPage,int numPerPage) {
		int totalNum = this.studentDao.getTotalNum(studentName);
		Page<Student> page = new Page<>(currentPage,numPerPage,totalNum);
		List<Student> list = this.studentDao.searchByName(studentName, page.getOffset(), numPerPage);
		page.setList(list);
		return page;
	}
	public void deleteById(Integer id) {
		this.studentDao.deleteById(id);
	}
	@NoTX
	public Student getById(Integer id) {
		return this.studentDao.getById(id);
	}
	
}
