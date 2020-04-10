package spring.myapp.shoppingmall.service;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.UserDao;

@Service
public class UpdatePasswordCommand 
{
	@Autowired
	private UserDao Userdao;
	@Inject
	private BCryptPasswordEncoder passwordEncoder;
	
	public UpdatePasswordCommand()
	{
		System.out.println("UpdatePasswordCommand bean made");
	}
	
	public void update(String Userid,String Password)
	{
		String encPassword = passwordEncoder.encode(Password);
		Userdao.updatePassword(Userid, encPassword);
	}
}