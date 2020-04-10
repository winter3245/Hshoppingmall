package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class CartSpace {
	@Autowired
	private MallDao Malldao;
	
	public int cartspace(String Id)
	{
		return Malldao.cartspace(Id);
	}
}
