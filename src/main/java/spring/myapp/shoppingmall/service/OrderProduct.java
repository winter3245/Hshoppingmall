package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class OrderProduct {
	@Autowired
	private MallDao Malldao;
	
	public void order(String phoneNumber,String address)
	{
		//Malldao.insertOrder(phoneNumber,address);
	}
}
