 package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import dao.DaoException;
import dao.StudentDao;
import entity.Student;
import utils.DbSource;

public class StudentDaoPgImpl implements StudentDao {
	private DbSource dbs;
	public DbSource getDbs() {
		return dbs;
	}
	@Override
	public void setDbSource(DbSource dbSource) {
		this.dbs = dbSource;
	}
	@Override
	public boolean isEntityExists(String name) {
		boolean result = false;
		Connection con =null;	
		try {
			//2. 取得Connection
			con = dbs.getConnection();
			String sql = "select id from student s where s.name = ?";
			//3.创建PreparedStatement
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, name);
			//4. 取得ResultSet
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				result = true;
			}
		} catch (SQLException e) {
			throw new DaoException("sql异常", e);
		}finally{
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}

	@Override
	public void saveOrUpdateEntity(Student student) {
		Connection con=null;
		try {
			//2. 取得Connection
			con = dbs.getConnection();
			String sql = "";
			PreparedStatement pst = null;
			if(student.getId()==null){
				sql = "insert into student(name,age,email) values(?,?,?)";
				//3.创建PreparedStatement
				pst = con.prepareStatement(sql);
				pst.setString(1, student.getName());
				pst.setInt(2, student.getAge());
				pst.setString(3, student.getEmail());
			}else{
				sql = "update student set name=?,age=?,email=? where id=?";
				pst = con.prepareStatement(sql);
				pst.setString(1, student.getName());
				pst.setInt(2, student.getAge());
				pst.setString(3, student.getEmail());
				pst.setInt(4, student.getId());
			}
			//4. 取得ResultSet
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}


	@Override
	public List<Student> searchByName(String studentName,int offset,int numPerPage) {
		List<Student> studentList = new ArrayList<>();
		Connection con=null;
		if(StringUtils.isBlank(studentName)){
			studentName = "%";
		}else{
			studentName = "%" +studentName.trim() + "%";
		}
		
		try {
			//2. 取得Connection
			con = dbs.getConnection();
			String sql = "select id,name,age,email from student where name like ? limit ? offset ?";
			//3.创建PreparedStatement
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, studentName);
			pst.setInt(2,numPerPage);
			pst.setInt(3,offset);
			//4. 取得ResultSet
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Student stu = new Student(rs.getInt("id"),rs.getString("name"),rs.getInt("age"),rs.getString("email"));
				studentList.add(stu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return studentList;
	}

	@Override
	public void deleteById(Integer id) {
		Connection con=null;
		try {
			//2. 取得Connection
			con = dbs.getConnection();
			String sql = "delete from student where id = ?";
			//3.创建PreparedStatement
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1,id);
			//4. 取得ResultSet
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public int getTotalNum(String studentName) {
		int result = 0;
		Connection con=null;
		if(studentName == null || "".endsWith(studentName.trim())){
			studentName = "%";
		}else{
			studentName = "%" +studentName.trim() + "%";
		}
		
		try {
			//2. 取得Connection
			con = dbs.getConnection();
			String sql = "select count(*) from student where name like ? ";
			//3.创建PreparedStatement
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, studentName);
			//4. 取得ResultSet
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public Student getById(Integer id) {
		Student stu = null;
		Connection con=null;
		
		try {
			//2. 取得Connection
			con = dbs.getConnection();
			String sql = "select id,name,age,email from student where id = ?";
			//3.创建PreparedStatement
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1,id);
			//4. 取得ResultSet
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				stu = new Student(rs.getInt("id"),rs.getString("name"),rs.getInt("age"),rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return stu;
	}

	

	

}
