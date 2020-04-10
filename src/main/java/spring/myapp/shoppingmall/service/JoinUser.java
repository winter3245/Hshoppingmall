package spring.myapp.shoppingmall.service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.UserDao;

@Service
public class JoinUser 
{
	@Autowired
	private UserDao Userdao;
	
	@Inject
	private BCryptPasswordEncoder passwordEncoder;
	
	public void joinuser(HttpServletRequest request) {
		String Id = request.getParameter("Id");
		String Password = request.getParameter("Password");
		String Name = request.getParameter("Name");
		String Address = request.getParameter("Address");
		int Age = Integer.valueOf(request.getParameter("Age"));
		String Sex = request.getParameter("Sex");
		String PhoneNumber = request.getParameter("PhoneNumber");
		String email = request.getParameter("email");
		
		String encPassword = passwordEncoder.encode(Password);
		
		Userdao.join(Id, encPassword, Name, Address, Sex, Age, PhoneNumber,email);
	}
}
