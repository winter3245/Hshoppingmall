package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;
import spring.myapp.shoppingmall.dto.Refund;

@Service
public class GetRefundInfo {
	@Autowired
	private MallDao Malldao;

	public Refund getrefund(String merchant_id) {
		return Malldao.getrefund(merchant_id);
	}
}
