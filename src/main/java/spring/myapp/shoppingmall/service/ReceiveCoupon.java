package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class ReceiveCoupon {
	@Autowired
	private MallDao Malldao;
	
	public Integer receivecoupon(String Id)
	{
		return Malldao.receivecoupon(Id);
	}
}
