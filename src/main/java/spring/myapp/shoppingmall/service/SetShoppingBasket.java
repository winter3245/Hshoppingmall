package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class SetShoppingBasket 
{
	@Autowired
	private MallDao Malldao;
	
	public void shoppingbasket(int qty,int gid,int price,String User_ID,String name) {
		Malldao.setShoppingbasket(gid, User_ID, price, qty,name);
	}
}
