package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class DeleteAllShoppingmall {
	@Autowired
	private MallDao Malldao;

	public void deleteall(String id) {
		Malldao.deleteall(id);
	}
}
