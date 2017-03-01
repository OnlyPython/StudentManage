package dao;

import javax.sql.DataSource;

import entity.User;

public interface UserDao {
	void setDbSource(DataSource dbSource);
	public User findUserByUserName(String userName);

}
