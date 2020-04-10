package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class MerChantId 
{
	@Autowired
	private MallDao Malldao;
	
	public void InsertMerchant(String id,String merchant_id,String phoneNumber,String address,String buyer_name,String memo) {
		Malldao.insertMerchant(id,merchant_id,phoneNumber,address,buyer_name,memo);
	}
}
