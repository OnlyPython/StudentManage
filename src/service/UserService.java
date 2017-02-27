package service;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import dao.UserDao;
import entity.User;

@Service
public class UserService {
	@Resource
	private UserDao userDao;
 
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public User findUserByUserName(String userName) {
		return userDao.findUserByUserName(userName);
	}
}
