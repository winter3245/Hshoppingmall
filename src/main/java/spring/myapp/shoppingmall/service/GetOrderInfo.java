package spring.myapp.shoppingmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;
import spring.myapp.shoppingmall.dto.Order;

@Service
public class GetOrderInfo {
	@Autowired
	private MallDao Malldao;
	
	public List<Order> getorderinfo(String id,int curPageNum)
	{
		return Malldao.getorderinfo(id,curPageNum);
	}
}
