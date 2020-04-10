package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class MobileMerchantuidCheck {
	@Autowired
	private MallDao Malldao;
	
	public boolean mobilecheckbymerchantuid(String merchant_uid) {
		return Malldao.mobilecheck(merchant_uid);
	}
}
