package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class UpdateStatusCancel 
{
	@Autowired
	private MallDao Malldao;
	
	public void updatestatuscancel(String merchant_id,String cancel) {
		Malldao.statusupdatecancel(merchant_id,cancel);
	}
}
