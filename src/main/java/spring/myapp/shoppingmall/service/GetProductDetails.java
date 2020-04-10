package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;
import spring.myapp.shoppingmall.dto.goods;

@Service
public class GetProductDetails //product.jsp�� ��Ÿ���µ� �̿�
{
	@Autowired
	private MallDao Malldao;
	
	public goods getproductdetails(int goods_id) {
		return Malldao.getGoodsInfo(goods_id);
	}
}
