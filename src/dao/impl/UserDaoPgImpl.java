package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.DaoException;
import dao.UserDao;
import entity.User;
import utils.DbSource;
import utils.DbSourceImpl;

@Repository("userDao")
public class UserDaoPgImpl implements UserDao {
	@Autowired
	private DbSource dbs = new DbSourceImpl();
	@Override
	public void setDbSource(DbSource dbSource) {
		this.dbs = dbSource;
	} 
	@Override
	public User findUserByUserName(String userName) {
		Connection con = dbs.getConnection();
		User user=null;
		try {
			String sql = "select * from users u where u.user_name = ?";
			PreparedStatement prepareStatement = (PreparedStatement) con.prepareStatement(sql);
			prepareStatement.setString(1, userName);
			ResultSet rs = prepareStatement.executeQuery();
			if(rs.next()){
				Integer id = rs.getInt("id");
				String userName2 = rs.getString("user_name");
				String realName = rs.getString("real_name");
				String password = rs.getString("password");
				user = new User(id,userName2,realName,password);
			}
		} catch (SQLException e) {
			throw new DaoException("Dao 发生异常！", e);
		}
		return user;
	}

	

}
