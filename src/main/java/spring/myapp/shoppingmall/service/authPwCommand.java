package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.UserDao;

@Service
public class authPwCommand 
{
	@Autowired
	private UserDao Userdao;
	
	public boolean authPw(String cId,String e_mail,String phoneNumber,String name)
	{
		return Userdao.authPw(cId,e_mail,phoneNumber,name);
	}
}
