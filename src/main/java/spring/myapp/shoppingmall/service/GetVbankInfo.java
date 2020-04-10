package spring.myapp.shoppingmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;
import spring.myapp.shoppingmall.dto.Vbank;

@Service
public class GetVbankInfo {
	@Autowired
	private MallDao Malldao;
	
	public Vbank getvbankinfo(String merchant_id)
	{
		return Malldao.getvbankinfo(merchant_id);
	}
}