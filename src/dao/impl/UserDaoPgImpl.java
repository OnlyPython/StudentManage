package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import dao.DaoException;
import dao.UserDao;
import entity.Student;
import entity.User;

@Repository("userDao")
public class UserDaoPgImpl implements UserDao {
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	RowMapper<User> userRowMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setUserName(rs.getString("user_name"));
			user.setRealName(rs.getString("real_name"));
			user.setPassword(rs.getString("password"));
			return user;
		}
	};
	
	@Override
	public User findUserByUserName(String userName) {
		String sql = "select * from users u where u.user_name = ?";
		List<User> userList = jdbcTemplate.query(sql, userRowMapper, userName);
		if(userList.size()==0){
			return null;
		}
		return userList.get(0);
	}

}
