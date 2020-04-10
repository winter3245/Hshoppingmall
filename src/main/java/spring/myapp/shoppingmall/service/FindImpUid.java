package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class FindImpUid {
	@Autowired
	private MallDao Malldao;
	
	public String getimp_uid(String merchant_uid)
	{
		return Malldao.getimp_uid(merchant_uid);
	}
}
