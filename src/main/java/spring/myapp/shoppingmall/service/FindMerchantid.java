package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;
import spring.myapp.shoppingmall.dto.Order;

@Service
public class FindMerchantid 
{
	@Autowired
	private MallDao Malldao;
	
	public Order getMerchantId(String merchant_id)
	{
		return Malldao.getMerchantid(merchant_id);
	}
}
