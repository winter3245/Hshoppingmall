package spring.myapp.shoppingmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;
import spring.myapp.shoppingmall.dto.Shoppingbasket;

@Service
public class GetShoppingBasket 
{
	@Autowired
	private MallDao Malldao;
	
	public List<Shoppingbasket> getshoppingbasketlist(String User_ID){
		return Malldao.getShoppingbasket(User_ID);
	}
}
