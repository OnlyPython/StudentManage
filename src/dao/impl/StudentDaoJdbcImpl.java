 package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.StudentDao;
import entity.Student;

public class StudentDaoJdbcImpl implements StudentDao {

	@Override
	public boolean isEntityExists(String name) {
		boolean result = false;
		try {
			//1. 加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection con=null;
		try {
			//2. 取得Connection
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
			String sql = "select id from student s where s.name =?";
			//3.创建PreparedStatement
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, name);
			//4. 取得ResultSet
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				result = true;
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
	public void saveOrUpdateEntity(Student student) {
		try {
			//1. 加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection con=null;
		try {
			//2. 取得Connection
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
			String sql = "insert into student(id,name,age,email) values(234,?,?,?)";
			//3.创建PreparedStatement
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, student.getName());
			pst.setInt(2, student.getAge());
			pst.setString(3, student.getEmail());
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
	public List<Student> listStudent() {
		List<Student> studentList = new ArrayList<>();
		try {
			//1. 加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection con=null;
		try {
			//2. 取得Connection
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
			String sql = "select id,name,age,email from student";
			//3.创建PreparedStatement
			PreparedStatement pst = con.prepareStatement(sql);
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

}
