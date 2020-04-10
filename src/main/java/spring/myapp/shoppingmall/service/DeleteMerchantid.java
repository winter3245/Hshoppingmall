package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class DeleteMerchantid {
	@Autowired
	private MallDao Malldao;
	
	public void deletemerchantid(String merchant_id) {
		Malldao.deltemerchantid(merchant_id);
	}
}
