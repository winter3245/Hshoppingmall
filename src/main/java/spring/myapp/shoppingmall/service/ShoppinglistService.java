package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;
import spring.myapp.shoppingmall.dao.UserDao;
import spring.myapp.shoppingmall.dto.User;

@Service
public class ShoppinglistService {
	
	@Autowired
	private MallDao Malldao;
	@Autowired
	private UserDao Userdao;
	
	public User getUser(String Id) {
		User user = Userdao.getUser(Id);
		return user;
	}
}