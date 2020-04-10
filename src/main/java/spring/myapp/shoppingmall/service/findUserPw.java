package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import spring.myapp.shoppingmall.dao.UserDao;
@Service
public class findUserPw 
{
	@Autowired
	private UserDao Userdao;
	
	public void find(String cId,Model model)
	{
		model.addAttribute("findUser",Userdao.findPw(cId));
	}
}