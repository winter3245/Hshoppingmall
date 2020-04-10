package spring.myapp.shoppingmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;
import spring.myapp.shoppingmall.dto.goods;

@Service
public class GetGoodsInfo 	//shop.jsp의 화면을 나타내는데 이용
{
	@Autowired
	private MallDao Malldao;
	
	public List<goods> getGoodsInfo(String kind){
		return Malldao.getShoppingGoodsInfo(kind);
	}
}
