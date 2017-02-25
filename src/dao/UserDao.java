package dao;

import utils.DbSource;
import entity.User;

public interface UserDao {
	void setDbSource(DbSource dbSource);
	public User findUserByUserName(String userName);

}
