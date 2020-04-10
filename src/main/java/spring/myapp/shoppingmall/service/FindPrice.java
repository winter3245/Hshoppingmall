package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class FindPrice 
{
	@Autowired
	private MallDao Malldao;
	
	public int getpriceBymerchantid(String merchant_uid)
	{
		return Malldao.getfindprice(merchant_uid);
	}
}
