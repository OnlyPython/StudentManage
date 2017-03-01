package service;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.UserDao;
import entity.User;
import suport.NoTX;

@Service
@NoTX
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
