package dao;

import entity.User;
import utils.TransactionalAspect;

public interface UserDao {
	void setDbSource(TransactionalAspect dbSource);
	public User findUserByUserName(String userName);

}
