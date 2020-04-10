package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import spring.myapp.shoppingmall.dao.UserDao;

@Service
public class findUserId 
{
	@Autowired
	private UserDao Userdao;
	
	public void find(String name,String phoneNumber,Model model)
	{
		model.addAttribute("findUser",Userdao.findId(name,phoneNumber));
	}
}