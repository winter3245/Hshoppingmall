package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class Goodsprofileupload {	//��ǰ�� �̹���
	@Autowired
	private MallDao Malldao;
	
	public void uploadProflie(int goods_id,String uploadurl) {
		Malldao.uploadProfilegoods(goods_id,uploadurl);
	}
}