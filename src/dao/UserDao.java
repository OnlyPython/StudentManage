package dao;

import javax.sql.DataSource;

import entity.User;

public interface UserDao {
	public User findUserByUserName(String userName);

}
