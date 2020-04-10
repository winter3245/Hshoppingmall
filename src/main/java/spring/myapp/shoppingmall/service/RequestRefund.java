package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class RequestRefund 
{
	@Autowired
	private MallDao Malldao;
	
	public void requestrefund(String merchant_id,Integer amount,String holder,String bank,String account)
	{
		Malldao.requestrefund(merchant_id,amount,holder,bank,account);
	}
}
