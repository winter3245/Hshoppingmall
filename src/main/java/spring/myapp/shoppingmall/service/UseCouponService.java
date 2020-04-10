package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class UseCouponService 
{
	@Autowired
	private MallDao Malldao;
	
	public Integer usecoupon(String cnumber)
	{
		return Malldao.usecoupon(cnumber);
	}
}
