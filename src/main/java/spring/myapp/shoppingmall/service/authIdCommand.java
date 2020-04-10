package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.UserDao;

@Service
public class authIdCommand 
{
	@Autowired
	private UserDao Userdao;
	
	public boolean authId(String e_mail,String phoneNumber,String name)
	{
		return Userdao.authId(e_mail,phoneNumber,name);
	}
}
