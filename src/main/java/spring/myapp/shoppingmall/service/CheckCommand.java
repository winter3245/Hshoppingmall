package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.UserDao;

@Service
public class CheckCommand 
{
	@Autowired
	private UserDao dao;
	
	public CheckCommand()
	{
		System.out.println("CheckCommand");
	}
	
	public int execute(String id)
	{
		int count = dao.check(id);
		
		return count;
	}
}