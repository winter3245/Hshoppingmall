package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.UserDao;

@Service
public class repasswordCommand 
{
	@Autowired
	private UserDao userdao;
	
	public void execute(String userId,String repassword)
	{
		userdao.repassword(userId,repassword);
	}
}
