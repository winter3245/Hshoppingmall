package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class UpdateDiscountPercent {
	@Autowired
	private MallDao Malldao;
	
	public void updatediscountpercent(String couponId,String merchant_id)
	{
		Malldao.updatediscountpercent(couponId,merchant_id);
	}
}
