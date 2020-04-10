package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class DeleteShoppingBasket 
{
	@Autowired
	private MallDao Malldao;
	
	public void deleteshoppingbasket(int pnum) {
		Malldao.deletebasket(pnum);
	}
}
