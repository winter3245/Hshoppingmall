package spring.myapp.shoppingmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;
import spring.myapp.shoppingmall.dto.Coupon;
@Service
public class GetCoupons {
	@Autowired
	private MallDao Malldao;
	
	public List<Coupon> getcouponsbyId(String id) {
		return Malldao.getcoupons(id);
	}

}
