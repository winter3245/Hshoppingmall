package spring.myapp.shoppingmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;
import spring.myapp.shoppingmall.dto.Ordergoods;

@Service
public class GetOrderGoods {
	@Autowired
	private MallDao Malldao;
	
	public List<Ordergoods> getordergoods(String merchant_id)
	{
		return Malldao.getordergoods(merchant_id);
	}
}
