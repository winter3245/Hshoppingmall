package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class UpdateUseCouponService {
	@Autowired
	private MallDao Malldao;
	
	public void updateusecouponservice(String yes,String cnumber)
	{
		Malldao.updateusecouponservice(yes,cnumber);
	}
}
