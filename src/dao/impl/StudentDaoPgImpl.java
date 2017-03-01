 package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import dao.StudentDao;
import entity.Student;

@Repository("studentDao")
public class StudentDaoPgImpl implements StudentDao {
	@Resource
	private JdbcTemplate jdbcTemplate;
	
//	实现RowMapper接口，定义类与查询结果集的映射关系
	private RowMapper<Student> stuRowMapper = new RowMapper<Student>() {
		@Override
		public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
			Student student = new Student();
			student.setId(rs.getInt("id"));
			student.setName(rs.getString("name"));
			student.setAge(rs.getInt("age"));
			student.setEmail(rs.getString("email"));
			return student;
		}
	};
	
	@Override
	public boolean isEntityExists(String name) {
		String sql = "select count(*) from student s where s.name = ?";
//		queryForOjbect方法必须返回至少一个结果，否则回抛出异常,
		return jdbcTemplate.queryForObject(sql, Integer.class, name) != 0;
	}

	@Override
	public void saveOrUpdateEntity(Student student) {
		String sql;
		Object[] args;
		if(student.getId()==null){
			sql = "insert into student(name,age,email) values(?,?,?)";
			args = new Object[]{student.getName(), student.getAge(), student.getEmail()};
		}else{
			sql = "update student set name=?,age=?,email=? where id=?";
			args = new Object[]{student.getName(), student.getAge(), student.getEmail(), student.getId()};
		}
		jdbcTemplate.update(sql, args);
	}


	@Override
	public List<Student> searchByName(String studentName,int offset,int numPerPage) {
		if(StringUtils.isBlank(studentName)){
			studentName = "%";
		}else{
			studentName = "%" +studentName.trim() + "%";
		}
		String sql = "select id,name,age,email from student where name like ? limit ? offset ?";
		Object[] args = new Object[]{studentName, numPerPage, offset};
		return jdbcTemplate.query(sql, args, stuRowMapper);
	}

	@Override
	public void deleteById(Integer id) {
		String sql = "delete from student where id = ?";
		jdbcTemplate.update(sql, id);
	}

	@Override
	public int getTotalNum(String studentName) {
		if(StringUtils.isBlank(studentName)){
			studentName = "%";
		}else{
			studentName = "%" +studentName.trim() + "%";
		}
		String sql = "select count(*) from student where name like ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, studentName);
	}

	@Override
	public Student getById(Integer id) {
		String sql = "select id,name,age,email from student where id = ?";
		List<Student> stuList = jdbcTemplate.query(sql, stuRowMapper, id);
		if(stuList.size()==0){
			return null;
		}
		return stuList.get(0);
	}

}
