package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class InsertGoods {
	@Autowired
	private MallDao Malldao;

	public void insergoods(String merchant_id,String[] list,Integer[] glist)
	{
		Malldao.insertgoods(merchant_id,list,glist);
	}
}
