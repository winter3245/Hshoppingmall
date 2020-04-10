package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class InsertDbPrice 
{
	@Autowired
	private MallDao Malldao;
	
	public void insertprice(String price,String merchant_uid)
	{
		Malldao.insertPrice(Integer.valueOf(price),merchant_uid);
	}
}
