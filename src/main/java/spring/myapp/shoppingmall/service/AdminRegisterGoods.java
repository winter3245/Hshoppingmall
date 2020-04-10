package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class AdminRegisterGoods 
{
	@Autowired
	private MallDao Malldao;
	
	public void register(String thumbnail,String name,int price,String kind,int remain,String content,String writer,String wcompany
			,String tcontent) {
		Malldao.uploadgoods(thumbnail,name,price,kind,remain,content,writer,wcompany,tcontent);
	}
}
