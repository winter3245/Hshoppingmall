package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.UserDao;

@Service
public class checkNowPasswordCommand 
{
	@Autowired
	private UserDao Userdao;
	
	public boolean checkNowPassword(String Userid,String Password)
	{
		return Userdao.checkNowPassword(Userid,Password);
	}	
}