package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class AddComment {
	@Autowired
	private MallDao Malldao;
	
	public boolean addComment(String gId,String user_id,String reply)
	{
		return Malldao.addComment(gId,user_id,reply);
	}
}
